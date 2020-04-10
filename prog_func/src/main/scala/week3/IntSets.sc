abstract class IntSet {
  def incl(s: Int): IntSet

  def contains(x: Int): Boolean

  def union(other: IntSet): IntSet
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true    // if x=elem -> i.e. set contains x
  // include an element
  def incl(x: Int): IntSet =
  // as we traverse the set we will reach an empty left or right
  // which will when call the empty `incl` method to create a new node
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this
  // termination logic:
  // every call to union is smaller than the set we start with
  // at some we reach zero, thus the empty set
  override def union(other: IntSet): IntSet =
    ((left union right) union other) incl elem

  override def toString: String = "{" + left + elem + right + "}"
}

class Empty extends IntSet {
  // an empty set doesn't contain anything
  def contains(x: Int): Boolean = false

  // if we include an element then we create an non Empty cell (node)
  // with two empty subtrees
  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)


  override def union(other: IntSet): IntSet = other

  override def toString: String = "."
}


val t1 = new NonEmpty(3, new Empty, new Empty)
val t2 = t1 incl 4
val t3 = t2 incl 3
val t4 = t3 incl 1