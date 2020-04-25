def msort[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
  def merge(xs: List[T], ys: List[T]): List[T] =
    (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) => if (lt(x, y)) x :: merge(xs1, ys)
      else y :: merge(xs, ys1)
    }

  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msort(fst)(lt), msort(snd)(lt))
  }

}

def msortOrd[T](xs: List[T])(ord: Ordering[T]): List[T] = {
  def merge(xs: List[T], ys: List[T]): List[T] =
    (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) =>
        if (ord.lt(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }

  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msortOrd(fst)(ord), msortOrd(snd)(ord))
  }

}

def msortOrdImpl[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
  def merge(xs: List[T], ys: List[T]): List[T] =
    (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) =>
        if (ord.lt(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
    }

  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msortOrdImpl(fst), msortOrdImpl(snd))
  }

}

msort(List(3, 1, 2))((x: Int, y: Int) => x < y)
msort(List(2))((x: Int, y: Int) => x < y)
msort(List())((x: Int, y: Int) => x < y)
msort(List("b", "a", "z"))((x: String, y: String) => x < y)
val fruits: List[String] = List("oranges", "apples", "pears")
// sort in lexicographical order
msort(fruits)((x: String, y: String) => x.compareTo(y) < 0)
msort(fruits)((x: String, y: String) => x < y)

msortOrd(List(3, 1, 2))(Ordering.Int)
msortOrd(List(2))(Ordering.Int)
msortOrd(List())(Ordering.Int)
msortOrd(List("b", "a", "z"))(Ordering.String)
val fruits1: List[String] = List("oranges", "apples", "pears")
// sort in lexicographical order
msortOrd(fruits1)(Ordering.String)
msortOrd(fruits1)(Ordering.String)

msortOrdImpl(List(3, 1, 2))
msortOrdImpl(List(2))
msortOrdImpl(List())
msortOrdImpl(List("b", "a", "z"))
val fruits2: List[String] = List("oranges", "apples", "pears")
// sort in lexicographical order
msortOrdImpl(fruits2)
msortOrdImpl(fruits2)

