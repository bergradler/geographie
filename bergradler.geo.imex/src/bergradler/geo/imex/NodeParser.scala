package bergradler.geo.imex

import bergradler.geo.model.primitives.mutable.Node

trait NodeParser {

  def parseWpt(event: GpxElement): Node = {
    val lat = event.attribute("lat").get.toDouble
    val lon = event.attribute("lon").get.toDouble
    val ele = event.attribute("ele").flatMap(e => Some(e.toDouble))

    new Node(lat, lon, ele)
  }

}