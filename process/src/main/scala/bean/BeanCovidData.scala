package bean

case class BeanCovidData(
  provinceName: String,//省会
  cityName: String,
  provinceShortName: String,//简称

  currentConfirmedCount: Int,//现确认数

  confirmedCount: Int,//以确诊人数

  suspectedCount: Int,//疑似
  curedCount: Int,//治愈人数

  deadCount: Int,//死亡人数

  comment: String,
  locationId: Int,
  statisticsData: String,
  cities: String,//城市
  provinceId: Int,
  date: String

  )
