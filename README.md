# Table of Contents  
[Week1](#week1)  
* [Call-by-name(CBN) and call-by-value(CBV)](#Call-by-name(CBN)-and-call-by-value(CBV))  
* [Recursion](#Recursion) 
* [Tail Recursion](#Tail-Recursion) 

[Week2](#week2) 
* [High-order functions](#High-order-functions)  
* [Currying](#Currying) 
    * [Example](#Example) 

[Week3](#week3)  
* [Class hierarchies](#Class-hierarchies)  
    * [Override](#Override) 
* [Traits](#Traits) 
* [Exceptions](#Exceptions)

[Week4](#week4) 
* [Primitive types as objects](#Primitive-types-as-objects)  
* [Subtypes and generics](#Subtypes-and-generics) 
* [Pattern matching](#Pattern-matching)
* [Lists](#Lists)

[Week5](#week5) 
* [More on Lists](#More-on-Lists)  
* [Implementation of `List` methods](#Implementation-of-`List`-methods) 
* [Pairs and Tuples ](#Pairs-and-Tuples )
* [Parameterization](#Parameterization)
* [High-Order List Functions](#High-Order-List-Functions)
    * [`map`](#`map`)
    * [`filter`](#`filter`)
    * [Other `List` methods that extract sublists based on a predicate](#Other-`List`-methods-that-extract-sublists-based-on-a-predicate)
    * [`Examples`](#Examples)
* [List reduce methods](#List-reduce-methods)
    * [`reduceLeft`](#`reduceLeft`)
    * [`foldLeft`](#`foldLeft`)
    * [Implementation of `foldLeft` and `reduceLeft`](#Implementation-of-`foldLeft`-and-`reduceLeft`)
    * [Implementation of `foldRight` and `reduceRight`](#Implementation-of-`foldRight`-and-`reduceRight`)
* [Induction methods](#Induction-methods)
    * [Laws of `concat`](#Laws-of-`concat`)
    * [Structural induction](#Structural-induction)    
        * [`concat` example](#`concat`-example)
[Week6](#week6) 
* [Other Collections](#Other-Collections)  
    * [Hierarchy](#Hierarchy)  
    * [Vector](#Vector) 
    * [Arrays and Strings](#Arrays-and-Strings)    
    * [Ranges](#Ranges) 
    * [Seq operations](#Seq-operations)    
        * [exists](#exists)    
        * [forall](#forall)        
        * [zip](#zip)        
        * [unzip](#unzip)        
        * [flatMap](#flatMap)        
        * [sum](#sum)        
        * [product](#product) 
        * [max](#max)  
        * [min](#min)                     


# Week1
## Call-by-name(CBN) and call-by-value(CBV)
CBV has the advantage of evaluating every argument once
CBN has the advantage of not evaluating an argument is not used 
## Recursion
* First define a function that computes one iteration step using recursion
  * Example for Newton's method of finding square roots
    ```scala
    def sqrtIter(guess: Double): Double =
        if (isGoodEnough(guess)) guess else sqrtIter(improve(guess))
    ```   
* Then write the functions to implement the step
    ```scala
    def isGoodEnough(guess: Double): Boolean =
        abs((guess * guess) - x) / x < 0.001

    def improve(guess: Double): Double =
        (guess + x / guess) / 2.0
    ``` 
## Tail Recursion
If a function calls itself as its last action then the function's stack frame can be reused. This is the functional form of a loop and executes with the same efficiency. 
  * non-tail recursion (because of `n*factorial(n-1)`)
    ```scala
    def factorial(n: Int): Int =
        if (n==0) 1 else n * factorial(n-1)
    ``` 
  * tail recursion 
    * function `loop` calls itself as its last action so it's tail recursive
    ```scala
    def factorial(n: Int): Int = {
    @scala.annotation.tailrec
    def loop(acc: Int, n: Int): Int = {
        if (n == 0) acc
        else loop(acc * n, n - 1)
    }

    loop(1, n)
    }
    ```  
    * `acc`
        * The important argument is the accumulator (`acc`) which is initiated at `1` since this is the value of factorial when `n==0`
        * The factorial "recursion" is then established by `loop` calling itself reducing n by one 
        * The termination condition is `if (n == 0) acc`
        * For `n=3` `loop` executes as:
            * `loop(1,3)`
                * `if (3==0) then 1 else loop(1*3,2)`
            * `loop(1*3,2)`
                * `if (2==0) then 1*3 else loop(1*3*2,1)`
            * `loop(1*3*2,1)`
                * `if (1==0) then 1*3*2 else loop(1*3*2*1,0)`
            * `loop(1*3*2*1,0)`
                * `if (0==0) then 1*3*2*1 else loop(1*3*2*1*0,-1)`
            * returns the answer `1*3*2*1` which is `3!`. Note the importance of starting `acc=1` so that `1*3*2*1` is the same as `3*2*1`
The intention of tail recursion is to avoid very deep recursions and avoid stack overflow exceptions. If deep recursions are not a problem write your function as clearly as possible without worrying about tail recursion.
# Week2
## High-order functions
Can take functions as arguments and can return functions
## Currying 
Uses high order functions and patterns in programming to write elegant programs
### Example
* A function that calculates the product between two numbers 
    ```scala
    def product(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 1
    else f(a) * product(f)(a + 1, b)
    }
    ```
    * Note this is non-tail recursive
    * The termination condition is `if (a > b) 1`
    * `product(x => x * x)(3, 4)`
        * `if (3>4) 1 else 3*3*product(x => x * x)(3+1, 4)`
    * `3*3*product(x => x * x)(3+1, 4)`
        * `if (4>4) 1 else (3+1)*(3+1)*product(x => x * x)(3+1+1, 4)`
    * `3*3*(3+1)*(3+1)*product(x => x * x)(3+1+1, 4)`
        * `if (5>4) 1 else (3+1+1)*(3+1+1)*product(x => x * x)(3+1+1+1, 4)`
    * `3*3*(3+1)*(3+1)*1=144`
    * Tail recursive version
        ```scala
        def product_tail(f: Long => Long)(a: Long, b: Long): Long = {
        @scala.annotation.tailrec
        def loop(f: Long => Long)(acc: Long, a: Long, b: Long): Long = {
            if (a > b) acc
            else loop(f)(acc * f(a), a + 1, b)
        }

        loop(f)(acc = 1, a, b)

        }
        ```
* A more general version can be created
    ```scala
    def mapreduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int =
    if (a > b) zero
    else combine(f(a), mapreduce(f, combine, zero)(a + 1, b))
    ```
    * By defining how to combine the results using `combine` we can generalize
    * For example the product can be expressed as 
        ```scala
        def product2(f: Int => Int)(a: Int, b: Int): Int = mapreduce(f, (x, y) => x * y, 1)(a, b)
        ```
    * a tail recursive version of mapreduce
        ```scala
        def mapreduce_tail(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
        //  if (a > b) zero
        //  else combine(f(a), mapreduce(f, combine, zero)(a + 1, b))
        @scala.annotation.tailrec
        def loop(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(acc: Int, a: Int, b: Int): Int = {
            if (a > b) acc
            else loop(f, combine, zero)(combine(acc, f(a)), a + 1, b)
        }

        loop(f, combine, zero)(acc = zero, a, b)
        }
        ```
# Week3
## Class hierarchies
Abstract class can contain methods not implemented that the subclasses need to implement
* Example for abstract class with methods that need to be implemented
    ```scala
    abstract class IntSet {
    def incl(s: Int): IntSet

    def contains(x: Int): Boolean

    def union(other: IntSet): IntSet
    }
    ```
* Any class that inherits from `IntSet` needs to implement the methods not implemented in `IntSet`
    ```scala
    class Empty extends IntSet {
    def contains(x: Int): Boolean = false
    def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
    override def union(other: IntSet): IntSet = other
    override def toString: String = "."
    }
    ```
    * `toString` is inherited from a higher level class that all classes inherit from 
### Override
Class methods and definitions can be overriden 
```scala
abstract class Base {
  def foo = 1
  def bar: Int
}

class Sub extends Base {
  override def foo: Int = 2
  def bar = 3
}
```
## Traits
* Advantages
    * Traits are like abstract classes but a class can inherit multiple traits
    * Traits are like Java interfaces but they can also containt fields and concrete methods (i.e. implemented methods)
* Disadvantages
    * Traits cannot have values parameters, only classes can
        * For example `numer` and `denom` 
            ```scala
            class Rational(x: Int, y: Int) {
            def numer = x
            def denom = y
            }
            ```

## Exceptions
Example
```scala
package week3
import java.util.NoSuchElementException

trait List[T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

class Nil[T] extends List[T] {
  def isEmpty = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.head")
}
```
# Week4
## Primitive types as objects
* The start of this week wants to show how primitive types can actually be objects
* Example for `Boolean` 
    ```scala

    abstract class Boolean {
    def ifThenElse[T](then_part: => T, else_part: => T): T
    def &&(x: => Boolean): Any = ifThenElse(x, false_custom)
    ```
    * the `ifThenElse` takes two arguments and will return one back, either the `then_part` or `else_part`
    * The `and` `&&` operator is also defined
    * OK, now let's implement the `true` and `false` booleans from the `Boolean` abstract class
        ```scala
        object true_custom extends Boolean {
        override def ifThenElse[T](then_part: => T, else_part: => T : T = then_part
        override def toString: String = "TRUE"
        }

        object false_custom extends Boolean {
        override def ifThenElse[T](then_part: => T, else_part: => T): T = else_part
        override def toString: String = "FALSE"
        }
        ```
    * the key part is to understand the imlpementations of `ifThenElse` 
        * for `true_custom` the `then_part` part is returned 
            * this makes sense since when a condition if `true` we return the `then` part `if (cond) 1 else 0` returns 1 if `cond` is `true` 
        * for `false_custom` the `else_part` part is returned 
            * this makes sense since when a condition if `false` we return the `else` part `if (cond) 1 else 0` returns 0 if `cond` is `false` 
    * OK, let's try it out
        ```scala
        object run_some_stuff {
        def main(args: Array[String]): Unit ={
            val t = true_custom
            val f = false_custom
            println(t.&&(f))
        }
        }
        ```
        * `t.&&(f)` (or equivalently `t&&f`)
            1. the execution starts with `this` being a `true_custom` implementation of `Boolean` 
            2. this means that `ifThenElse` will return the the `then_part` 
            3. in this example the `then_part` is a `false_custom` object 
            4. so the result is correctly `false_custom` (`false_custom.toString()="FALSE"`)
    * All combination examples
        1. if we do `true_custom.&&(false_custom)` the `x` is `false_custom` and the `ifThenElse` comes from `true_custom`
        this means that the `ifThenElse` will return the then part which is `x` (i.e. `false_custom`)
        2. if we do `true_custom.&&(true_custom)` the `x` is `true_custom` and the `ifThenElse` comes from `true_custom`
        this means that the `ifThenElse` will return the then part which is `x` (i.e. `true_custom`)
        3. if we do `false_custom.&&(true_custom)` the `x` is `true_custom` and the `ifThenElse` comes from `false_custom`
        this means that the `ifThenElse` will return the else part which is `false_custom`
        4. if we do `false_custom.&&(false_custom)` the `x` is `false_custom` and the `ifThenElse` comes from `false_custom`
        this means that the `ifThenElse` will return the else part which is `false_custom`
        * for scenarios 3, 4 it doesn't matter what `x` is since what will be returned is the else part
        since `false_custom` is `this` and that's how its `ifThenElse` behaves
        * for scenarios 1, 2 the `x` will always be returned (since `this` is `true_custom`)
        which will result in true if `x` is `true_custom` and false if `x` is `false_custom` 
## Subtypes and generics
* A function can be parameterized with `<:`, an upper bound 
* Example
    ```scala
    def assertAllPos[S <: IntSet](r:S) : S = ???
    ```
    `S` is a subtype of `IntSet`, a subtype needs to conform to `IntSet`
* A function can also be parameterized with `>:`, an lower bound 
    * `S >: T` means that `S` is a supertype of `T`, or `T` is a subtype of `S`
* A function can also be parameterized with `>:` and `>:`, an interval bound 
    * `[S >: T1 <: T2]` means that `S` is a supertype of `T1` and a subtype of `T2`
* `covariant` means that if `S <: T` holds then also `List[S] <: List[T]` also holds
    * In general if `S <: T` an expression of type `S` should be substitutable wherever an expression of type `T` is used

## Pattern matching
Let's say there is a `Trait` and from that `Trait` there are a few sub-classes that extend it. Pattern matching is used to define functions in the `Trait` that would recognise (i.e. pattern match) the sub-classes and return different results each time.
* Example of `Trait` that we use to define expressions of addition
    ```scala
    trait Expr {
        def isNumber: Boolean
        def isSum: Boolean
        def numValue: Int
        def leftOp: Expr
        def rightOp: Expr
        def eval: Int
    }
    class Number(n: Int) extends Expr {
        override def isNumber: Boolean = true
        override def isSum: Boolean = false
        override def numValue: Int = n
        override def leftOp: Expr = throw new Error("Number.leftOp")
        override def rightOp: Expr = throw new Error("Number.rightOp")
        override def eval: Int = n
        override def toString: String = n.toString
    }
    class Sum(e1: Expr, e2: Expr) extends Expr {
        override def isNumber: Boolean = false
        override def isSum: Boolean = true
        override def numValue: Int = throw new Error("Sum.numValue")
        override def leftOp: Expr = e1
        override def rightOp: Expr = e2
        override def eval: Int = e1.eval + e2.eval
    }
    ```
    An example of using `Expr`
    ```scala
    object test_expr {
        def main(args: Array[String]): Unit = {
            val one = new Number(1) 
            val two = new Number(2)
            val sum = new Sum(one, two)
            println(sum.eval)
        }
    }    
    ```
    This will printout `3` 
    * Can we write the `eval` in `Expr` to save use from adding it to the subclasses?
    Yes using pattern matching
    We will need to make some changes to the code:
        1. The subclasses will start with the prefix `case`
        2. Add a new `eval` called `evalPatternMatching` to `Expr` 
        3. Once the subclasses start with `case` we don't need `new` to instantiate them
            ```scala
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
                    val one = Number(1) 
                    val two = Number(2)
                    val sum = Sum(one, two)
                    println(sum.eval)
                    println(sum.evalPatternMatching)
                }
            }
            ```
* Patterns can be constructed from:
    1. Constructors e.g. `Number`, `Sum`
    2. Variables e.g. `n`, `e1`, `e2`
    3. wildcard patterns e.g. `_`
        * `Number(_)` in case we don't want to use/care about the arguments of `Number` 
    4. constants e.g. `1`, `true` 
    * Rules
        1. Variables always start with a lowercase letter
        2. The same variable cannot be used more than once, i.e. `Sum(x,x)` is not allowed
        3. The names of constants begin with a capital letter
* Pattern matching (FP) vs implementing asbtract methods in sub-classes (OOP)
    * Are you more often creating sub-classes or methods?
        * More sub-classes => Use OOP
            * Local changes to sub-classes
        * More methods, class hierarchy is stable => FP
            * Local changes to methods in `Trait`
        * Choose the solution that results in changing the less code parts
## Lists
* Constructing Lists 
    * Using `List`
        ```scala
        val fruit: List[String] = List("apples", "oranges", "pears")
        val diag3: List[List[Int]] = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
        val empty: List[Nothing] = List()
        ```
    * Using `Cons` (`::`) and `Nil`
        ```scala
        val fruit2 = "apples" :: ("oranges" :: ("pears" :: Nil))
        val fruit3 = "apples" :: "oranges" :: "pears" :: Nil
        ```
        * operators ending in `:` are seens as method call of the right-hand operand
            ```scala
            val fruit4 = Nil.::("apples").::("oranges").::("pears")
            ```
            is equivalent to 
            ```scala
            val fruit4 = "apples" :: "oranges" :: "pears" :: Nil
            ```     
* Fundamental operations of Lists (all others are derived from them)
    ```scala
    fruit.head
    fruit.tail.head
    diag3.head
    diag3.isEmpty
    empty.head
    ```
* List patterns
    * Lists that start with 1 and then 2
        ```scala
        1 :: 2 :: xs
        ```
    * Lists of length 1
        ```scala
        x :: Nil
        ```
    * Lists of length 1 (alternative)
        ```scala
        List(x)
        ```
    * The empty List
        ```scala
        List()
    * A List than contains as only element another list that starts with 2
        ```scala
        List(2 :: xs)
        ```          
* List insert sort
    1. Sort the tail `List(7 ,3, 9, 2)` -> `List(2, 3, 9)`
    2. Insert the head in the right place `List(2, 3, 7, 9)`
    ```scala
    def isort(xs: List[Int]): List[Int] = xs match {
        case List() => List()
        case y :: ys => insert (y, isort(ys))

    }
    def insert(x:Int, List[Int]): List[Int] = xs match {
        case List() = List(x)
        case y :: ys => if (x<=y) x :: xs else y :: insert(x, ys)
    }
    ```
Time complexity : O(`N^2`)
# Week5
## More on Lists
* Sublists and element access
    * The number of elements of xs
        ```scala
        xs.length
        ```
    * The list's last element, exception if xs is empty.
        ```scala
        xs.last
        ```
    * A list consisting of all elements of xs except the last one, exception if xs is empty.
        ```scala
        xs.init
        ```
     * A list consisting of the first n elements of xs, or xs itself if it is shorter than n.
        ```scala
        xs take n
        ```   
     * The rest of the collection after taking n elements (or, written out, xs apply n).
        ```scala
        xs drop n
        ```      
     * The element of xs at index n
        ```scala
        xs(n)
        ```    
* Creating new lists
    * The list consisting of all elements of xs followed by all elements of ys
        ```scala
        xs ++ ys
        ```
    * The list containing the elements of xs in reversed order.
        ```scala
        xs.reverse
        ```
    * The list containing the same elements as xs, except at index n where it contains x.
        ```scala
        xs updated (n, x)
        ```
* Finding elements
    * The index of the first element in xs equal to x, or -1 if x does not appear in xs.
        ```scala
        xs indexOf x 
        ```
    * Boolean check
        ```scala
        xs contains x
        ```
## Implementation of `List` methods
* `last`
    ```scala
    def last[T](xs: List[T]): T = xs match {
        case List() => throw new Error("last of empty lists")
        case List(x) => x
        case y :: ys => last(ys)
    }
    ```
    * complexity `O(n)`
* `init`
    ```scala
    def init[T](xs: List[T]): List[T] = xs match {
        case List() => throw new Error("init of empty lists")
        case List(x) => List()
        case y :: ys => y :: init(ys)
    }
    ```
    * Build list from start to end with `case y :: ys => y :: init(ys)` 
    * Last element is ommited with `case List(x) => List()`
    * complexity `O(n)`
* `concat`
    ```scala
    def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
        case List() => ys
        case z :: zs => z :: concat(zs, ys)
    }
    ```
    * Start building from first list `xs` -> `z :: concat(zs, ys)` 
    * When `xs` list runs out then add the second list `case List() => ys`
    * complexity `O(n)` length of `xs`
* `reverse`
    ```scala
    def reverse[T](xs: List[T]): List[T] = xs match {
        case List() => xs
        case y :: ys => reverse(ys) ++ List(y)
    }
    ```
    * complexity `O(n^2)` 
* `removeAt`
    ```scala
    def removeAt[T](n: Int, xs: List[T]): List[T] = (xs take n) ::: (xs drop n + 1)
    }
    ```
## Pairs and Tuples
* Definition 
    ```scala
    case class Tuple2[T1, T2](_1: +T1, _2: +T2){
        override def toString = "(" + _1 + "," + _2 + ")"
    }
    ```
* We can use pairs and tuples pattern matching to make code more efficient
* For example use 
    ```scala
    val (label, value) = pair
    ```
    instead of 
    ```scala
    val label = pair._1
    val value = pair._2
    ```
* Another example use 
    ```scala
    def msort(xs: List[Int]): List[Int] = {
    def merge(xs: List[Int], ys: List[Int]): List[Int] =
        (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) => if (x < y) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
        }

    val n = xs.length / 2
    if (n == 0) xs
    else {
        val (fst, snd) = xs splitAt n
        merge(msort(fst), msort(snd))
    }
    }
    ```
    instead of 
    ```scala
    def msort(xs: List[Int]): List[Int] = {
    def merge(xs: List[Int], ys: List[Int]): List[Int] =
        xs match {
        case Nil => ys
        case x :: xs1 =>
            ys match {
            case Nil => xs
            case y :: ys1 =>
                if (x < y) x :: merge(xs1, ys)
                else y :: merge(xs, ys1)
            }
        }

    val n = xs.length / 2
    if (n == 0) xs
    else {
        val (fst, snd) = xs splitAt n
        merge(msort(fst), msort(snd))
    }
    }
    ```    
 ## Parameterization
 * The example of `msort` can be made more general and use for any parameter by using `T`
 * The problem is that the `<` comparison is not defined for any parameter 
 * The solution is to provide the comparison as a predicate
    ```scala
    def msort[T](xs: List[T])(lt: (T, T) => Boolean): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] =
        (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) => if (lt(x, y)) x :: merge(xs1, ys)
        else y :: merge(xs, ys1)
        }

    val n = xs.length / 2
    if (n == 0) xs
    else {
        val (fst, snd) = xs splitAt n
        merge(msort(fst)(lt), msort(snd)(lt))
    }

    }
    ```
    example usage 
    ```scala
    msort(List(3, 1, 2))((x: Int, y: Int) => x < y)
    msort(List("b", "a", "z"))((x: String, y: String) => x < y)
    val fruits: List[String] = List("oranges", "apples", "pears")
    // sort in lexicographical order
    msort(fruits)((x: String, y: String) => x.compareTo(y) < 0)
    msort(fruits)((x: String, y: String) => x < y)
    ```
* There is class already defined in scala that represents ordering which we could use `scala.math.Ordering[T]`
    ```scala
    def msortOrd[T](xs: List[T])(ord: Ordering[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] =
        (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) =>
            if (ord.lt(x, y)) x :: merge(xs1, ys)
            else y :: merge(xs, ys1)
        }

    val n = xs.length / 2
    if (n == 0) xs
    else {
        val (fst, snd) = xs splitAt n
        merge(msortOrd(fst)(ord), msortOrd(snd)(ord))
    }

    }
    ```
    example usage 
    ```scala
    msortOrd(List(3, 1, 2))(Ordering.Int)
    msortOrd(List(2))(Ordering.Int)
    msortOrd(List())(Ordering.Int)
    msortOrd(List("b", "a", "z"))(Ordering.String)
    val fruits1: List[String] = List("oranges", "apples", "pears")
    // sort in lexicographical order
    msortOrd(fruits1)(Ordering.String)
    msortOrd(fruits1)(Ordering.String)
    ```
* Code can be made even more consice if we define `ord` as an implicit parameter to be defined based on the type of `T`
    * In this case we don't need to define `ord` when calling the function
    ```scala
    def msortOrdImpl[T](xs: List[T])(implicit ord: Ordering[T]): List[T] = {
    def merge(xs: List[T], ys: List[T]): List[T] =
        (xs, ys) match {
        case (Nil, _) => ys
        case (_, Nil) => xs
        case (x :: xs1, y :: ys1) =>
            if (ord.lt(x, y)) x :: merge(xs1, ys)
            else y :: merge(xs, ys1)
        }

    val n = xs.length / 2
    if (n == 0) xs
    else {
        val (fst, snd) = xs splitAt n
        merge(msortOrdImpl(fst), msortOrdImpl(snd))
    }

    }
    ```
    example usage 
    ```scala
    msortOrdImpl(List(3, 1, 2))
    msortOrdImpl(List("b", "a", "z"))
    val fruits2: List[String] = List("oranges", "apples", "pears")
    // sort in lexicographical order
    msortOrdImpl(fruits2)
    msortOrdImpl(fruits2)
    ```
## High-Order List Functions
### `map`
* `map` is a method of `List` that can apply transforms to `List` elements
* For example we can use map to write 
    ```scala
    def scaleList(xs: List[Double], factor: Double): List[Double] = xs match {
    case Nil => xs
    case y :: ys => y * factor :: scaleList(ys, factor)
    }
    ```
    as
    ```scala
    def scaleListMap(xs: List[Double], factor: Double): List[Double] = {
    xs map (x => x * factor)
    }
    ```    
### `filter`
* `filter` is a method of `List` that can filter `List` elements
* For example we can use filter to write 
    ```scala
    def posElements(xs: List[Int]): List[Int] = xs match {
    case Nil => xs
    case y :: ys => if (y>0) y :: posElements(ys) else posElements(ys)
    }
    ```
    as
    ```scala
    def posElementsMap(xs: List[Int]): List[Int] =  {
    xs filter (x => x > 0)
    }
    ```  
### Other `List` methods that extract sublists based on a predicate
* `filterNot` same as` xs filter (x => !p(x))`
    * The list consisting of those elements of xs that do not satisfy the predicate p.
    * example
    ```scala
    val nums = List(2, -4, 5, 7, 1)
    nums filterNot (x => x > 0)
    """
    >>>
    List[Int] = List(-4)
    """
    ```
* `partition` same as `(xs filter p, xs filterNot p)`
    * example
    ```scala
    val nums = List(2, -4, 5, 7, 1)
    nums partition (x => x > 0)
    """
    >>>
    (List[Int], List[Int]) = (List(2, 5, 7, 1),List(-4))
    """
    ```
* `takeWhile` The longest prefix of list xs consisting of elements that all satisfy the predicate p
    * example
    ```scala
    val nums = List(2, -4, 5, 7, 1)
    nums takeWhile (x => x > 0)
    """
    >>>
    List[Int] = List(2)
    """
    ```
* `dropWhile` The remainder of the list xs after any leading elements satisfying p have been removed.
    * example
    ```scala
    val nums = List(2, -4, 5, 7, 1)
    nums dropWhile (x => x > 0)
    """
    >>>
    List[Int] = List(-4, 5, 7, 1)
    """
    ```
* `span` Same as `(xs takeWhile p, xs dropWhile p)` but
computed in a single traversal of the list xs.
    * example
    ```scala
    val nums = List(2, -4, 5, 7, 1)
    nums span (x => x > 0)
    """
    >>>
    (List[Int], List[Int]) = (List(2),List(-4, 5, 7, 1))
    """
    ```
#### Examples
* Pack consecutive elements of lists into sublists
    ```scala
    def pack[T](xs: List[T]): List[List[T]] = xs match {
    case Nil => Nil
    case x :: Nil => List(x) :: Nil
    case x :: _ =>
        val (first, rest) = xs span (y => y == x)
        first :: pack(rest)
    }
    val tst = List("a", "a", "a", "b", "c", "c", "a")
    pack(tst)
    """
    >>>
    List[List[String]] = List(List(a, a, a), List(b), List(c, c), List(a))
    """
    ```
* Run-length encoding of list
    ```scala
    def encode[T](xs: List[T]): List[(T, Int)] = xs match {
    case Nil => Nil
    case x :: Nil => List((x, 1))
    case _ :: _ => pack(xs) map (x => (x.head, x.length))
    }
    encode(tst)
    """
    >>>
    List[(String, Int)] = List((a,3), (b,1), (c,2), (a,1))
    """
    ```
## List reduce methods
### `reduceLeft`
* Inserts a given binary operator between adjacent elements of a list
    ```scala
    def sum(xs: List[Int]) = (0 :: xs) reduceLeft (_ + _ )
    def product(xs: List[Int]) = (1 :: xs) reduceLeft (_ * _ )
    ```
### `foldLeft`
* Like `reduceLeft` but takes an accumulator `z` as an additional parameter which is returned then when `foldLeft` is called on an empty list
    ```scala
    def sumFold(xs: List[Int]) = (xs foldLeft 0) (_ + _)
    def productFold(xs: List[Int]) = (xs foldLeft 1) (_ * _)
    ```
### Implementation of `foldLeft` and `reduceLeft`
```scala
abstract class List[T] {...
    def reduceLeft(op: (T, T) => T): T = this match {
        case Nil => throw new Error("Nil.reduceleft")
        case x :: xs >> (xs reduceLeft x)(op)
    }

    def foldLeft[U](z: U)(op: (U, T) => U): U = this match {
        case Nil => z
        case x :: xs -> (xs foldLeft op(z, x))(op)
    }
}
```
### Implementation of `foldRight` and `reduceRight`
* Same as `foldLeft` and `reduceLeft` when the `op` is associative and commutative 
```scala
abstract class List[T] {...
  def reduceRight(op: (T, T) => T): T = this match {
    case Nil => throw new Error("Nil.reduceleft")
    case x :: Nil => x
    case x :: xs >> op(x, xs reduceRight op)
  }

  def foldRight[U](z: U)(op: (U, T) => U): U = this match {
    case Nil => z
    case x :: xs -> op(x, (xs foldRight z)(op))
  }
}
```
* Example of `foldRight` working and `foldLeft` not working 
    ```scala
    // this works
    def concatRight[T](xs: List[T], ys: List[T]): List[T] =
    (xs foldRight ys) (_ :: _)
    // type error
    def concatLeft[T](xs: List[T], ys: List[T]): List[T] =
    (xs foldLeft ys) (_ :: _)
    ```
## Induction methods
### Laws of `concat`
We would like to prove that concat:
* is associative
    * `(xs ++ ys) ++ zs = xs ++ (ys ++ zs)` 
* admits empty list as neutral element to the right
    * `xs ++ Nil = xs` 
* admits empty list as neutral element to the left
    * `Nil ++ xs = xs` 
### Structural induction
To prove a property for all lists `xs` 
* show that a property (`P`) `P(Nil)` holds -> **Base case**
* for a list `xs` and some element `x`, show the **induction step**:
    * if `P(xs)` holds then `P(x :: xs)` also holds
#### `concat` example
* definition
    ```scala
    def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
        case List() => ys
        case x :: xs1 => x :: concat(xs1, ys)
    }
    ```
    From this definition we have two `defining clauses` of ++: 
    ```scala
    // from `case List() => ys`
    Nil ++ ys = ys 
    // from `case x :: xs1 => x :: concat(xs1, ys)`
    (x :: xs1) ++ ys = x :: (xs1 ++ ys) 
    ```
* Structural induction 
    * Show `(xs ++ ys) ++ zs = xs ++ (ys ++ zs)`
    * **Base case**: `Nil`
        * LHS -> `(Nil ++ ys) ++ zs`
             * From 1st defining clause
             * `ys ++ zs`
        * RHS -> `Nil ++ (ys ++ zs)`
             * From 1st defining clause
             * `ys ++ zs`
        * LHS = RHS so the property holds for the **base case**
    * **Induction step**: `x :: xs`
        * LHS -> `((x :: xs) ++ ys) ++ zs`
             * From 2nd defining clause
             * `(x :: (xs ++ ys)) ++ zs`
             * `x :: ((xs ++ ys) ++ zs)`
             * Induction hypothesis assume hypothesis is already proven for `xs` ("if `P(xs)` holds then `P(x :: xs)` also holds")
             * `x :: (xs ++ (ys ++ zs))`
        * RHS -> `(x :: xs) ++ (ys ++ zs)`
             * From 2nd defining clause
             * `x :: (xs ++ (ys ++ zs))`
        * LHS = RHS so the property holds for the **induction step** => **Property proven**
    * Show `xs ++ Nil = xs`
    * **Base case**: `Nil`
        * LHS -> `Nil ++ Nil`
             * From 1st defining clause
             * `Nil`
        * RHS -> `Nil`
        * LHS = RHS so the property holds for the **base case**
    * **Induction step**: `x :: xs`
        * LHS -> `(x :: xs) ++ Nil`
             * From 2nd defining clause
             * `x :: (xs ++ Nil)`
             * Induction hypothesis assume hypothesis is already proven for `xs` ("if `P(xs)` holds then `P(x :: xs)` also holds")
             * `x :: xs`
        * RHS -> `x :: xs`
        * LHS = RHS so the property holds for the **induction step** => **Property proven**
# Week 6
## Other Collections
### Hierarchy
* Iterable
    * Seq
        * Vector
        * List
        * Array (Java)
        * String (Java)
    * Set
    * Map
### Vector
* More evenly access patterns than List
* Access patterns 
    * `x +: xs` or `xs :+ x`
        ```scala
        val nums = Vector(1, 2, 3, -88)
        def addElementFront(xs: Vector[Int], elem: Int): Vector[Int] =
        elem +: xs
        def addElementBack(xs: Vector[Int], elem: Int): Vector[Int] =
        xs :+ elem
        addElementFront(nums, 2)
        addElementBack(nums, 2)
        ```
    * If the pattern access used fit more `headElem :: tail` or `head :: tailElem` use `List` not `Vector`
### Arrays and Strings
* Arrays and Lists are also part of `Iterable`
    ```scala
    val xs = Array(1, 2, 3, 44)
    xs map (x => x * 2)
    val s = "Hello World"
    s filter (c => c.isUpper)
    ```
### Ranges
* `to`
    * inclusive
* `until`
    * exclusive
* `by`
    * Step value
```scala
val r: Range = 1 until 5 // 1,2,3,4
val t: Range = 1 to 5 // 1,2,3,4,5
1 to 10 by 3 // 1,4,7,10
6 to 1 by -2 // 6,4,2
```
### Seq operations
#### exists
* `xs exists p`
* true if there is an element x of xs such that p(x) holds,
false otherwise
* examples
    ```scala
    val s1 = "Hello World"
    s1 exists (c => c.isUpper)
    >>>
    Boolean = true
    ```
#### forall
* `xs forall p`
* true if p(x) holds for all elements x of xs, false otherwise.
* examples
    ```scala
    val s1 = "Hello World"
    s1 forall (c => c.isUpper)
    >>>
    Boolean = false
    ```    
#### zip
* `xs zip ys`
* A sequence of pairs drawn from corresponding elements
of sequences xs and ys.
* examples
    ```scala
    val s1 = "Hello World"
    val pairs = List(1,2,3) zip s1
    >>>
    List[(Int, Char)] = List((1,H), (2,e), (3,l))
    ```   
#### unzip
* `xs.unzip`
* Splits a sequence of pairs xs into two sequences consisting of the first, respectively second halves of all pairs.
* examples
    ```scala
    val s1 = "Hello World"
    val pairs = List(1,2,3) zip s1
    pairs.unzip
    >>>
    (List[Int], List[Char]) = (List(1, 2, 3),List(H, e, l))
    ```   
#### flatMap
* `xs.flatMap f`
* Applies collection-valued function f to all elements of xs and concatenates the results
* examples
    ```scala
    val s1 = "Hello World"
    s1 flatMap (c => List('.', c))
    >>>
    IndexedSeq[Char] = Vector(., H, ., e, ., l, ., l, ., o, .,  , ., W, ., o, ., r, ., l, ., d)
    ```  
#### sum
* `xs.sum`
* The sum of all elements of this numeric collection.
#### product
* `xs.product`
* The product of all elements of this numeric collection
#### max
* `xs.max`
* The maximum of all elements of this collection (an Ordering must exist)
#### min
* `xs.min`
* The minimum of all elements of this collection
