package bergradler.geo.imex

import java.io.File
import org.junit.Before
import org.junit.Test
import bergradler.geo.imex.jdk.JdkStaxWriter
import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Node
import bergradler.geo.model.primitives.Relation
import bergradler.geo.model.primitives.Way
import bergradler.geo.imex.jdk.JdkStaxParser
import org.junit.Assert
import bergradler.geo.model.primitives.Primitive

class ExportTest {

  var model: Model = null

  @Before
  def setup: Unit = {
    model = new Model()
    val n1 = new Node(48.661667, 9.2125, Option.apply(711.0))
    n1.tags.put("name", "n1")
    val n2 = new Node(48.22, 9.1125)
    n2.tags.put("name", "n2")
    val n3 = new Node(47.661667, 9.05)

    val trkSeg1 = new Way(List(n1, n2))
    val trkSeg2 = new Way(List(n2, n3))

    val trkWith1Seg = new Relation(List(trkSeg1))
    trkWith1Seg.tags put ("name", "track1")
    val trkWith2Segs = new Relation(List(trkSeg1, trkSeg2))
    trkWith2Segs.tags put ("name", "multiSeg")
    model.addAll(List(n1, n2, n3, trkWith1Seg, trkWith2Segs))
  }

  @Test
  def exportsNodes(): Unit = {
    val writer = new JdkStaxWriter()
    val exporter = new GpxExporter(writer, new File("test/out.gpx"))

    exporter.run(model)

  }

  @Test
  def roundtrip(): Unit = {
    val importedModel = importModel("test/test.gpx")

    val writer = new JdkStaxWriter()
    val exporter = new GpxExporter(writer, new File("test/out.gpx"))

    exporter.run(importedModel)

    val reimportedModel = importModel("test/out.gpx")

    Assert.assertEquals("Model size different.", importedModel.elements.size, reimportedModel.elements.size)

    importedModel.elements.zip(reimportedModel.elements).foreach(p =>
      compare(p._1, p._2))
  }

  @Test
  def formatExport() = {
    val importedModel = importModel("test/test.gpx")

    val writer = new JdkStaxWriter()
    val exporter = new GpxExporter(writer, new File("test/out.gpx"))

    exporter.run(importedModel)

  }

  private def compare(alpha: Primitive, beta: Primitive) = {
    alpha match {
      case alphaNode: Node =>
        Assert.assertTrue(beta.isInstanceOf[Node])
        val betaNode = beta.asInstanceOf[Node]
        assertNodesEqual(alphaNode, betaNode)
      case alphaWay: Way =>
        Assert.assertTrue(beta.isInstanceOf[Way])
        assertWaysEqual(alphaWay, beta.asInstanceOf[Way])
      case alphaRelation: Relation =>
        Assert.assertTrue(beta.isInstanceOf[Relation])
        assertRelationsEqual(alphaRelation, beta.asInstanceOf[Relation])
      case _ => Assert.fail("What is this?")
    }
  }

  private def importModel(filename: String): Model = {
    val parser = new JdkStaxParser()
    val importer = new GpxImporter(parser, new File(filename))
    importer.run
  }

  private def assertNodesEqual(alphaNode: bergradler.geo.model.primitives.Node, betaNode: bergradler.geo.model.primitives.Node): Unit = {
    Assert.assertEquals(alphaNode.lat, betaNode.lat, 0.)
    Assert.assertEquals(alphaNode.lon, betaNode.lon, 0.)
    Assert.assertEquals(alphaNode.ele, betaNode.ele)
    Assert.assertEquals(alphaNode.tags, betaNode.tags)
  }

  private def assertWaysEqual(alpha: Way, beta: Way): Unit = {
    Assert.assertEquals(alpha.nodes.size, beta.nodes.size)
    alpha.nodes.zip(beta.nodes).foreach(p => assertNodesEqual(p._1, p._2))
  }

  private def assertRelationsEqual(alpha: Relation, beta: Relation): Unit = {
    Assert.assertEquals(alpha.members.size, beta.members.size)
    alpha.members.zip(beta.members).foreach(p => assertPrimitvesEqual(p._1, p._2))
  }

  private def assertPrimitvesEqual(alpha: Primitive, beta: Primitive): Unit = {
    Assert.assertEquals(alpha.getClass(), beta.getClass())

    alpha match {
      case alphaNode: Node => assertNodesEqual(alphaNode, beta.asInstanceOf[Node])
      case alphaWay: Way => assertWaysEqual(alphaWay, beta.asInstanceOf[Way])
      case alphaRelation: Relation => assertRelationsEqual(alphaRelation, beta.asInstanceOf[Relation])
    }
  }

}