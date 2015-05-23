package model

import play.api.libs.json.Json
import play.twirl.api.Html

case class FuneralSchedule(name: String, content: List[Funeral])
case class Funeral(hour: String, who: String, age: String)
object formats{
  implicit val funeralFormat = Json.format[Funeral]
  implicit val tableFormat = Json.format[FuneralSchedule]
}