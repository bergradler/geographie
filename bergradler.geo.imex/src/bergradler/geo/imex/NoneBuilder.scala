package bergradler.geo.imex

import bergradler.geo.model.primitives.Model

class NoneBuilder(model: Model) extends PrimitiveBuilder {

  def handle(element: GpxElement): PrimitiveBuilder = {
    if (element.isStart) {
      val name = element.name
      name match {
        case "wpt" => new NodeBuilder(model).handle(element)
        case "trk" => new WayBuilder(model).handle(element)
        case _ => this
      }
    } else {
      this
    }
  }

}