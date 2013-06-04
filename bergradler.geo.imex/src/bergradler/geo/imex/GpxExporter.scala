/**
 *
 */
package bergradler.geo.imex

import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Node
import bergradler.geo.model.primitives.Way
import bergradler.geo.model.primitives.Way
import bergradler.geo.model.primitives.Relation
import bergradler.geo.model.primitives.TaggedElement

class GpxExporter(writer: GpxWriter) {

  def run(model: Model) = {

    writer.prepare

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
    model.ways.foreach(w => exportWay(w, w.nodes))
    model.relations.foreach(r => exportWayGroup(r))
  }

  private def exportNode(node: Node, name: String = "wpt") = {
    writer.startElement(name)
    writer.attribute("lat", node.lat.toString)
    writer.attribute("lon", node.lon.toString)
    writer.newline
    node.ele.foreach(ele => writer.element("ele", ele.toString))
    exportTag(node, "name")
    exportTag(node, "type")
    exportTag(node, "time")
    writer.endElement(name)
    writer.newline
  }

  private def exportWay(taggedElement:TaggedElement, nodes:List[Node]) = {
    writer.startElement("trk")
    taggedElement.tags.get("name").foreach(writer.element("name", _))
    writer.startElement("trkseg")
    nodes.foreach(n => exportNode(n, "trkpt"))
    writer.endElement("trkseg")
    writer.endElement("trk")
  }

  private def exportWayGroup(relation: Relation) = {
	  exportWay(relation, relation)
  }

  private def exportTag(node: Node, name: String) = {
    node.tags.get(name).foreach(value => writer.element(name, value))
  }

}