class Poly(val _terms: Map[Int, Double]) {
  val terms = _terms withDefaultValue 0.0

  /*Note two different was to add this to other
  * `adjust` was an exercise
  * `adjustCourse` is what was presented in the course
  * */
  def +(other: Poly) = new Poly(terms ++ (other.terms map adjust))

  /*this is wrong because if terms and other.terms have the same key
  * the value in other.terms will shadow the terms value*/
  def addWrong(other: Poly) = new Poly(terms ++ other.terms)

  def addCourse(other: Poly) = new Poly(terms ++ (other.terms map adjustCourse))

  def adjust(term: (Int, Double)): (Int, Double) = {
    if (terms.keySet.contains(term._1)) (term._1, term._2 + terms(term._1))
    else (term._1, term._2)
  }

  def adjustCourse(term: (Int, Double)): (Int, Double) = {
    val (exp, coeff) = term
    exp -> (coeff + terms(exp))
  }


  override def toString: String = {
    (for ((exp, coeff) <- terms.toList.sortWith(_._1 < _._1)) yield coeff + "x^" + exp) mkString "+"
  }
}

val p1 = new Poly(Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.2))
val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))
p1 addWrong p2
p1 addCourse p2
p1 + p2