package api.exceptions;

public class NoDevicePairedException extends RuntimeException {

    public NoDevicePairedException() {
        super("No se encontró ningún dispositivo vinculado.");
    }

    public NoDevicePairedException(String message) {
        super(message);
    }

    public NoDevicePairedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDevicePairedException(Throwable cause) {
        super(cause);
    }

    protected NoDevicePairedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
