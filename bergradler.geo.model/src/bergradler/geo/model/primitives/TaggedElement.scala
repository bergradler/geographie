package bergradler.geo.model.primitives

import scala.collection.mutable.Map

trait TaggedElement {

  val tags:Map[String,String] = Map()
  
}