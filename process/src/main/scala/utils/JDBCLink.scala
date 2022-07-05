package utils

import org.apache.spark.sql.{ForeachWriter, Row}

import java.sql.{Connection, DriverManager, PreparedStatement}




abstract class JDBCLink(sql: String) extends ForeachWriter[Row] {
  var conn: Connection=_
  var ps: PreparedStatement=_
  def realProcess(str: String,row: Row)
  override def open(partitionId: Long, epochId: Long): Boolean = {
    conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/wmk_database","root","19990730wmk")
    true

  }
  override def process(value: Row): Unit = {
    realProcess(sql,value)
  }

  override def close(errorOrNull: Throwable): Unit ={
    if (conn!=null){
      conn.close()
    }
    if (ps!=null){
      ps.close()
    }
  }
}
