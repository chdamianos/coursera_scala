/*
Binary tree of integers
              _______node_______
              |                 |
      ____node left____         node right
      |                |
  EMPTY           node right1

 node contains an integer
 integer in right node>integer in left node
*/
abstract class IntSet {
  def incl(s: Int): IntSet

  def contains(x: Int): Boolean

  def union(other: IntSet): IntSet
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true    // if x=elem -> i.e. node contains x
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
  // to convince yourself do a simple example of union of two
  // sets with a node and two empty sets each
  // it works based on 1) The union of an empty set to another is the other
  // 2) the incl follows the rules of the binary tree
  override def union(other: IntSet): IntSet = {
    left.union(right).union(other).incl(elem)
  }
//    ((left union right) union other) incl elem
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