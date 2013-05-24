package bergradler.geo.model.primitives

class Relation(members: List[PrimitiveWithRole]) extends Primitive {

  def members(): List[Primitive] = {
    members.map(primitveWithRole => primitveWithRole.primitive)
  }

  def membersWithRoles(): List[PrimitiveWithRole] = {
    members
  }

}