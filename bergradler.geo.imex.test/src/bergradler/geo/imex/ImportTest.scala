package bergradler.geo.imex

import org.junit.Test
import org.junit.Assert
import bergradler.geo.imex.jdk.JdkStaxParser
import java.io.File
import bergradler.geo.model.primitives.Relation
import bergradler.geo.model.primitives.Model
import org.junit.Before
import bergradler.geo.model.primitives.Node
import bergradler.geo.model.primitives.Way

class ImportTest {

  var model: Model = null

  @Before
  def setup = {
    model = parseModel
  }

  @Test
  def envOk(): Unit = {
    //2 wpt, 3 trk
    val parser = new JdkStaxParser
    val input = new File("test/test.gpx")
    Assert.assertTrue(input.exists())
  }
  @Test
  def correctNumberOfElements(): Unit = {
    Assert.assertEquals(5, model.elements.size)
  }
  
  @Test
  def wptName():Unit={
    val huette = model.elements.apply(1)
    Assert.assertEquals("Westgipfelhütte", huette.tags.get("name").get)
  }
  
  @Test
  def eleInTrkPt():Unit={
    val relation = model.elements.last.asInstanceOf[Relation]
    val way = relation.members.last.asInstanceOf[Way]
    val trkPtWithEle = way.nodes.last
    Assert.assertTrue(trkPtWithEle.ele.isDefined)
  }

  @Test
  def distinctRelationNames(): Unit = {
    val last = model.elements.last
    Assert.assertTrue(last.isInstanceOf[Relation])

    model.elements.filter(e => e.isInstanceOf[Relation]).foldLeft("")((last, current) =>
      assertNotSameAndReturn(last, current.tags.get("name").get))
  }
  
  @Test
  def trkSegs(): Unit = {
    val last = model.elements.last
    val relation = last.asInstanceOf[Relation]
    val relationName = relation.tags.get("name").getOrElse("")

    Assert.assertEquals("Urach_64_kehren on GPSies.com", relationName)
    Assert.assertEquals(2, relation.members.size)

  }

  @Test
  def ele: Unit = {
    val wptWithEle = model.elements.apply(0).asInstanceOf[Node]
    Assert.assertEquals(438, wptWithEle.ele.get, 0.0)
  }

  def assertNotSameAndReturn[T](expectedNotBe: T, candidate: T): T = {
    Assert.assertFalse("expected to be not equal: " + expectedNotBe + "!=" + candidate, expectedNotBe.equals(candidate))
    candidate
  }

  private def parseModel: bergradler.geo.model.primitives.Model = {
    //2 wpt, 3 trk
    val parser = new JdkStaxParser
    val input = new File("test/test.gpx")
    val importer = new GpxImporter(parser, input)

    val model = importer.run
    model
  }

}