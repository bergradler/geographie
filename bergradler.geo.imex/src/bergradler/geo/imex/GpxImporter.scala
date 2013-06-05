package bergradler.geo.imex

import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Primitive
import bergradler.geo.model.primitives.Node
import scala.Double
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ListBuffer
import bergradler.geo.model.primitives.Way
import bergradler.geo.model.primitives.Relation
import bergradler.geo.model.primitives.TaggedElement
import java.io.File

class GpxImporter(parser: GpxParser, output:File) {

  var builder: PrimitiveBuilder = null

  def run: Model = {
    parser.prepare(output)
    val model = new Model
    builder = new NoneBuilder(model)
    while (parser.hasNext && builder != null) {
      val event = parser.next
      builder = builder.handle(event)
    }
    model
  }

  
}