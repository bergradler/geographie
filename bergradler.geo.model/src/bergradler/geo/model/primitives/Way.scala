package bergradler.geo.model.primitives

class Way(val nodes: List[Node]) extends Primitive with Iterable[Node] {

  def nodeCount(): Integer = {
    nodes.size
  }
  
  def walk(v:Node=>Unit)={
    foreach(v)
  }
  
  def iterator={
    nodes.iterator
  }
}