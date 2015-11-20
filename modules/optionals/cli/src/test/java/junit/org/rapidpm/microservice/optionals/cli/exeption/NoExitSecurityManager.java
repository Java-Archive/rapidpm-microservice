package junit.org.rapidpm.microservice.optionals.cli.exeption;

import java.security.Permission;

/**
 * Created by b.bosch on 19.11.2015.
 */
public class NoExitSecurityManager
        extends java.lang.SecurityManager {
    private final SecurityManager parent;

    public NoExitSecurityManager(final SecurityManager manager) {
        parent = manager;
    }

    public void checkExit(int status) {
        throw new AttemptToExitException(status);
    }

    public void checkPermission(Permission perm) {
    }
}
