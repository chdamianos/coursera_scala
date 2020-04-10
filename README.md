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