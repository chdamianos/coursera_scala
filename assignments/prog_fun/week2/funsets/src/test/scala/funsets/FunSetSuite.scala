package funsets

import org.junit._

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite {

  import FunSets._


  @Test def `contains is implemented`(): Unit = {
    assert(contains(_ => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   * val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1: _root_.funsets.FunSets.FunSet = singletonSet(1)
    val s2: _root_.funsets.FunSets.FunSet = singletonSet(2)
    val s3: _root_.funsets.FunSets.FunSet = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remvoe the
   *
   **/
  //  @Ignore("not ready yet")
  @Test def `singleton set one contains one`(): Unit = {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  @Test def `union contains all elements of each set`(): Unit = {
    new TestSets {
      val s: _root_.funsets.FunSets.FunSet = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  @Test def `intersect contains common elements between sets`(): Unit = {
    new TestSets {
      val s12: _root_.funsets.FunSets.FunSet = union(s1, s2)
      val s23: _root_.funsets.FunSets.FunSet = union(s2, s3)
      val s123: _root_.funsets.FunSets.FunSet = intersect(s12, s23)
      assert(!contains(s123, 1), "Intersect 1")
      assert(contains(s123, 2), "Intersect 2")
      assert(!contains(s123, 3), "Intersect 3")
    }
  }

  @Test def `diff contains different elements between sets`(): Unit = {
    new TestSets {
      val s12: _root_.funsets.FunSets.FunSet = union(s1, s2)
      val s23: _root_.funsets.FunSets.FunSet = union(s2, s3)
      val s123: _root_.funsets.FunSets.FunSet = diff(s12, s23)
      assert(contains(s123, 1), "Diff 1")
      assert(!contains(s123, 2), "Diff 2")
      assert(contains(s123, 3), "Diff 3")
    }
  }

  @Test def `filter keeps elements satisfying condition`(): Unit = {
    new TestSets {
      def p(elem: Int): Boolean = elem!=2
      val s12: _root_.funsets.FunSets.FunSet = union(s1, s2)
      val s123: _root_.funsets.FunSets.FunSet = union(s12, s3)
      val s123_filt: _root_.funsets.FunSets.FunSet = filter(s123, p)
      assert(contains(s123_filt, 1), "Filter 1")
      assert(!contains(s123_filt, 2), "Filter 2")
      assert(contains(s123_filt, 3), "Filter 3")
    }
  }

  @Test def `forall tests if all elements satisfy condition`(): Unit = {
    new TestSets {
      assert(!forall(Set(1, 2, 3),{elem: Int => elem!=2}), "forall 1")
      assert(forall(Set(1, 2, 3),{elem: Int => elem>0}), "forall 2")
      assert(!forall(Set(-1, 2, 3),{elem: Int => elem>0}), "forall 3")
    }
  }

  @Test def `exists tests if at least one element satisfies condition`(): Unit = {
    new TestSets {
      assert(exists(Set(1, 2, 3),{elem: Int => elem!=2}), "exists 1")
      assert(!exists(Set(-1, -2, -3),{elem: Int => elem>0}), "exists 2")
      assert(exists(Set(-1, -2, 3),{elem: Int => elem>0}), "exists 3")
    }
  }

  @Test def `map s to another set by applying f`(): Unit = {
    new TestSets {
      val testSet = Set(1,2,3,4)
      def mapFunc: Int => Int = { x:Int => x*x}
      assert(contains(map(testSet, mapFunc),1), "map 1")
      assert(contains(map(testSet, mapFunc),4), "map 2")
      assert(contains(map(testSet, mapFunc),9), "map 3")
      assert(contains(map(testSet, mapFunc),16), "map 4")
      assert(!contains(map(testSet, mapFunc),25), "map 5")
    }
  }
  @Rule def individualTestTimeout = new org.junit.rules.Timeout(10 * 1000)
}
