val fruit: List[String] = List("apples", "oranges", "pears")
fruit.length
fruit.last
fruit.init
fruit take 2
fruit drop 1
fruit(0)

val fruit2: List[String] = List("strawberries", "melons", "watermelons")
fruit ++ fruit2
fruit.reverse
fruit updated(0, "newfruit")

fruit indexOf "pears"
fruit indexOf "dffgfdg"

fruit contains "pears"
fruit contains "dffgfdg"

def last[T](xs: List[T]): T = xs match {
  case List() => throw new Error("last of empty lists")
  case List(x) => x
  case y :: ys => last(ys)
}

last(fruit)

def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("init of empty lists")
  case List(x) => List()
  case y :: ys => y :: init(ys)
}

init(fruit)

def concatLame[T](xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
  case (List(), List()) => throw new Error("concat of empty lists")
  case (x :: Nil, y :: Nil) => List(x, y)
  case (List(), x :: Nil) => List(x)
  case (x :: Nil, List()) => List(x)
  case (x :: xs, y :: ys) => y :: x :: concatLame(ys, xs)
}

concatLame(fruit, fruit2)

def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
  case List() => ys
  case z :: zs => z :: concat(zs, ys)
}

concat(fruit, fruit2)

def reverse[T](xs: List[T]): List[T] = xs match {
  case List() => xs
  case y :: ys => reverse(ys) ++ List(y)
}

reverse(fruit)

//def removeAt[T](xs: List[T], n: Int): List[T] = (xs, n) match {
//  case (x :: Nil, n) => if (x!=xs(n)) List(x) else Nil
//  case (y :: ys, n) => if (y != xs(n)) y :: removeAt(ys, n) else removeAt(ys, n)
//}

def removeAt[T](n: Int, xs: List[T]): List[T] = (xs take n) ::: (xs drop n + 1)

removeAt(1,fruit)