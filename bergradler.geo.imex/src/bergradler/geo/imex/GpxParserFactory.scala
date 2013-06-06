package bergradler.geo.imex

import java.io.File

object GpxParserFactory {

  def createJdkImporter(input: File): GpxImporter = {
    val parserClass = Class.forName("bergradler.geo.imex.jdk.JdkStaxParser")
    val parser = parserClass.newInstance.asInstanceOf[GpxParser]
    new GpxImporter(parser, input)
  }

  def createJdkExporter(output: File): GpxExporter = {
    val writerClass = Class.forName("bergradler.geo.imex.jdk.JdkStaxWriter")
    val writer = writerClass.newInstance.asInstanceOf[GpxWriter]
    new GpxExporter(writer, output)
  }

}