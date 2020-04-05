import week3.Rational

val a = new Rational(1,2)

def error(msg: String) = throw new Error(msg)

//error("test")

val x = null
val y :String = x

//val z: Int = null -> error type mismatch

if (true) 1 else false