package bergradler.geo.imex

import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Node
import bergradler.geo.model.primitives.Way
import bergradler.geo.model.primitives.Way
import bergradler.geo.model.primitives.Relation
import bergradler.geo.model.primitives.TaggedElement
import java.io.File

class GpxExporter(writer: GpxWriter, output:File) {

  def run(model: Model) = {

    writer.prepare(output)

    exportModel(model)

    writer.close

  }

  private def exportModel(model: Model) = {

    writeProlog

    exportNodes(model)

    exportWays(model)

    writeEpilog

  }

  private def writeProlog = {
    writer.startDocument
    writer.newline
    writer.startElement("gpx")
    writer.attribute("creator", getClass().getName())
    writer.attribute("version", "1.1")
    writer.attribute("xsi:schemaLocation", "http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd")
    writer.attribute("xmlns","http://www.topografix.com/GPX/1/1" )
    writer.attribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance")
    writer.newline
  }

  private def writeEpilog = {
    writer.endElement("gpx")
    writer.endDocument
  }

  private def exportNodes(model: Model) = {
    model.nodes.foreach(n => exportNode(n))
  }

  private def exportWays(model: Model) = {
    model.ways.foreach(w => exportWay(w, List(w.nodes)))
    model.relations.foreach(r => exportWayGroup(r))
  }

  private def exportNode(node: Node, name: String = "wpt") = {
    writer.startElement(name)
    writer.attribute("lat", node.lat.toString)
    writer.attribute("lon", node.lon.toString)
    writer.newline
    node.ele.foreach(ele => writer.element("ele", ele.toString))
    node.tags.foreach(e=>writer.element(e._1, e._2))
    writer.endElement(name)
    writer.newline
  }

  private def exportWay(taggedElement:TaggedElement, nodes:List[List[Node]]) = {
    writer.startElement("trk")
    taggedElement.tags.foreach(e=>writer.element(e._1, e._2))
    nodes.foreach(wayNodes=>exportWayFragment(wayNodes))
    writer.endElement("trk")
  }
  
  private def exportWayFragment(nodes:List[Node]) = {
    writer.startElement("trkseg")
    nodes.foreach(wayNode=>exportNode(wayNode, "trkpt"))
    writer.endElement("trkseg")
  }

  private def exportWayGroup(relation: Relation) = {
	  exportWay(relation, Relation.relation2Nodes(relation))
  }

  private def exportTag(taggedElement: TaggedElement, name: String) = {
    taggedElement.tags.get(name).foreach(value => writer.element(name, value))
  }

}