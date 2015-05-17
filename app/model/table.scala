package model

import play.api.libs.json.Json
import play.twirl.api.Html

case class FuneralSchedule(name: String, content: String)
object formats{
  implicit val tableFormat = Json.format[FuneralSchedule]
}