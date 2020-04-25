def sum(xs: List[Int]) = (0 :: xs) reduceLeft (_ + _)
def product(xs: List[Int]) = (1 :: xs) reduceLeft (_ * _)

val testList = List(1, 2, 3)
sum(testList)
product(testList)

def sumFold(xs: List[Int]) = (xs foldLeft 0) (_ + _)
def productFold(xs: List[Int]) = (xs foldLeft 1) (_ * _)
sumFold(testList)
productFold(testList)

def concatRight[T](xs: List[T], ys: List[T]): List[T] =
  (xs foldRight ys) (_ :: _)

val list1 = List("a","b","c")
val list2 = List("y","x","z")
concatRight(list1,list2)

def concatLeft[T](xs: List[T], ys: List[T]): List[T] =
  (xs foldLeft ys) (_ :: _)

concatRight(list1,list2)