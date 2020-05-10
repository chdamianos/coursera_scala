import scala.io.Source
import scala.jdk.CollectionConverters.getClass

val linuxwords = getClass.getResource("/linuxwords.txt").getPath
val in = Source.fromFile(linuxwords, "UTF-8")
val words = in.mkString.split("\\s+").toList

val mnem = Map('2' -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
  '6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")

val charCode: Map[Char, Char] = (for ((k, v) <- mnem; ltr <- v) yield ltr -> k).withDefaultValue(" ".charAt(0))
// e.g "Java" -> "5282"
def wordCode(word: String): String = {
  word.toUpperCase map charCode
}
wordCode("Java")

val wordsForNum: Map[String, Seq[String]] =
  words groupBy wordCode withDefaultValue Seq()

def encode(number: String): Set[List[String]] =
  if (number.isEmpty) Set(List())
  else {
    for {
      split <- 1 to number.length
      word <- wordsForNum(number take split)
      rest <- encode(number drop split)
    } yield word :: rest
  }.toSet

encode("7225247386")

def translate(number: String): Set[String] =
  encode(number) map (_ mkString " ")
translate("7225247386")

