// Vectors
val people = Vector("Bob", "James", "Peter")
val nums = Vector(1, 2, 3, -88)
def addElementFront(xs: Vector[Int], elem: Int): Vector[Int] =
  elem +: xs
def addElementBack(xs: Vector[Int], elem: Int): Vector[Int] =
  xs :+ elem
addElementFront(nums, 2)
addElementBack(nums, 2)
// Array String
val xs = Array(1, 2, 3, 44)
xs map (x => x * 2)
val s = "Hello World"
s filter (c => c.isUpper)
// Ranges
val r: Range = 1 until 5 // 1,2,3,4
val t: Range = 1 to 5 // 1,2,3,4,5
1 to 10 by 3 // 1,4,7,10
6 to 1 by -2 // 6,4,2
// seq operations
val s1 = "Hello World"
s1 exists (c => c.isUpper)
s1 forall (c => c.isUpper)
val pairs = List(1, 2, 3) zip s1
pairs.unzip
s1 flatMap (c => List('.', c))
val xs1 = Array(1, 2, 3, 44)
xs1.sum
xs1.product
