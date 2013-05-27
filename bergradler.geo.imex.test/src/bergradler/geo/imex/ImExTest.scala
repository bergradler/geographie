package bergradler.geo.imex

import org.junit.Test
import org.junit.Assert
import bergradler.geo.imex.jdk.JdkStaxParser
import java.io.File
import bergradler.geo.model.primitives.Relation

class ImExTest {

  @Test
  def parseTestFile(): Unit = {
    //2 wpt, 3 trk
    val parser = new JdkStaxParser
    val input = new File("test/test.gpx")
    Assert.assertTrue(input.exists())

    parser.setInput(input)
    val importer = new GpxImporter(parser)

    val model = importer.run
    Assert.assertEquals(5, model.elements.size)

    val last = model.elements.last
    Assert.assertTrue(last.isInstanceOf[Relation])

    model.elements.filter(e => e.isInstanceOf[Relation]).foldLeft("")((last, current) =>
      assertNotSameAndReturn(last, current.tags.get("name").get))

    val relation = last.asInstanceOf[Relation]
    val relationName = relation.tags.get("name").get
    
    Assert.assertEquals("Urach_64_kehren on GPSies.com", relationName)
    Assert.assertEquals(2, relation.members.size)

  }

  def assertNotSameAndReturn[T](expectedNotBe: T, candidate: T): T = {
    Assert.assertFalse("expected to be not equal: "+expectedNotBe+"!="+candidate, expectedNotBe.equals(candidate))
    candidate
  }

}