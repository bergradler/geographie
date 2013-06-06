package bergradler.geo.model.primitives

trait Primitive extends TaggedElement {

  def role(): Option[String] = {
    None
  }

}

object Primitive {

}