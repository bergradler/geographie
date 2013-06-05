package bergradler.geo.model.primitives.mutable

import bergradler.geo.model.primitives.Primitive

class Node(var lat: Double, var lon: Double, var ele: Option[Double] = None) extends Primitive {

  def toNode: bergradler.geo.model.primitives.Node = {
    new bergradler.geo.model.primitives.Node(lat, lon, ele)
  }

}