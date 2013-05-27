package bergradler.geo.model.primitives

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ListBuffer

class Model {
  
  val elements:ListBuffer[Primitive] = ListBuffer()
  
  def add(element:Primitive*)={
    elements++=element
  }
  
}