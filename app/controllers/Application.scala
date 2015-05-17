package controllers

import java.util.regex.Pattern

import play.api._
import play.api.mvc._
import play.twirl.api.Html

import scala.io.Source

object Application extends Controller {

  def index = Action {
    val source = Source.fromURL("http://zck.krakow.pl/?pageId=16").mkString
    val regex = "(?s)<table>.+?(Cmentarz.+?)<.+?</table>".r

    val out = regex.findAllIn(source).matchData
    /*foreach{ elem =>
      println(elem.group(1))
    }*/

    Ok(views.html.index(Html(out.mkString)))
  }

}