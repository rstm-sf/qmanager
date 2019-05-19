package kmqc.qsimulator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class QsimulatorTest extends TestCase {

  public QsimulatorTest(String testName) {
    super(testName);
  }

  public static Test suite() {
    return new TestSuite(QsimulatorTest.class);
  }

  public void testQsimulator() {
    QsimulatorLib.main(null);
    assertTrue(true);
  }

}
