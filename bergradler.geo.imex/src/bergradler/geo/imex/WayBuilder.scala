package bergradler.geo.imex

import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Primitive
import bergradler.geo.model.primitives.Way
import bergradler.geo.model.primitives.Node
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import bergradler.geo.model.primitives.Relation
import bergradler.geo.model.primitives.TaggedElement

class WayBuilder(model: Model) extends PrimitiveBuilder with NodeParser {

  val nodes: ListBuffer[List[Node]] = ListBuffer()

  var currentList: ListBuffer[Node] = null

  var currentTags: Map[String, String] = Map()

  def handle(element: GpxElement): PrimitiveBuilder = {
    element match {
      case Skip =>
        this
      case name @ element if element.name == "trk" && element.isStart =>
        this
      case name @ element if element.name == "trk" && !element.isStart =>
        val primitive: Option[Primitive] = nodes.size match {
          case 0 => None
          case 1 =>
            Option.apply(new Way(nodes.apply(0)))
          case _ =>
            val members: List[Way] = nodes.toList.map(l => new Way(l))
            Option.apply(new Relation(members))
        }
        tag(primitive)
        new NoneBuilder(model)
      case name @ element if element.name == "trkseg" && element.isStart =>
        currentList = ListBuffer()
        this
      case name @ element if element.name == "trkseg" && !element.isStart =>
        nodes += currentList.toList
        this
      case name @ element if element.name == "trkpt" && element.isStart =>
        val trkpt = parseWpt(element)
        currentList += trkpt
        currentTags = Map()
        this
      case name @ element if element.name == "trkpt" && !element.isStart =>
        tag(Option.apply(currentList.last))
        this
      case _ =>
        if (element.isStart ) {
         val text = element.text
          if (text != null) {
            currentTags.put(element.name, text)
          }
        }
        this
    }
  }
  
  private def tag(primitive: Option[bergradler.geo.model.primitives.Primitive]): Any = {
    if (primitive.isDefined) {
      currentTags.foreach(t => primitive.get.tags.put(t._1, t._2))
      model.add(primitive.get)
    }
    currentTags.clear
  }
}