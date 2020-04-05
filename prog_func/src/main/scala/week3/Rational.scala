package week3

class Rational(x: Int, y: Int) {

  require(y != 0, "_denominator must be nonzero")

  // alternative constructor
  def this(x: Int) = this(x, 1)

  @scala.annotation.tailrec
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  private val g = gcd(x, y)

  def numer: Int = x / g

  def denom: Int = y / g

  def max(that: Rational): Rational =
    if (this < that) that else this

  def <(that: Rational): Boolean =
    this.numer * that.denom < that.numer * this.denom

  def +(that: Rational) =
    new Rational(numer * that.denom + that.numer * denom,
      denom * that.denom)

  def unary_- : Rational = new Rational(-numer, denom)

  def -(that: Rational): Rational =
    this + -that

  override def toString: String = numer + "/" + denom


}
