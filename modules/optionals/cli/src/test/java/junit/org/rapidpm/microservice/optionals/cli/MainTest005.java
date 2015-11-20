package junit.org.rapidpm.microservice.optionals.cli;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by b.bosch on 19.11.2015.
 */
public class MainTest005 extends BaseCmdlineTest {

    @Test
    public void test001() throws Throwable {
        final String[] args = {"-trololo", "-jolo"};
        final int expectedStatus = 1;
        final int status = startAndGetExit(args);
        Assert.assertEquals("System.exit must be called with the value of " + expectedStatus, expectedStatus, status);
    }


}
