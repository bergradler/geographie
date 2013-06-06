/**
 *
 */
package bergradler.geo.imex

import java.io.File

trait XmlFormatter {
  
  val indent = 2

  def format(input: File)

}