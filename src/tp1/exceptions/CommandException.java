package tp1.exceptions;

public class CommandException extends Exception {

    // Constructor sin argumentos
    public CommandException() { 
        super(); 
    }

    // Constructor con mensaje
    public CommandException(String message) {
        super(message);
    }

    // Constructor con causa (para envolver excepciones sin añadir mensaje extra)
    public CommandException(Throwable cause) {
        super(cause);
    }

    // Constructor con mensaje y causa (EL MÁS IMPORTANTE PARA ESTA PRÁCTICA)
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor completo (con supresión y traza de pila)
    public CommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}