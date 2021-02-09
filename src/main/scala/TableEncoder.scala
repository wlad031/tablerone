package dev.vgerasimov.stf

import shapeless.labelled.FieldType
import shapeless.{ ::, HList, HNil, LabelledGeneric, Witness }

import scala.collection.immutable

trait TableEncoder[-A] {
  def encode(a: A): Table
}

object TableEncoder {

  def instance[A](table: Table): TableEncoder[A] = (_: A) => table
  def instance[A](f: A => Table): TableEncoder[A] = (a: A) => f(a)

  implicit val intEncoder: TableEncoder[Int] = instance((a: Int) => Table.oneCell(Nil, a.toString))
  implicit val stringEncoder: TableEncoder[String] = instance((a: String) => Table.oneCell(Nil, a))

  implicit val hnilEncoder: TableEncoder[HNil] = instance(Table.empty)

  implicit def hlistEncoder[K <: Symbol, H, T <: HList](
    implicit
    witness: Witness.Aux[K],
    hEncoder: TableEncoder[H],
    tEncoder: TableEncoder[T]
  ): TableEncoder[FieldType[K, H] :: T] = TableEncoder.instance { (a: FieldType[K, H] :: T) =>
    {
      val name = witness.value.name
      val h = hEncoder.encode(a.head)
      val t = tEncoder.encode(a.tail)
      val headers = t.headers.::(name)
      val data = List(h.data.head ++ t.data.headOption.getOrElse(Nil))
      Table(headers, data)
    }
  }

  implicit def genericEncoder[A, Repr](
    implicit
    gen: LabelledGeneric.Aux[A, Repr],
    reprEncoder: TableEncoder[Repr]
  ): TableEncoder[A] = TableEncoder.instance { (a: A) =>
    {
      reprEncoder.encode(gen.to(a))
    }
  }

  implicit def seqEncoder[A](implicit aEncoder: TableEncoder[A]): TableEncoder[Seq[A]] = TableEncoder.instance {
    (a: Seq[A]) =>
      {
        a match {
          case immutable.::(head, next) =>
            val h = aEncoder.encode(head)
            val data = next.map(x => aEncoder.encode(x)).map(x => x.data).foldLeft(h.data)((a, b) => a ++ b)
            Table(h.headers, data)
          case Nil => Table.empty
        }
      }
  }
}
