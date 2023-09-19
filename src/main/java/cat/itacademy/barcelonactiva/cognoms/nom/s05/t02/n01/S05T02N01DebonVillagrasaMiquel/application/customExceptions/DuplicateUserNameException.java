package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions;

public class DuplicateUserNameException extends RuntimeException{
    public DuplicateUserNameException() {
        super("Duplicate user name");
    }

    public DuplicateUserNameException(String message) {
        super(message);
    }
}
