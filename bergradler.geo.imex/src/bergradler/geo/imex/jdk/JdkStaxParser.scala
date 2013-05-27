package bergradler.geo.imex.jdk

import bergradler.geo.imex.GpxParser
import java.io.File
import bergradler.geo.imex.GpxElement
import javax.xml.stream.XMLInputFactory
import java.io.FileInputStream
import java.io.InputStream
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.XMLEvent
import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.EventFilter
import javax.xml.namespace.QName
import javax.xml.XMLConstants
import bergradler.geo.imex.Skip

class JdkStaxParser extends GpxParser {

  var inStream: InputStream = null

  var reader: XMLEventReader = null

  def setInput(input: File) = {
    if (inStream != null) {
      throw new RuntimeException("Parser cannot be reused.")
    }
    this.inStream = new FileInputStream(input)
    val factory = XMLInputFactory.newFactory()
    val baseReader = factory.createXMLEventReader(inStream)
    this.reader = factory.createFilteredReader(baseReader, new EventFilter() {
      def accept(event: XMLEvent): Boolean = {
        event.getEventType() == XMLStreamConstants.START_ELEMENT || event.getEventType() == XMLStreamConstants.CHARACTERS
      }
    })
  }

  def next: GpxElement = {
    val event = reader.nextEvent()
    event.getEventType() match {
      case XMLStreamConstants.START_ELEMENT => xmlEventToGpxElement(event)
      case _ => Skip
    }
  }

  def hasNext: Boolean = {
    reader.hasNext()
  }

  def close = {
    reader.close
    inStream.close
  }

  def xmlEventToGpxElement(event: XMLEvent): GpxElement = {
    if(!event.isStartElement()){
      return null
    }
    val src = event.asStartElement()
    new GpxElement() {

      def qualifiedName: String = {
        src.getName().getNamespaceURI()
      }

      def name: String = {
        src.getName().getLocalPart()
      }

      def namespacePrefix: String = {
        src.getName().getPrefix()
      }

      def attribute(name: String): Option[String] = {
        def qname = new QName(XMLConstants.NULL_NS_URI, name)
        def attribute = Option.apply(src.getAttributeByName(qname))
        attribute.flatMap(a => Some(a.getValue()))
      }
      

      def text: String = {
        reader.getElementText()
      }
    }
  }
  
  

}
