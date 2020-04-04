abstract class Base {
  def foo = 1

  def bar: Int

}

class Sub extends Base {
  // cannot do: def foo =2, need to override
  override def foo: Int = 2

  def bar = 3

}