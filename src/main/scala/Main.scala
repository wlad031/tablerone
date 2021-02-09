package dev.vgerasimov.stf

object Main {
  case class A(i: Int, s: String)
  case class B(a: A, ii: Int)

  def main(args: Array[String]): Unit = {
    val e = implicitly[TableEncoder[List[B]]]
    val table = e.encode(List(B(A(1, "1s"), 10), B(A(2, "2s"), 11), B(A(3, "3s"), 12)))
    println(table.asString)
  }
}
