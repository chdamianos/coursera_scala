// Given a positive integer n, find all pairs of positive
// integers i and j with 1 <= j < i < n such that i+j is prime
val n = 7
(1 until n) map (i =>
  (1 until i) map (j => (i, j)))
((1 until n) map (i =>
  (1 until i) map (j => (i, j)))).flatten
(1 until n).flatMap(i =>
  (1 until i) map (j => (i, j)))
def isPrime(n: Int) = (2 until n) forall (n % _ != 0)
(1 until n).flatMap(i =>
  (1 until i) map (j => (i, j))) filter (pair =>
  isPrime(pair._1 + pair._2))
// for expressions
for {
  i <- 1 until n
  j <- 1 until i
  if isPrime(i + j)
} yield (i, j)
def scalaProduct(xs: List[Double], ys: List[Double]): Double =
  (for ((x, y) <- xs zip ys) yield x * y).sum