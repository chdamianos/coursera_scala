def scaleList(xs: List[Double], factor: Double): List[Double] = xs match {
  case Nil => xs
  case y :: ys => y * factor :: scaleList(ys, factor)
}

scaleList(List(2.5, 3, 6), 2)

def scaleListMap(xs: List[Double], factor: Double): List[Double] = {
  xs map (x => x * factor)
}

scaleListMap(List(2.5, 3, 6), 2)

def squareList(xs: List[Double]): List[Double] = xs match {
  case Nil => xs
  case y :: ys => y * y :: squareList(ys)
}

squareList(List(2.5, 3, 6))

def squareListMap(xs: List[Double]): List[Double] = {
  xs map (x => x * x)
}

squareListMap(List(2.5, 3, 6))

def posElements(xs: List[Int]): List[Int] = xs match {
  case Nil => xs
  case y :: ys => if (y > 0) y :: posElements(ys) else posElements(ys)
}

posElements(List(-10, 2, -2, 3, 6))

def posElementsMap(xs: List[Int]): List[Int] = {
  xs filter (x => x > 0)
}

posElementsMap(List(-10, 2, -2, 3, 6))

val nums = List(2, -4, 5, 7, 1)
val fruits = List("apple", "pineapple", "orange", "banana")

nums filter (x => x > 0)
nums filterNot (x => x > 0)
nums partition (x => x > 0)
nums takeWhile (x => x > 0)
nums dropWhile (x => x > 0)
nums span (x => x > 0)

def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case x :: Nil => List(x) :: Nil
  case x :: _ =>
    val (first, rest) = xs span (y => y == x)
    first :: pack(rest)
}
val tst = List("a", "a", "a", "b", "c", "c", "a")
pack(tst)

def encode[T](xs: List[T]): List[(T, Int)] = xs match {
  case Nil => Nil
  case x :: Nil => List((x, 1))
  case _ :: _ => pack(xs) map (x => (x.head, x.length))
}

encode(tst)