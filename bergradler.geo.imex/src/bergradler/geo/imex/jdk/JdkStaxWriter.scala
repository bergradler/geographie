package bergradler.geo.imex.jdk

import bergradler.geo.imex.GpxWriter
import java.io.File
import java.io.FileOutputStream
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLEventWriter
import javax.xml.stream.XMLEventFactory

class JdkStaxWriter extends GpxWriter {

  var outStream: FileOutputStream = null

  var eventWriter: XMLEventWriter = null

  var eventFactory: XMLEventFactory = null

  def prepare(output: File) = {
    outStream = new FileOutputStream(output)
    eventWriter = XMLOutputFactory.newInstance().createXMLEventWriter(outStream, "utf-8")
    eventFactory = XMLEventFactory.newInstance()
  }

  def startDocument = {
    eventWriter.add(eventFactory.createStartDocument)
  }

  def endDocument = {
    eventWriter.add(eventFactory.createEndDocument)
  }

  def startElement(name: String) = {
    eventWriter.add(eventFactory.createStartElement("", "", name))
  }

  def endElement(name: String) = {
    eventWriter.add(eventFactory.createEndElement("", "", name))
  }

  def characters(content: String) = {
    eventWriter.add(eventFactory.createCharacters(content))
  }

  def dtd(ctrl: String) = {
    eventWriter.add(eventFactory.createDTD(ctrl))
  }

  def attribute(name: String, value: String) = {
    eventWriter.add(eventFactory.createAttribute(name, value))
  }

  def close = {
    eventWriter.close
    outStream.close
  }

}