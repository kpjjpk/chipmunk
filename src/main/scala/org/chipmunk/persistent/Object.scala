package org.chipmunk.persistent

import org.squeryl.annotations.Transient
import scala.reflect.ClassTag
import scala.reflect.runtime.currentMirror
import scala.reflect.runtime.universe.TypeTag
import org.squeryl.Table
import org.chipmunk.persistent.Entity

abstract class Object[T <: Object[T, O], O](
  table: Table[T],
  val typ: Type)
    extends Entity[T](table) {
  self: T =>

  @Transient
  private[this] var instance: O = _

  def this(table: Table[T], objClass: Class[_ <: O]) =
    this(table, new Type(objClass))

  def this(table: Table[T], obj: O) =
    this(table, obj.getClass)

  protected def getValue()(
    implicit classTag: ClassTag[O],
    typeTag: TypeTag[O]): O = {
    if (instance == null) { instance = resolveObj() }
    instance
  }

  private[this] def resolveObj()(
    implicit classTag: ClassTag[O],
    typeTag: TypeTag[O]): O = {
    val classNameProper = typ.classNameProper
    val moduleSymbol = currentMirror.staticModule(classNameProper)
    val moduleMirror = currentMirror.reflectModule(moduleSymbol)
    val obj = moduleMirror.instance

    if (classTag.runtimeClass.isInstance(obj)) {
      obj.asInstanceOf[O]
    } else {
      val expectedType = typeTag.tpe
      throw new IllegalStateException(
        s"$classNameProper is not an instance of expected type: $expectedType")
    }
  }

  protected def keys: Product = Tuple1(typ.classNameProper)
}
