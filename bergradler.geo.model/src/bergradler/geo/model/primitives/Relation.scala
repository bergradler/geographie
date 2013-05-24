package bergradler.geo.model.primitives

class Relation(members: List[PrimitiveWithRole]) extends Primitive {

  def members(): List[Primitive] = {
    members.map(primitiveWithRole => primitiveWithRole.primitive)
  }

  def membersWithRoles(): List[PrimitiveWithRole] = {
    members
  }

}