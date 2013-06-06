package bergradler.geo
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import bergradler.geo.imex.ExportTest
import bergradler.geo.imex.ImportTest
import org.junit.runners.Suite
import bergradler.geo.model.primitives.PrimitiveTests

@RunWith(classOf[Suite])
@SuiteClasses(Array(classOf[ImportTest], classOf[ExportTest], classOf[PrimitiveTests]))
class ImexSuite {

}