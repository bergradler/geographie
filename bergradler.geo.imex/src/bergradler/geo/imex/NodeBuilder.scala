package bergradler.geo.imex

import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Node

import scala.collection.mutable.Map

class NodeBuilder(model: Model) extends PrimitiveBuilder with NodeParser {

  var lat: Double = 0.

  var lon: Double = 0.

  var ele: Option[Double] = None

  var tags: Map[String, String] = Map()

  def handle(element: GpxElement): PrimitiveBuilder = {
    element match {
      case Skip => this
      case name @ element if element.name == "wpt" && element.isStart =>
        lat = element.attribute("lat").get.toDouble
        lon = element.attribute("lon").get.toDouble
        this
      case name @ element if element.name == "wpt" && !element.isStart =>
        val node = new Node(lat, lon, ele)
        tag(node, tags.toMap)
        model.add(node)
        new NoneBuilder(model)
      case name @ element if element.name == "ele" && element.isStart =>
        val text = element.text
        if (text != null) {
          ele = Option.apply(text.toDouble)
        }
        this
      case name @ element if element.isStart && isKnownChild(element.name) =>
        tags.put(element.name, element.text)
        this
      case _ => this
    }
  }

}