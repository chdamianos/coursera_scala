def msortPair(xs: List[Int]): List[Int] = {
  def merge(xs: List[Int], ys: List[Int]): List[Int] =
    (xs, ys) match {
      case (Nil, _) => ys
      case (_, Nil) => xs
      case (x :: xs1, y :: ys1) => if (x < y) x :: merge(xs1, ys)
      else y :: merge(xs, ys1)
    }

  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msortPair(fst), msortPair(snd))
  }
}

def msort(xs: List[Int]): List[Int] = {
  def merge(xs: List[Int], ys: List[Int]): List[Int] =
    xs match {
      case Nil => ys
      case x :: xs1 =>
        ys match {
          case Nil => xs
          case y :: ys1 =>
            if (x < y) x :: merge(xs1, ys)
            else y :: merge(xs, ys1)
        }
    }

  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}




msort(List(3, 1, 2))
msort(List(2))
msort(List())

msortPair(List(3, 1, 2))
msortPair(List(2))
msortPair(List())