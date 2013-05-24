package bergradler.geo.model.primitives

import scala.collection.mutable.Map

class Node(lat: Double, lon: Double, _ele: Option[Double] = None) extends Primitive {

  val ele = _ele

}