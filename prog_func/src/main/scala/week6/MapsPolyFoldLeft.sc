class Poly(val _terms: Map[Int, Double]) {
  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  val terms = _terms withDefaultValue 0.0

  /*same as List foldLeft `def sumFold(xs: List[Int]) = (xs foldLeft 0) (_ + _)`
  * I guess the behaviour is that (thisMap foldLeft otherMap)(func)
  * func(thisMap: Map[T,T], keyValPairOfOtherMap:(T,T)): Map[T,T] = {
  * <do some operations that will return a Map>
  * }
  * */
  def plus(other: Poly) = new Poly((terms foldLeft other.terms) (addTerm))

  def addTerm(thisTerms: Map[Int, Double], otherTerm: (Int, Double)): Map[Int, Double] = {
    val (exp, coeff) = otherTerm
    /*
    ms + (k -> v) :	The map containing all mappings of ms as well as the mapping k -> v from key k to value v
    for example Map(3->7, 4->5) + (3->11) >>> Map(3 -> 11, 4 -> 5)
    */
    thisTerms + (exp -> (coeff + thisTerms(exp)))
  }

  override def toString: String = {
    (for ((exp, coeff) <- terms.toList.sortWith(_._1 < _._1)) yield coeff + "x^" + exp) mkString "+"
  }
}

val p1 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2)
val p2 = new Poly(0 -> 3.0, 3 -> 7.0)
p1 plus p2