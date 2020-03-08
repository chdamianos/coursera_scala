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
    def inner_func(chars_inner: List[Char], numOpens: Int, numParentheses: Int): Boolean = {
      if (chars_inner.nonEmpty) {
        val firstChar = chars_inner.head
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
          inner_func(chars_inner.tail, n, nPare)
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
   * The idea here is to use up our coins and subtract it from the current amount of money.
   * Eventually, the amount of money will be either 0, some negative number (meaning this combination of coins failed),
   * or some positive number (meaning that we can still subtract more with the coins we currently have).
   * countChange(money - coins.head, coins) will exhaust all combinations subtracting the first coin from the money,
   * while countChange(money, coins.tail) exhausts all combinations using all other coins only.
   * They are added together, since + is synonymous with the logical OR operator.
   * See page 51 of Structure and interpretation of computer programs, (second edition)
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    val coin_bigger_than_money: Boolean = money < 0
    val all_coins_used: Boolean = coins.isEmpty
    val change_made: Boolean = money == 0
    if (coin_bigger_than_money || all_coins_used)
      0
    else if (change_made)
      1
    else
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }
}
