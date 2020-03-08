package recfun

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(s"${pascal(col, row)} ")
      println()
    }

  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int =

    if (c == 0) {
      1
    } else if (r == 0) {
      0
    } else {
      pascal(c, r - 1) + pascal(c - 1, r - 1)
    }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    @scala.annotation.tailrec
    def inner_func(chars: List[Char], numOpens: Int, numParentheses: Int): Boolean = {
      if (chars.nonEmpty) {
        val firstChar = chars.head
        var n: Int = numOpens
        var nPare: Int = numParentheses
        if (firstChar == '(') {
          n = n + 1
          nPare = nPare + 1
        } else if (firstChar == ')') {
          n = n - 1
          nPare = nPare + 1
        }
        if (n < 0) {
          false
        } else {
          inner_func(chars.tail, n, nPare)
        }
      } else {
        if (numOpens == 0 && chars.nonEmpty && numParentheses > 0) {
          true
        } else {
          false
        }
      }
    }

    inner_func(chars, 0, 0)
  }


  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
