package controllers

import java.text.SimpleDateFormat
import java.util.Calendar

import model._
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
    Future[List[FuneralSchedule]] {
      val source = Source.fromURL("http://zck.krakow.pl/?pageId=16&date="+date).mkString
      val regex = "(?s)<table>.+?(Cmentarz.+?)<.+?</table>".r

      var thing: List[FuneralSchedule] = List()

      val out = regex.findAllIn(source).matchData foreach { elem =>
        thing ::= FuneralSchedule(elem.group(1),clearStrings(elem.group(0)))
      }
      thing
    }
  }

}