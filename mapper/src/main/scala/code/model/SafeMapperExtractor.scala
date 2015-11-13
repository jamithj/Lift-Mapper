package code
package model

import net.liftweb.mapper._
import net.liftweb.common._
import java.util.{ Calendar => JavaCalendar }
import java.util.Date

trait SafeMapperExtractor {
  protected def safeString[T <: Mapper[T]](t: MappedText[T]): Box[String] = t.get match {
    case s: String => Full(s)
    case _ => Empty
  }
  protected def safeString[T <: Mapper[T]](t: MappedString[T]): Box[String] = t.get match {
    case s: String => Full(s)
    case _ => Empty
  }
  protected def safeLong[T <: Mapper[T]](t: MappedLong[T]): Box[Long] = Full(t.get)
  protected def safeInt[T <: Mapper[T]](t: MappedInt[T]): Box[Int] = Full(t.get)
  protected def safeBoolean[T <: Mapper[T]](t: MappedBoolean[T]): Box[Boolean] = Full(t.get)
  protected def safeDate[T <: Mapper[T]](t: MappedDate[T]): Box[Date] = t.get match {
    case d: Date => Full(d)
    case _ => Empty
  }
  protected def safeDateTime[T <: Mapper[T]](t: MappedDateTime[T]): Box[Date] = t.get match {
    case d: Date => Full(d)
    case _ => Empty
  }
  protected def safeDouble[T <: Mapper[T]](t: MappedDouble[T]): Box[Double] = Full(t.get)

}

class MappedStringForeignKey[Owner <: Mapper[Owner], Other <: StringKeyedMapper[Other]](incomingOwner: Owner, incomingOther: Other, incomingLen: Int) extends MappedString[Owner](incomingOwner, incomingLen) with MappedForeignKey[String, Owner, Other] {
    override def dbKeyToTable = incomingOther.getSingleton
    def defined_? : Boolean = true
    override def foreignMeta: KeyedMetaMapper[String, Other] = { incomingOther.getSingleton }
  }

  trait BaseStringKeyedMapper extends BaseKeyedMapper {
    override type TheKeyType = String
  }
  trait StringKeyedMapper[OwnerType <: StringKeyedMapper[OwnerType]] extends KeyedMapper[String, OwnerType] with BaseStringKeyedMapper {
    self: OwnerType =>
  }

  trait StringKeyedMetaMapper[A <: StringKeyedMapper[A]] extends KeyedMetaMapper[String, A] { self: A => }

  class MappedStringForeignIndexKey[Owner <: Mapper[Owner], Other <: StringKeyedMapper[Other]](owner: Owner, foreign: Other, length: Int)
    extends MappedStringForeignKey[Owner, Other](owner, foreign, length) with IndexedField[String] {
    override def convertKey(in: AnyRef): Box[String] = Full(in.toString)
    override def convertKey(in: Long): Box[String] = Full(in.toString)
    override def convertKey(in: Int): Box[String] = Full(in.toString)
    override def convertKey(in: String): Box[String] = Full(in)
    override def makeKeyJDBCFriendly(in: String): AnyRef = in
  }
