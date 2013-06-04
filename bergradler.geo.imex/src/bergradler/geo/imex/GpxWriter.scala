/**
 *
 */
package bergradler.geo.imex

trait GpxWriter {

  val newlineChar = "\n"

  val tabChar = "\t"

  def prepare

  def startDocument

  def endDocument

  def startElement(name: String)

  def endElement(name: String)
  
  def attribute(name:String, value:String)

  def characters(content: String)

  def dtd(ctrl: String)

  def newline={
    dtd(newlineChar)
  }
  
  def tab={
    dtd(tabChar)
  }
  
  def element(name:String,content:String)={
    startElement(name)
    characters(content)
    endElement(name)
    newline
  }
  
  def close

}