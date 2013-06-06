package bergradler.geo.imex.jdk

import bergradler.geo.imex.XmlFormatter
import java.io.File
import javax.xml.transform.stream.StreamSource
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.TransformerFactory
import java.io.FileReader
import javax.xml.transform.OutputKeys
import java.io.FileWriter

class JdkXmlFormatter extends XmlFormatter {

  def format(input: File) = {
    input.renameTo(new File(input.getAbsolutePath() + "/" + input.getName()+ ".raw"))
    input.delete()
    val outputFile = new File(input.getAbsolutePath())
    
    val xmlInput = new StreamSource(new FileReader(input));
    val fileWriter = new FileWriter(outputFile);
    val xmlOutput = new StreamResult(fileWriter);
    val transformerFactory = TransformerFactory.newInstance();
    transformerFactory.setAttribute("indent-number", indent);

    val transformer = transformerFactory.newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.transform(xmlInput, xmlOutput);
    return xmlOutput.getWriter().w
  }

}