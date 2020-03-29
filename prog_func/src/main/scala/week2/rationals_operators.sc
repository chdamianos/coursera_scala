class Rational(x: Int, y: Int) {
  require(y != 0, "_denominator must be nonzero")

  // alternative constructor
  def this(x: Int) = this(x, 1)

  @scala.annotation.tailrec
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  private val g = gcd(x, y)

  def numer = x / g

  def denom = y / g

  def max(that: Rational) =
    if (this < that) that else this

  def < (that: Rational) =
    this.numer * that.denom < that.numer * this.denom

  def + (that: Rational) =
    new Rational(numer * that.denom + that.numer * denom,
      denom * that.denom)

  def unary_- : Rational = new Rational(-numer, denom)

  def - (that: Rational) =
    this + -that

  override def toString: String = numer + "/" + denom

}


//val x = new Rational(1, 2)
//x.numer
//x.denom
//val y = new Rational(2,3)
//x.add(y)
val x = new Rational(1, 3)
val y = new Rational(5, 7)
val z = new Rational(3, 2)
val zz = new Rational(6, 2)
x - y - z
x < y
x max y
new Rational(2)
val strange = new Rational(1, 0)
strange + strange
