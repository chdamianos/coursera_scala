package week4

// Peano numbers
abstract class Nat {
  def isZero: Boolean

  def predecessor: Nat

  def succesor: Nat = new Succ(this)

  def +(that: Nat): Nat

  def -(that: Nat): Nat
}

object Zero extends Nat {
  def isZero: Boolean = true

  override def predecessor: Nat = throw new Error("0.predecessor")

  override def succesor: Nat = new Succ(this)

  def +(that: Nat): Nat = that

  def -(that: Nat): Nat = if (that.isZero) this else throw new Error("0.predecessor")
}

class Succ(n: Nat) extends Nat {
  def isZero = false

  override def predecessor: Nat = n

  def +(that: Nat): Nat = new Succ(n + that)

  def -(that: Nat): Nat = if (that.isZero) this else n - that.predecessor
}

object main_run {
  def main(args: Array[String]): Unit = {
    val zero = Zero
    val one = new Succ(zero)
    val two = new Succ(one)
    println(zero+one+two)
  }
}