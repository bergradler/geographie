package bergradler.geo.model.primitives

class Way(nodes: List[Node]) extends Primitive {

  def nodeCount(): Integer = {
    nodes.size
  }

}