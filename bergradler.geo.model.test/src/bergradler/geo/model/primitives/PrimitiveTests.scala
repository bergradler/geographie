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

}