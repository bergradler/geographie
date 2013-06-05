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
import scala.collection.immutable.Stack

class WayBuilder(model: Model) extends PrimitiveBuilder with NodeParser {

  val nodes: ListBuffer[List[Node]] = ListBuffer()

  var currentList: ListBuffer[bergradler.geo.model.primitives.mutable.Node] = null

  var currentTags: Stack[Map[String, String]] = Stack()

  def handle(element: GpxElement): PrimitiveBuilder = {
    element match {
      case Skip =>
        this
      case name @ element if element.name == "trk" && element.isStart =>
        currentTags = currentTags.push(Map())
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
        model.add(primitive.get)
        new NoneBuilder(model)
      case name @ element if element.name == "trkseg" && element.isStart =>
        currentList = ListBuffer()
        this
      case name @ element if element.name == "trkseg" && !element.isStart =>
        nodes += currentList.map(n => n.toNode).toList
        this
      case name @ element if element.name == "trkpt" && element.isStart =>
        val trkpt = parseWpt(element)
        currentList += trkpt
        currentTags = currentTags.push(Map())
        this
      case name @ element if element.name == "trkpt" && !element.isStart =>
        tag(Option.apply(currentList.last))
        this
      case name @ element if element.name == "ele" && element.isStart =>
        val ele = element.text.toDouble
        currentList.last.ele = Option.apply(ele)
        this

      case name @ element if element.isStart && isKnownChild(element.name) =>
        val text = element.text
        if (text != null) {
          currentTags.head.put(element.name, text)
        }
        this
      case _ => this
    }
  }

  private def tag(primitive: Option[bergradler.geo.model.primitives.Primitive]): Any = {
    if (primitive.isDefined) {
      tag(primitive.get, currentTags.head.toMap)
    }
    currentTags = currentTags.pop
  }
}