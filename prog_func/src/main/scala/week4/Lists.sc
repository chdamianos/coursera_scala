val fruit: List[String] = List("apples", "oranges", "pears")
val diag3: List[List[Int]] = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
val empty: List[Nothing] = List()
// all lists in scala constructed from the empty list Nil and the construcion operation ::
val fruit2 = "apples" :: ("oranges" :: ("pears" :: Nil))
val fruit3 = "apples" :: "oranges" :: "pears" :: Nil
// operators endingi n : are seens as method call of the right-hand operand
val fruit4 = Nil.::("apples").::("oranges").::("pears")
// fundamental operations on list based on which all others are defined
fruit.head
fruit.tail.head
diag3.head
diag3.isEmpty
empty.head
// List patterns
