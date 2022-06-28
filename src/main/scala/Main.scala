package dev.vgerasimov.tablerone

import dev.vgerasimov.shapelse.empty.Empty
import dev.vgerasimov.shapelse.{CoproductSchema, ListSchema, OptionSchema, PrimitiveSchema, ProductSchema, ProductSchemaEncoder, SchemaEncoder, structureSchemaEncoder}
import dev.vgerasimov.shapelse.structure.implicits.all._
import dev.vgerasimov.shapelse.empty.implicits._

object Main {

  sealed trait Cell
  object Cell {
    final case class Single(data: String) extends Cell
    final case class Multi(cells: List[Cell]) extends Cell
  }

  final case class Row(cells: List[Cell])
  final case class Table(rows: List[Row])

  trait TableEncoder[A] {
    def encode(ls: List[A]): Table
  }

  implicit def genericTableEncoder[A](implicit schemaEncoder: ProductSchemaEncoder[Empty, A]): TableEncoder[A]= {
    val schema = schemaEncoder.encode
    def itemToRow(a: A): Row = {
      Row(schema.childs.map({
        case (sym, sch) => sch match {
          case schema: PrimitiveSchema[_] => Cell.Single()
          case OptionSchema(meta, schema) => ???
          case ListSchema(meta, schema) => ???
          case ProductSchema(meta, childs) => ???
          case CoproductSchema(meta, childs) => ???
        }
      }))
    }
  }

  case class Foo(a: Int, b: String)

  def main(args: Array[String]): Unit = {

    val s = structureSchemaEncoder[Foo].encode
    println(s)
  }
}
