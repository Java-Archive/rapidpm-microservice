package junit.org.rapidpm.microservice.optionals.cli.exeption;

/**
 * Created by b.bosch on 19.11.2015.
 */
public class AttemptToExitException extends RuntimeException {
    private int status;

    public int getStatus() {
        return status;
    }

    public AttemptToExitException(int status) {
        this.status = status;

    }
}
