package api.exceptions;

import net.sf.oval.ConstraintViolation;

import java.util.ArrayList;
import java.util.List;

public class InvalidDataException extends RuntimeException {

    private final List<String> erros = new ArrayList<>();

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(List<ConstraintViolation> violations) {
        violations.forEach(violation -> erros.add(violation.getMessage()));
    }

    public List<String> getErros() {
        return erros;
    }

}
