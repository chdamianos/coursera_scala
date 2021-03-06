package funsets

/**
 * 2. Purely Functional Sets.
 */
trait FunSets extends FunSetsInterface {
  /**
   * We represent a set by its characteristic function, i.e.
   * its `contains` predicate.
   */
  override type FunSet = Int => Boolean

  /**
   * Indicates whether a set contains a given element.
   */
  def contains(s: FunSet, elem: Int): Boolean = s(elem)

  /**
   * Returns the set of the one given element.
   */
  def singletonSet(elem: Int): FunSet = { elem2: Int => elem == elem2 }


  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   */
  def union(s: FunSet, t: FunSet): FunSet =
    (x: Int) =>
      contains(s, x) || contains(t, x)

  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` and `t`.
   */
  def intersect(s: FunSet, t: FunSet): FunSet =
    (x: Int) =>
      contains(s, x) && contains(t, x)

  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
  def diff(s: FunSet, t: FunSet): FunSet =
    (x: Int) =>
      (contains(s, x) && !contains(t, x)) || (!contains(s, x) && contains(t, x))

  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: FunSet, p: Int => Boolean): FunSet =
    (x: Int) => p(x)

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: FunSet, p: Int => Boolean): Boolean = {
    @scala.annotation.tailrec
    def iter(a: Int): Boolean = {
      if (a >= bound) true // stop condition
      // if a is in s and also a is not in s when condition applies then not all elements satisfy p
      else if (contains(s, a) && !contains(filter(s, p), a)) false
      else iter(a + 1) // this "feeds"/increments the recursion
    }
    // The iteration starts at the lower bound, this is the "seed" of the recursion
    // this is just to make sure recursion terminates, (bound could be any integer (within computation limits))
    iter(-bound)
  }

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: FunSet, p: Int => Boolean): Boolean = {
    @scala.annotation.tailrec
    def iter(a: Int): Boolean = {
      if (a >= bound) false // stop condition, if it's reached then no element of s satisfies p
      //  if a is in s and also a is in s when condition applies then at least one element satisfies p
      else if (contains(s, a) && contains(filter(s, p), a)) true
      else iter(a + 1) // this "feeds"/increments the recursion
    }
    // The iteration starts at the lower bound, this is the "seed" of the recursion
    // this is just to make sure recursion terminates, (bound could be any integer (within computation limits))
    iter(-bound)
  }


  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
  def map(s: FunSet, f: Int => Int): FunSet = {
    // the transformation needs to be within the bounds for this to work
    { elem: Int => exists(s, { elem2: Int => f(elem2) == elem }) }
  }

  /**
   * Displays the contents of a set
   */
  def toString(s: FunSet): String = {

    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: FunSet): Unit = {
    println(toString(s))
  }
}

object FunSets extends FunSets
