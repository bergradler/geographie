package bergradler.geo.imex

import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Node

class NodeBuilder(model: Model) extends PrimitiveBuilder with NodeParser{

  var lat: Double = 0.

  var lon: Double = 0.

  var ele: Option[Double] = None

  val tags: Map[String, String] = Map()

  def handle(element: GpxElement): PrimitiveBuilder = {
    element match {
      case Skip => this
      case name @ element if element.name == "wpt" && element.isStart =>
        lat = element.attribute("lat").get.toDouble
        lon = element.attribute("lon").get.toDouble
        this
      case name @ element if element.name == "wpt" && !element.isStart =>
        val node = new Node(lat, lon, ele)
        tags.foreach(t => node.tags.put(t._1, t._2))
        model.add(node)
        new NoneBuilder(model)
      case name @ element if element.name == "ele" && element.isStart =>
        if(element.text != null){}
        ele = Option.apply(element.text.toDouble)
        
        this
      case _ => this
    }
  }

  

}