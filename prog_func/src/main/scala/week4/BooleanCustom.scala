package week4

abstract class BooleanCustom {


  // this method can be viewed as a transformation of if (cond) t else et
  // cond.ifThenElse(tt,et)
  def ifThenElse[T](then_part: => T, else_part: => T): T

  // if the Boolean itself (x) is true the then_part (x) is returned (i.e. true)
  // if the Boolean itself (x) is false then the second argument is returned (i.e. false)
  // 1. if we do true_custom.&&(false_custom) the x is false_custom and the ifThenElse comes from true_custom
  // this means that the ifThenElse will return the then part which is x (i.e. false_custom)
  // 2. if we do true_custom.&&(true_custom) the x is true_custom and the ifThenElse comes from true_custom
  // this means that the ifThenElse will return the then part which is x (i.e. true_custom)
  // 3. if we do false_custom.&&(true_custom) the x is true_custom and the ifThenElse comes from false_custom
  // this means that the ifThenElse will return the else part which is false_custom
  // 4. if we do false_custom.&&(false_custom) the x is false_custom and the ifThenElse comes from false_custom
  // this means that the ifThenElse will return the else part which is false_custom
  // for scenarios 3, 4 it doesn't matter what x is since what will be returned is the else part
  // since false_custom is `this` and that's how its ifThenElse behaves
  // for scenarios 1, 2 the x will always be returned (since `this` is true_custom)
  // which will result in true if x is true_custom and false if x is false_custom
  def &&(x: => BooleanCustom): Any = ifThenElse(x, false_custom)

  // if the Boolean itself (x) is true then the result is immediately true (LHS argument)
  // if the Boolean itself (x) is false then x (RHS argument) is returned
  def ||(x: => BooleanCustom): Any = ifThenElse(true_custom, x)

  // negation operation
  // if the Boolean itself is true we return false (LHS argument)
  // if the Boolean itself is false we return true (RHS argument)
  def unary_! : Any = ifThenElse(false_custom, true_custom)

  // if the argument is true then the result is true
  // if the argument is false then the result is true
  // the result of the test `==` is the value of the argument
  def ==(x: BooleanCustom): Any = ifThenElse(x, x.unary_!)

  def !=(x: BooleanCustom): Any = ifThenElse(x.unary_!, x)

  def <(x: BooleanCustom): BooleanCustom = ifThenElse(false_custom, x)


}

object true_custom extends BooleanCustom {
  override def ifThenElse[T](then_part: => T, else_part: => T): T = then_part

  override def toString: String = "TRUE"
}

object false_custom extends BooleanCustom {
  override def ifThenElse[T](then_part: => T, else_part: => T): T = else_part

  override def toString: String = "FALSE"
}

object run_some_stuff {
  def main(args: Array[String]): Unit = {
    val t = true_custom
    val f = false_custom
    println(t.&&(f))
  }
}
