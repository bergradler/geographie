package bergradler.geo.model.primitives

import scala.collection.mutable.ListBuffer

class Relation(members: List[Primitive]) extends Primitive {

  def members(): List[Primitive] = {
    members
  }

  def membersWithRoles(): List[Primitive] = {
    members
  }

}

object Relation {
  implicit def relation2Nodes(relation: Relation): List[Node] = {
    val ways = relation.members.collect { case way: Way if way.isInstanceOf[Way] => way.nodes }
    ways.flatten
  }
}