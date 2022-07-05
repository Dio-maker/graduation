package process

import bean.{BeanCovidData, BeanGlobalData, BeanStatisticsData}
import com.alibaba.fastjson.JSON
import org.apache.spark.SparkContext
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, Dataset, ForeachWriter, Row, SparkSession}
import utils.JDBCLink

import java.util
import scala.jdk.CollectionConverters._
//import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`
import scala.collection.{StringOps, mutable}

object DataProcess {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession=SparkSession.builder().master(master="local[*]").appName("DataProcess").getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("WARN")

    import spark.implicits._
    import org.apache.spark.sql.functions._

    val kafkaData: DataFrame=spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers","hadoop01:9092,hadoop02:9092,hadoop03:9092")
      .option("subscribe","covid2022")
      //.option("checkpointLocation","./checkpoint")
      .load()
    /*val kafkaData2: DataFrame=spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers","hadoop01:9092,hadoop02:9092,hadoop03:9092")
      .option("subscribe","covidglobal")
      //.option("checkpointLocation","./checkpoint")
      .load()*/
    val jsonStr: Dataset[String]=kafkaData.selectExpr("CAST(value AS STRING)").as[String]
    //val jsonStr2: Dataset[String]=kafkaData2.selectExpr("CAST(value AS STRING)").as[String]



    val beanCovid: Dataset[BeanCovidData]= jsonStr.map(jsonStr=>{
      JSON.parseObject(jsonStr,classOf[BeanCovidData])

    })


    val province: Dataset[BeanCovidData] = beanCovid.filter(_.statisticsData!=null)
    val city: Dataset[BeanCovidData] = beanCovid.filter(_.statisticsData==null)
    /*val global: Dataset[BeanGlobalData]= jsonStr2.map(jsonStr2=>{
      JSON.parseObject(jsonStr2,classOf[BeanGlobalData])

    })*/

    val statisticsDS: Dataset[BeanStatisticsData] = province.flatMap(p=> {
      val jsonSta: String = p.statisticsData
      val value: util.List[BeanStatisticsData]= JSON.parseArray(jsonSta,classOf[BeanStatisticsData])
      value.asScala.map(s => {
        s.provinceShortName = p.provinceShortName
        s.locationId = p.locationId
        s
      })
  })

    //全国疫情数据，总计现有确诊，已确诊人数，疑似病例，已治愈人数，死亡人数
    val Data01: DataFrame= province.groupBy("date").agg(
      sum("currentConfirmedCount") as "currentConfirmedCount",
      sum("confirmedCount") as "confirmedCount",
      sum("suspectedCount") as "suspectedCount",
      sum("curedCount") as "curedCount",
      sum("deadCount") as "deadCount"
    )
    Data01.writeStream.foreach(new JDBCLink("replace into Data_01 (date,currentConfirmedCount,confirmedCount,suspectedCount,curedCount,deadCount) values (?,?,?,?,?,?)") {
      override def realProcess(sql: String, row: Row): Unit = {
        val date: String = row.getAs[String]("date")
        val currCCount: Long = row.getAs[Long]("currentConfirmedCount")
        val confCount: Long = row.getAs[Long]("confirmedCount")
        val susCount: Long = row.getAs[Long]("suspectedCount")
        val curCount: Long = row.getAs[Long]("curedCount")
        val deadCount: Long = row.getAs[Long]("deadCount")
        ps = conn.prepareStatement(sql)
        ps.setString(1,date)
        ps.setLong(2,currCCount)
        ps.setLong(3,confCount)
        ps.setLong(4,susCount)
        ps.setLong(5,curCount)
        ps.setLong(6,deadCount)
        ps.executeUpdate()
      }
    }
    ).outputMode("complete").trigger(Trigger.ProcessingTime(0)).option("truncate",false).start()

    //各省份疫情分开统计
    val Data02: DataFrame=province.select("provinceShortName","locationId","date","currentConfirmedCount","confirmedCount","suspectedCount","curedCount","deadCount")
    Data02.writeStream.foreach(new JDBCLink("replace into Data_02 (provinceShortName,locationId,date,currentConfirmedCount,confirmedCount,suspectedCount,curedCount,deadCount) values (?,?,?,?,?,?,?,?)") {
      override def realProcess(sql: String, row: Row): Unit = {
        val provinceShortName: String = row.getAs[String]("provinceShortName")
        val locationId: Int = row.getAs[Int ]("locationId")
        val date: String = row.getAs[String]("date")
        val currentConfirmedCount: Int  = row.getAs[Int ]("currentConfirmedCount")
        val confirmedCount: Int  = row.getAs[Int ]("confirmedCount")
        val suspectedCount: Int  = row.getAs[Int ]("suspectedCount")
        val curedCount: Int  = row.getAs[Int ]("curedCount")
        val deadCount: Int  = row.getAs[Int ]("deadCount")

        ps = conn.prepareStatement(sql)
        ps.setString(1,provinceShortName)
        ps.setInt(2,locationId)
        ps.setString(3,date)
        ps.setInt(4,currentConfirmedCount)
        ps.setInt(5,confirmedCount)
        ps.setInt(6,suspectedCount)
        ps.setInt(7,curedCount)
        ps.setInt(8,deadCount)
        ps.executeUpdate()
      }
    }
    ).outputMode("append").trigger(Trigger.ProcessingTime(0)).option("truncate",false).start()

    //全国疫情增长分析
    val Data03: DataFrame=statisticsDS.groupBy("dateId").agg(
      sum("currentConfirmedIncr") as "currentConfirmedIncr",
      sum("confirmedIncr") as "confirmedIncr",
      sum("suspectedCountIncr") as "suspectedCountIncr",
      sum("curedIncr") as "curedIncr",
      sum("deadIncr") as "deadIncr"
    )
    Data03.writeStream.foreach(new JDBCLink("replace into Data_03 (dateId,currentConfirmedIncr,confirmedIncr,suspectedCountIncr,curedIncr,deadIncr) values (?,?,?,?,?,?)") {
      override def realProcess(sql: String, row: Row): Unit = {
        val dateId: String = row.getAs[String]("dateId")
        val currentConfirmedIncr: Long = row.getAs[Long]("currentConfirmedIncr")
        val confirmedIncr: Long = row.getAs[Long]("confirmedIncr")
        val suspectedCountIncr: Long = row.getAs[Long]("suspectedCountIncr")
        val curedIncr: Long = row.getAs[Long]("curedIncr")
        val deadIncr: Long = row.getAs[Long]("deadIncr")

        ps = conn.prepareStatement(sql)
        ps.setString(1,dateId)
        ps.setLong(2,currentConfirmedIncr)
        ps.setLong(3,confirmedIncr)
        ps.setLong(4,suspectedCountIncr)
        ps.setLong(5,curedIncr)
        ps.setLong(6,deadIncr)
        ps.executeUpdate()
      }
    }
    ).outputMode("complete").trigger(Trigger.ProcessingTime(0)).option("truncate",false).start()

    //各省份境外输入统计
    val Data04:DataFrame=city.filter(_.cityName.contains("境外")).groupBy("date","provinceId","provinceShortName","confirmedCount")
      .agg(sum("confirmedCount") as "confirmedCount" )
    Data04.writeStream.foreach(new JDBCLink("replace into Data_04 (date,provinceId,provinceShortName,confirmedCount) values (?,?,?,?)") {
      override def realProcess(sql: String, row: Row): Unit = {
        val date: String = row.getAs[String]("date")
        val provinceId: Int = row.getAs[Int]("provinceId")
        val provinceShortName: String = row.getAs[String]("provinceShortName")
        val confirmedCount: Long  = row.getAs[Long]("confirmedCount")

        ps = conn.prepareStatement(sql)
        ps.setString(1,date)
        ps.setInt(2,provinceId)
        ps.setString(3,provinceShortName)
        ps.setLong(4,confirmedCount)
        ps.executeUpdate()
      }
    }
    ).outputMode("complete").trigger(Trigger.ProcessingTime(0)).option("truncate",false).start()

    //江西数据
   val Data05:DataFrame=city.filter(_.provinceShortName.equals("江西")).select("provinceShortName","cityName","locationId","date","currentConfirmedCount","confirmedCount","suspectedCount","curedCount","deadCount")
    Data05.writeStream.foreach(new JDBCLink("replace into Data_05 (provinceShortName,cityName,locationId,date,currentConfirmedCount,confirmedCount,suspectedCount,curedCount,deadCount) values (?,?,?,?,?,?,?,?,?)") {
      override def realProcess(sql: String, row: Row): Unit = {
        val provinceShortName: String = row.getAs[String]("provinceShortName")
        val cityName: String = row.getAs[String]("cityName")
        val locationId: Int = row.getAs[Int]("locationId")
        val date: String = row.getAs[String]("date")
        val currentConfirmedCount: Int = row.getAs[Int]("currentConfirmedCount")
        val confirmedCount: Int = row.getAs[Int]("confirmedCount")
        val suspectedCount: Int = row.getAs[Int]("suspectedCount")
        val curedCount: Int = row.getAs[Int]("curedCount")
        val deadCount: Int = row.getAs[Int]("deadCount")

        ps = conn.prepareStatement(sql)
        ps.setString(1,provinceShortName)
        ps.setString(2,cityName)
        ps.setInt(3,locationId)
        ps.setString(4,date)
        ps.setInt(5,currentConfirmedCount)
        ps.setInt(6,confirmedCount)
        ps.setInt(7,suspectedCount)
        ps.setInt(8,curedCount)
        ps.setInt(9,deadCount)
        ps.executeUpdate()
      }
    }
    ).outputMode("append").trigger(Trigger.ProcessingTime(0)).option("truncate",false).start()
    //Data01.writeStream.format("console").outputMode("complete")
    //  .trigger(Trigger.ProcessingTime(0))
    //  .option("truncate",false).start().awaitTermination()




    Data05.writeStream.format("console").outputMode("append").trigger(Trigger.ProcessingTime(0)).option("truncate",false).start().awaitTermination()
}}
