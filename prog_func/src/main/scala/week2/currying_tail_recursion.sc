def time[R](block: => R): R = {
  val t0 = System.nanoTime()
  val result = block // call-by-name
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0) + "ns")
  result
}
val a1 = 1
val b1 = 20
/*
This example shows how to create abstractions using currying
*/
// product between a, b with tail recursion
def product_tail(f: Long => Long)(a: Long, b: Long): Long = {
  @scala.annotation.tailrec
  def loop(f: Long => Long)(acc: Long, a: Long, b: Long): Long = {
    if (a > b) acc
    else loop(f)(acc * f(a), a + 1, b)
  }

  loop(f)(acc = 1, a, b)

}
println("Tail recursion ans = " + product_tail(x => x)(a1, b1))
time {
  product_tail(x => x)(a1, b1)
}
// product between a, b
def product(f: Long => Long)(a: Long, b: Long): Long = {
  if (a > b) 1
  else f(a) * product(f)(a + 1, b)
}
println("Non tail recursion ans = " + product(x => x)(a1, b1))
time {
  product(x => x)(a1, b1)
}
// we can create a more abstract definition that combines the
// product between a,b and sum between a,b
// combine for production it will be *, for sum +
// zero -> 0 for sum, 1 for product (goes after `if (a > b) zero`)
def mapreduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int =
  if (a > b) zero
  else combine(f(a), mapreduce(f, combine, zero)(a + 1, b))
def mapreduce_tail(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
  //  if (a > b) zero
  //  else combine(f(a), mapreduce(f, combine, zero)(a + 1, b))
  @scala.annotation.tailrec
  def loop(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(acc: Int, a: Int, b: Int): Int = {
    if (a > b) acc
    else loop(f, combine, zero)(combine(acc, f(a)), a + 1, b)
  }

  loop(f, combine, zero)(acc = zero, a, b)
}

// using mapreduce to define sum and product
def product2(f: Int => Int)(a: Int, b: Int): Int = mapreduce(f, (x, y) => x * y, 1)(a, b)
product2(x => x * x)(3, 4)
def sum(f: Int => Int)(a: Int, b: Int): Int = mapreduce(f, (x, y) => x + y, 0)(a, b)
sum(x => x + x)(3, 4)
// using mapreduce to define sum and product
def product2_tail(f: Int => Int)(a: Int, b: Int): Int = mapreduce_tail(f, (x, y) => x * y, 1)(a, b)
product2_tail(x => x * x)(3, 4)
def sum_tail(f: Int => Int)(a: Int, b: Int): Int = mapreduce_tail(f, (x, y) => x + y, 0)(a, b)
sum_tail(x => x + x)(3, 4)

time {
  sum_tail(x => x + x)(3, 4000)
}
time {
  sum(x => x + x)(3, 4000)
}