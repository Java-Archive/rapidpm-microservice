package org.rapidpm.microservice.optionals.cli.helper;

/**
 * Created by b.bosch on 19.11.2015.
 */
public class SystemExitHelper implements ExitHelper {
    @Override
    public void exit(int exitcode) {
        System.exit(exitcode);
    }
}
