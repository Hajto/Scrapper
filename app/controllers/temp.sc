import scala.io.Source

val source = Source.fromURL("http://zck.krakow.pl/?pageId=16").mkString
val regex = "(?s)<table>.+?(Cmentarz.+?)<.+?</table>".r
val our = regex findAllIn source
our.foreach{
  r:String => println(r)
}