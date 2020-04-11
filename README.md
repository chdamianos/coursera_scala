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
