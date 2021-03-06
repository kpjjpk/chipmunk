package org.chipmunk.value

import org.joda.time.{ Duration => JDuration }
import org.squeryl.customtypes.LongField
import org.joda.time.{Duration => JDuration}

class Duration(millis: Long) extends LongField(millis) {
  require(millis >= 0)

  // no-arg constructor
  def this() = this(0)

  def this(duration: JDuration) = this(duration.getMillis)

  def asJoda: JDuration = new JDuration(millis)
}
