package io.github.wlad031.stf

/** Structure containing 2D data with headers.
  *
  * {{{
  *   ID         FIRSTNAME        LASTNAME
  *   1          Kris             Best
  *   2          John             Smith
  * }}}
  */
final case class Table(headers: List[String], data: List[List[String]]) {
  def asString: String = {
    headers.reduce((a, b) => s"$a | $b") + "\n" +
    data.map(row => row.reduce((a, b) => s"$a | $b")).reduce((a, b) => s"$a\n$b")
  }
}

object Table {
  def empty: Table = Table(Nil, Nil)
  def oneRow(row: List[String]): Table = Table(Nil, List(row))
  def oneRow(headers: List[String], row: List[String]): Table = Table(headers, List(row))
  def oneCell(cell: String): Table = Table(Nil, List(List(cell)))
  def oneCell(headers: List[String], cell: String): Table = Table(headers, List(List(cell)))
}
