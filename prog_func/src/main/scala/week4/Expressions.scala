package week4

trait Expr {
  def isNumber: Boolean

  def isSum: Boolean

  def numValue: Int

  def leftOp: Expr

  def rightOp: Expr

  def eval: Int

  def evalPatternMatching: Int = this match {
    case Number(n) => n
    case Sum(e1, e2) => e1.evalPatternMatching + e2.evalPatternMatching

  }

  def show: String = this match {
    case Number(x) => x.toString
    case Sum(e1, e2) => e1.show + "+" + e2.show
  }

}

case class Number(n: Int) extends Expr {
  override def isNumber: Boolean = true

  override def isSum: Boolean = false

  override def numValue: Int = n

  override def leftOp: Expr = throw new Error("Number.leftOp")

  override def rightOp: Expr = throw new Error("Number.rightOp")

  override def eval: Int = n

  override def toString: String = n.toString
}

case class Sum(e1: Expr, e2: Expr) extends Expr {
  override def isNumber: Boolean = false

  override def isSum: Boolean = true

  override def numValue: Int = throw new Error("Sum.numValue")

  override def leftOp: Expr = e1

  override def rightOp: Expr = e2

  override def eval: Int = e1.eval + e2.eval
}

object test_expr {
  def main(args: Array[String]): Unit = {
    val one = Number(1) // we don't need new Number(1) since Number is a case class
    val two = Number(2)
    val sum = Sum(one, two)
    println(sum.eval)
    println(sum.evalPatternMatching)
    println(sum.show)
    println(one.show)
  }
}