/*
This example shows how to create abstractions using currying
*/
// product between a, b
def product(f: Int => Int)(a: Int, b: Int): Int = {
  if (a > b) 1
  else f(a) * product(f)(a + 1, b)
}
product(x => x * x)(3, 4)
// we can use this definition of product to define factorial
def fact(n: Int) = product(x => x)(1, n)
fact(5)
// we can create a more abstract definition that combines the
// product between a,b and sum between a,b
// combine for production it will be *, for sum +
// zero -> 0 for sum, 1 for product (goes after `if (a > b) zero`)
def mapreduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int =
  if (a > b) zero
  else combine(f(a), mapreduce(f, combine, zero)(a + 1, b))
// using mapreduce to define sum and product
def product2(f: Int => Int)(a: Int, b: Int): Int = mapreduce(f, (x, y) => x * y, 1)(a, b)
product2(x => x * x)(3, 4)
def sum(f: Int => Int)(a: Int, b: Int): Int = mapreduce(f, (x, y) => x + y, 0)(a, b)
sum(x => x + x)(3, 4)