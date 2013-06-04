package bergradler.geo.model.primitives

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ListBuffer
import scala.collection.Iterable

class Model {

  val elements: ListBuffer[Primitive] = ListBuffer()

  def add(element: Primitive*) = {
    elements ++= element
  }

  
  def nodes: Iterable[Node] = {
    elements.toIterable.filter(p => p.isInstanceOf[Node]).map(p => p.asInstanceOf[Node])
  }

  def ways:Iterable[Way] = {
    elements.toIterable.filter(p => p.isInstanceOf[Way]).map(w => w.asInstanceOf[Way])
  }
  
  def relations:Iterable[Relation]={
    elements.toIterable.filter(p => p.isInstanceOf[Relation]).map(r => r.asInstanceOf[Relation])
  }
  
}