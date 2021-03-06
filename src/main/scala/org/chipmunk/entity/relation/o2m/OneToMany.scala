package org.chipmunk.entity.relation.o2m

import org.chipmunk.entity.Entity
import org.squeryl.dsl.{ OneToMany => SO2M }
import scala.collection.generic.Growable
import scala.collection.generic.Shrinkable
import org.chipmunk.entity.relation.Relation
import org.squeryl.dsl.{OneToMany => SO2M}

object OneToMany {
  type SOneToMany[M] = SO2M[M]
}

trait OneToMany[M <: Entity[_]]
    extends Relation[M] with Growable[M] with Shrinkable[M] {
  final type SRel = OneToMany.SOneToMany[M]

  def +=(other: M): this.type = {
    toSqueryl.associate(other)
    this
  }

  def clear(): Unit = { toSqueryl.deleteAll }

  protected final def isOwningSide: Boolean = true
}
