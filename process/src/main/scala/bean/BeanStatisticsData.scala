package bean

case class BeanStatisticsData(
  var locationId: Int,
  var provinceShortName: String,

  var curedCount: Int,
  var currentConfirmedCount: Int,
  var currentConfirmedIncr: Int,
  var midDangerCount: Int,
  var confirmedCount: Int,
  var confirmedIncr: Int,
 var suspectedCount: Int,
  var suspectedCountIncr: Int,
  var highDangerCount: Int,
  var deadCount: Int,
  var deadIncr: Int,
  var curedIncr: Int,
  var dateId: String

                           )
