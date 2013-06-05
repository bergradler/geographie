package bergradler.geo.imex

import java.io.File

trait GpxParser {

  def prepare(input:File)
  
  def next:GpxElement
  
  def peek:Option[GpxElement]
  
  def hasNext:Boolean
  
  def close
  
}