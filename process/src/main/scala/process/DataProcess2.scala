package process

import bean.{BeanCovidData, BeanGlobalData, BeanStatisticsData}
import com.alibaba.fastjson.JSON
import org.apache.spark.SparkContext
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import utils.JDBCLink

import java.util
import scala.jdk.CollectionConverters._
//import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`

object DataProcess2 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession=SparkSession.builder().master(master="local[*]").appName("DataProcess2").getOrCreate()
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("WARN")

    import org.apache.spark.sql.functions._
    import spark.implicits._



    val kafkaData2: DataFrame=spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers","hadoop01:9092,hadoop02:9092,hadoop03:9092")
      .option("subscribe","covidglobal")
      //.option("checkpointLocation","./checkpoint")
      .load()

    val jsonStr2: Dataset[String]=kafkaData2.selectExpr("CAST(value AS STRING)").as[String]


    val global: Dataset[BeanGlobalData]= jsonStr2.map(jsonStr2=>{
      JSON.parseObject(jsonStr2,classOf[BeanGlobalData])

    })


    //全球疫情数据，总计现有确诊，已确诊人数，疑似病例，已治愈人数，死亡人数
    val Data01: DataFrame= global.groupBy("date").agg(
      sum("currentConfirmedCount") as "currentConfirmedCount",
      sum("confirmedCount") as "confirmedCount",
      sum("curedCount") as "curedCount",
      sum("deadCount") as "deadCount"
    )
    Data01.writeStream.foreach(new JDBCLink("replace into global01 (date,currentConfirmedCount,confirmedCount,curedCount,deadCount) values (?,?,?,?,?)") {
      override def realProcess(sql: String, row: Row): Unit = {
        val date: String = row.getAs[String]("date")
        val currCCount: Long = row.getAs[Long]("currentConfirmedCount")
        val confCount: Long = row.getAs[Long]("confirmedCount")
        val curCount: Long = row.getAs[Long]("curedCount")
        val deadCount: Long = row.getAs[Long]("deadCount")
        ps = conn.prepareStatement(sql)
        ps.setString(1,date)
        ps.setLong(2,currCCount)
        ps.setLong(3,confCount)
        ps.setLong(4,curCount)
        ps.setLong(5,deadCount)
        ps.executeUpdate()
      }
    }
    ).outputMode("complete").trigger(Trigger.ProcessingTime(0)).option("truncate",false).start()


    //全球数据
    val Data02:DataFrame=global.select("locationId","continents","provinceName","currentConfirmedCount","confirmedCount","curedCount","deadCount","date")
    //
    Data02.writeStream.foreach(new JDBCLink("replace into global02 (locationId,continents,provinceName,currentConfirmedCount,confirmedCount,curedCount,deadCount,date) values (?,?,?,?,?,?,?,?)") {
      override def realProcess(sql: String, row: Row): Unit = {
        val locationId: Int = row.getAs[Int]("locationId")
        val continents: String = row.getAs[String]("continents")
        val provinceName: String = row.getAs[String]("provinceName")
        val currentConfirmedCount: Int = row.getAs[Int]("currentConfirmedCount")
        val confirmedCount: Int = row.getAs[Int]("confirmedCount")
        val curedCount: Int = row.getAs[Int]("curedCount")
        val deadCount: Int = row.getAs[Int]("deadCount")
        val date: String = row.getAs[String]("date")

        ps = conn.prepareStatement(sql)
        ps.setInt(1,locationId)
        ps.setString(2,continents)
        ps.setString(3,provinceName)
        ps.setInt(4,currentConfirmedCount)
        ps.setInt(5,confirmedCount)
        ps.setInt(6,curedCount)
        ps.setInt(7,deadCount)
        ps.setString(8,date)
        ps.executeUpdate()
      }
    }
    ).outputMode("append").trigger(Trigger.ProcessingTime(0)).option("truncate",false).start()
    //Data01.writeStream.format("console").outputMode("complete")
    //  .trigger(Trigger.ProcessingTime(0))
    //  .option("truncate",false).start().awaitTermination()


    Data02.writeStream.format("console").outputMode("append").trigger(Trigger.ProcessingTime(0)).option("truncate",false).start().awaitTermination()
}}
