package api.exceptions;

public class LoginFailedException extends RuntimeException {

    public enum ErrorType {

        USERNAME("El usuario ingresado no existe."),
        PASSWORD("La contraseña ingresada no es válida."),
        TOKEN("No se pudo validar el token."),
        EMPTY("Debes ingresar tus datos para iniciar sesión."),
        NO_ACTIVE("El usuario ingresado con se encuentra activo."),
        REMEMBER("No fue posible hacer uso de la funcionalidad \"Recuérdame\"."),
        OTHER("No fue posible iniciar sesión.");

        private final String message;

        ErrorType(String message) {
            this.message = message;
        }

    }

    private final ErrorType errorType;

    public LoginFailedException(ErrorType errorType) {
        super(errorType.message);
        this.errorType = errorType;
    }

    public LoginFailedException(ErrorType errorType, Throwable throwable) {
        super(errorType.message, throwable);
        this.errorType = errorType;
    }

    public boolean isUsernameError() {
        return errorType.equals(ErrorType.USERNAME);
    }

    public boolean isPasswordError() {
        return errorType.equals(ErrorType.PASSWORD);
    }

}
