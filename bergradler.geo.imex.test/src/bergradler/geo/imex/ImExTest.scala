package bergradler.geo.imex

import org.junit.Test
import org.junit.Assert
import bergradler.geo.imex.jdk.JdkStaxParser
import java.io.File
import bergradler.geo.model.primitives.Relation

class ImExTest {

   @Test
  def parseTestFile() = {
    //2 wpt, 3 trk
     val parser = new JdkStaxParser
     val input = new File("test/test.gpx")
     Assert.assertTrue(input.exists())
     
     parser.setInput(input)
     val importer = new GpxImporter(parser)
     
     val model = importer.run
     Assert.assertEquals(5, model.elements.size)
     
     val last = model.elements.last
     Assert.assertTrue(last.isInstanceOf[Relation] )
     
     val relation = last.asInstanceOf[Relation]
     Assert.assertEquals(2, relation.members.size)
     
  }
  
}