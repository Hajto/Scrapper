import model.FuneralSchedule
import play.api.libs.json.Json
import scala.io.Source


var schedulesList: List[FuneralSchedule] = List()
case class Funeral(hour: String, who: String, age: String) {}

implicit val format = Json.format[Funeral]
implicit val funeralSchedule = Json.format[FuneralSchedule]

