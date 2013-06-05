package bergradler.geo.model.primitives

class Way(val nodes: List[Node]) extends Primitive {

  def nodeCount(): Integer = {
    nodes.size
  }
  
  def walk(v:Node=>Unit)={
    nodes.foreach(v)
  }
  
}