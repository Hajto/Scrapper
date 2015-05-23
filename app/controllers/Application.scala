package controllers

import java.text.SimpleDateFormat
import java.util.Calendar

import model.{FuneralSchedule, Funeral}
import model.formats._
import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import play.twirl.api.Html
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import scala.io.Source

object Application extends Controller {

  def currentDayIndex = {
    index(getCurrentDate)
  }

  def index(data:String) = Action.async {
    downlaodData(data).map( element =>
      Ok(views.html.index(Html(Json.toJson(element).toString())))
    )
  }

  def getCurrentDate = {
    val today = Calendar.getInstance().getTime()
    new SimpleDateFormat("YYYY-MM-dd").format(today)
  }

  def clearStrings(s:String) = {
    val regex = "((class=\".+?\")|(id=\".+?\")|(style=\".+?\")|(\\n))"
    s.replaceAll(regex,"")
  }

  def getJsonedData(s: String) = Action.async {
    downlaodData(s).map( element =>
      Ok(Html(Json.toJson(element).toString()))
    )
  }

  def downlaodData(date: String): Future[List[FuneralSchedule]] = {
    def removeMarkers(s: String) = {
      s.replaceAll( """(</?\s?td\s?>)""", "")
    }

    Future[List[FuneralSchedule]] {
      var schedulesList: List[FuneralSchedule] = List()

      val source = Source.fromURL("http://zck.krakow.pl/?pageId=16&date=" + date).mkString
      val regex = "(?s)<table>.+?(Cmentarz.+?)<.+?</table>".r

      val out = regex.findAllIn(source).matchData foreach { table =>
        var jsonFeed: List[Funeral] = List()
        """<tr\s?>.+?</\s?tr>""".r.findAllIn(clearStrings(table.group(0))).matchData foreach { tr =>
          val cleanTR = tr.toString().replaceAll( """<td\s?>\s?</\s?td>""", "<td>Brak Danych</td>")
          val a #:: b #:: c #:: _ = """<td\s?>.+?</\s?td>""".r.findAllIn(cleanTR).toStream
          val feedObject = Funeral(removeMarkers(a), removeMarkers(b), removeMarkers(c))
          jsonFeed ::= feedObject
        }
        schedulesList ::= FuneralSchedule(table.group(1),jsonFeed.reverse)
      }

      schedulesList
    }
  }

}