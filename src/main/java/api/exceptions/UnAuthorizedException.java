package api.exceptions;

public class UnAuthorizedException extends ApiException {
    public UnAuthorizedException() {
        super("No autorizado");
    }
}
