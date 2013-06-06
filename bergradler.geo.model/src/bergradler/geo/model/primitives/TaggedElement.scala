package bergradler.geo.model.primitives

import scala.collection.mutable.Map

trait TaggedElement {

  private val _tags: Map[String, String] = Map()

  def tags(): Map[String, String] = {
    _tags
  }

}