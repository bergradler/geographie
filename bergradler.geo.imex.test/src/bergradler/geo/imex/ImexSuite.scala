package bergradler.geo.imex

import java.io.File
import org.junit.Before
import org.junit.Test
import bergradler.geo.imex.jdk.JdkStaxWriter
import bergradler.geo.model.primitives.Model
import bergradler.geo.model.primitives.Node
import bergradler.geo.model.primitives.Relation
import bergradler.geo.model.primitives.Way
import bergradler.geo.imex.jdk.JdkStaxParser
import org.junit.Assert
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(classOf[Suite])
@SuiteClasses(Array(classOf[ImportTest],classOf[ExportTest]))
class ImexSuite {

}