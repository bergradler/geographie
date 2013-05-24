package bergradler.geo.model.primitives

import scala.collection.mutable.Map

class PrimitiveWithRole(val primitive: Primitive, role: Option[String] = None) extends Primitive {

  override def tags: Map[String, String] = { primitive.tags() }
  
}

object PrimitiveWithRole{
  implicit def enhancePrimitiveWithRole(primitive:Primitive)={
    new PrimitiveWithRole(primitive)
  }
}