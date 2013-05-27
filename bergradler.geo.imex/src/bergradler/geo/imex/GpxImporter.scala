package bergradler.geo.imex

import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Primitive
import bergradler.geo.model.primitives.Node
import scala.Double
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ListBuffer
import bergradler.geo.model.primitives.Way
import bergradler.geo.model.primitives.Relation

class GpxImporter(parser: GpxParser) {

  var currentTrackSegment: ListBuffer[Node] = ListBuffer()

  var trackSegments: ListBuffer[Way] = ListBuffer()

  var currentName: String = null

  def run: Model = {
    val model = new Model

    while (parser.hasNext) {
      val event = parser.next
      event.name match {
        case "wpt" =>
          model.add(parseWpt(event))
        case "trk" =>
          processCollectedTrkInfo(model)
          trackSegments.clear
          currentTrackSegment.clear
        case "trkseg" =>
          if (!currentTrackSegment.isEmpty) {
            trackSegments += new Way(currentTrackSegment.toList)
          }
          currentTrackSegment.clear
        case "trkpt" =>
          currentTrackSegment += parseWpt(event)
        case "name" =>
          currentName = event.text
        case _ =>
      }
    }
    processCollectedTrkInfo(model)
    trackSegments.clear
    currentTrackSegment.clear
    model
  }

  private def parseWpt(event: GpxElement): Node = {
    val lat = event.attribute("lat").get.toDouble
    val lon = event.attribute("lon").get.toDouble
    val ele = event.attribute("ele").flatMap(e => Some(e.toDouble))
    new Node(lat, lon, ele)
  }

  private def processCollectedTrkInfo(model: Model) = {
    if (!currentTrackSegment.isEmpty) {
      val way = new Way(currentTrackSegment.toList)
      trackSegments += way
      val relation = new Relation(trackSegments.toList)
      relation.tags.put("name", currentName)
      model.add(relation)
    }
  }

}