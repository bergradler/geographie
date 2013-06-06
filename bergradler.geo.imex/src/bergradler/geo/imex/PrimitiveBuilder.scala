package bergradler.geo.imex

import bergradler.geo.model.primitives.Primitive

trait PrimitiveBuilder {

  val knownPrimitiveChildren = Set("time", "magvar", "geoidheight", "name", "cmt", "desc", "src", "link", "sym", "type", "fix", "sat", "hdop", "vdop", "pdop", "ageofdgpsdata", "dgpsid")

  def isKnownChild(name: String): Boolean = {
    knownPrimitiveChildren.contains(name)
  }

  def handle(element: GpxElement): PrimitiveBuilder

  def tag(p: Primitive, tags: Map[String, String]) = {
    tags.foreach(t => p.tags.put(t._1, t._2))
  }

}