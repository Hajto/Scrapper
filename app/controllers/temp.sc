import model.FuneralSchedule
import play.api.libs.json.Json
import scala.concurrent.Future
import scala.io.Source
import scala.xml.dtd.ContentModel._labelT

var date = "2015-05-05"
val source = Source.fromURL("http://zck.krakow.pl/?pageId=16&date=" + date).mkString
val regex = "(?s)<table>.+?(Cmentarz.+?)<.+?</table>".r
var thing: List[FuneralSchedule] = List()
var jsonFeed: List[Funeral] = List()
val regMatcher = "("

case class Funeral(hour: String, who: String, age: String)

//implicit val format = Json.format[Funeral]
val out = regex.findAllIn(source).matchData foreach { table =>
  thing ::= FuneralSchedule(table.group(1), clearStrings(table.group(0)))
  """<tr\s?>.+?</\s?tr>""".r.findAllIn(clearStrings(table.group(0))).matchData foreach { tr =>
    //TODO: Naprawic bo szlak trafia wydajnosc
    val temp = """<td\s?>.+?</\s?td>""".r.findAllIn(tr.group(0))
    //Eror
    Funeral(temp.subgroups:_*)
  }
  println("Koniec tabeli")
}
thing
//Json.toJson(jsonFeed)
println(removeMarkers("<td > <td> Marian Debil </ td>"))
def removeMarkers(s: String) = {
  s.replaceAll( """(</?\s?td\s?>)""", "")
}
def clearStrings(s: String) = {
  val regex = "((class=\".+?\")|(id=\".+?\")|(style=\".+?\")|(\\n))"
  s.replaceAll(regex, "")
}