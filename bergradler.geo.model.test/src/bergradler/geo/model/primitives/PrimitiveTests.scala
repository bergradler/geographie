package bergradler.geo.model.primitives

import org.junit.Test
import org.junit.Assert

class PrimitiveTests {

  @Test
  def nodeWithTags() = {
    val node = new Node(48.661667, 9.2125)
    node.tags put ("mtb:scale", "m2")
    Assert.assertTrue(node.tags contains "mtb:scale")
  }

  @Test
  def wayWith3Nodes() = {
    val n1 = new Node(48.661667, 9.2125)
    val n2 = new Node(48.22, 9.1125)
    val n3 = new Node(47.661667, 9.05)

    val way = new Way(List(n1, n2, n3))

    Assert.assertTrue(way.nodeCount == 3)
  }

  @Test
  def relationWith2Ways() = {
    val n1 = new Node(48.661667, 9.2125)
    val n2 = new Node(48.22, 9.1125)
    val n3 = new Node(47.661667, 9.05)

    val way = new Way(List(n1, n2, n3))

    val relation = new Relation(List(way, n2))
    Assert.assertEquals(2, relation.members.size)
    Assert.assertEquals(2, relation.membersWithRoles.size)

    Assert.assertNotSame(relation.members, relation.membersWithRoles)
  }

}