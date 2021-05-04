package api.storage.exceptions;

import net.sf.oval.ConstraintViolation;

import java.util.List;

public class DaoException extends Exception {

    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DaoException(List<ConstraintViolation> violations) {
        // TODO: 28/3/2021 serializar errores
    }

}
