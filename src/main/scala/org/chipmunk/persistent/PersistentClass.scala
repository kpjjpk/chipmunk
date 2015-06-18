package org.chipmunk.persistent

import org.squeryl.customtypes.StringField

//TODO: couldn't this be type safe?
class PersistentClass(val className: String) extends StringField(className) {
  def this(clazz: Class[_]) = this(clazz.getName)

  def asClass: Class[_] = Class.forName(className)
}