package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.ExceptionHandler.customExceptions;

public class EmptyDataBaseException extends RuntimeException{
    public EmptyDataBaseException() {
        super("Empty database");
    }

    public EmptyDataBaseException(String message) {
        super(message);
    }
}
