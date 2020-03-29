import math.abs

val tolerance = 0.0001
def isCloseEnough(x: Double, y: Double) =
  abs((x - y) / x) / x < tolerance
def fixedPoint(f: Double => Double)(firstGuess: Double) = {
  @scala.annotation.tailrec
  def iterate(guess: Double): Double = {
//    println("guess = " + guess)
    val next = f(guess)
    if (isCloseEnough(guess, next)) next
    else iterate(next)
  }

  iterate(firstGuess)
}
//fixedPoint(x => 1 + x / 2)(1)
/*
sqrt is the same as finding the fixed point of
y=>x/y (when y=x/y the solution is the same as sqrt())
 */
// This fails because of instability
//def sqrt(x: Double) = fixedPoint(y => x / y)(1)
// Increase stability by averaging succesive values
//def sqrt(x: Double) = fixedPoint(y => (y + x / y) / 2)(1)
//sqrt(2)
// generalize stability technique to it's own function
def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2
def sqrt(x:Double)=
  fixedPoint(averageDamp(y => x / y))(1)
sqrt(2)
