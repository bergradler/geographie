package bergradler.geo.imex

import java.io.File

trait GpxParser {

  def setInput(input:File)
  
  def next:GpxElement
  
  def hasNext:Boolean
  
  def close
  
}