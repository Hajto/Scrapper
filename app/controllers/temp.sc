import model.FuneralSchedule

import scala.concurrent.Future
import scala.io.Source

val dateToCheck = "2015-05-14"
val source = Source.fromURL("http://zck.krakow.pl/?pageId=16&date=" + dateToCheck).mkString
val regex = "(?s)<table>.+?(Cmentarz.+?)<.+?</table>".r

var thing = List[FuneralSchedule]()

val out = regex.findAllIn(source).matchData foreach { elem =>
 thing ::= FuneralSchedule(elem.group(1), dateToCheck, elem.group(0))
}
thing