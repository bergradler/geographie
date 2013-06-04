package bergradler.geo.imex

import java.io.File

import org.junit.Before
import org.junit.Test

import bergradler.geo.imex.jdk.JdkStaxWriter
import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Node
import bergradler.geo.model.primitives.Relation
import bergradler.geo.model.primitives.Way

class ExportTest {

  var model: Model = null

  @Before
  def setup:Unit = {
    model = new Model()
    val n1 = new Node(48.661667, 9.2125,Option.apply(711.0))
    n1.tags.put("name", "n1")
    val n2 = new Node(48.22, 9.1125)
    n2.tags.put("name", "n2")
    val n3 = new Node(47.661667, 9.05)
    
    val trkSeg1 = new Way(List(n1,n2))
    val trkSeg2 = new Way(List(n2,n3))
    
    val trkWith1Seg = new Relation(List(trkSeg1))
    trkWith1Seg.tags put ("name","track1")
    val trkWith2Segs = new Relation(List(trkSeg1, trkSeg2))
    trkWith2Segs.tags put ("name", "multiSeg")
    model.add(n1,n2,n3, trkWith1Seg, trkWith2Segs)
  }

  @Test
  def exportsNodes(): Unit = {
    val writer = new JdkStaxWriter(new File("test/out.gpx"))
    val exporter = new GpxExporter(writer)
    
    exporter.run(model)
    
  }
 
}