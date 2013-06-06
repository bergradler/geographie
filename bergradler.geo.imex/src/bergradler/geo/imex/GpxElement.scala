package bergradler.geo.imex

trait GpxElement {

  def qualifiedName: String

  def name: String

  def namespacePrefix: String

  def attribute(name: String): Option[String]

  def text: String

  def isStart: Boolean
}

object Skip extends GpxElement {
  def qualifiedName: String = {
    null
  }

  def name: String = {
    ""
  }

  def namespacePrefix: String = {
    null
  }

  def attribute(name: String): Option[String] = {
    null
  }

  def text: String = {
    null
  }

  def isStart = {
    false
  }
}

