package tp1.exceptions;

public class CommandException extends Exception {

    // 1. Constructor sin argumentos
    public CommandException() { 
        super(); 
    }

    // 2. Constructor con mensaje
    public CommandException(String message) {
        super(message);
    }

    // 3. Constructor con causa (para envolver excepciones sin añadir mensaje extra)
    public CommandException(Throwable cause) {
        super(cause);
    }

    // 4. Constructor con mensaje y causa (EL MÁS IMPORTANTE PARA ESTA PRÁCTICA)
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    // 5. Constructor completo (con supresión y traza de pila)
    public CommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}