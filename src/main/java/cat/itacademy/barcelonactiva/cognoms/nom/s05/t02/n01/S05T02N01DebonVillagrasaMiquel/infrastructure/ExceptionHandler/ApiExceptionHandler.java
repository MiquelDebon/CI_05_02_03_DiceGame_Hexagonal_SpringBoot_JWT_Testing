package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.infrastructure.ExceptionHandler;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.DuplicateUserEmailException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.DuplicateUserNameException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.EmptyDataBaseException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.application.customExceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> invalidUser(UserNotFoundException ex, WebRequest request){
        ErrorObject message = new ErrorObject(
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DuplicateUserNameException.class)
    public ResponseEntity<?> duplicatedName(DuplicateUserNameException ex){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("ErrorMessage", ex.getMessage());
        return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> duplicatedName(AccessDeniedException ex){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("ErrorMessage", ex.getMessage());
        return new ResponseEntity<>(mapError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateUserEmailException.class)
    public ResponseEntity<?> duplicatedEmail(DuplicateUserEmailException ex){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("ErrorMessage", ex.getMessage());
        return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyDataBaseException.class)
    public ResponseEntity<?> emptyDatabase(EmptyDataBaseException ex){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("ErrorMessage", ex.getMessage());
        return new ResponseEntity<>(mapError, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> notValid(ConstraintViolationException ex){

        Map<String, String> mapError = new HashMap<>();
        mapError.put("ErrorMessage", ex.getMessage());

        return new ResponseEntity<>(mapError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));
        Map<String, List<String>> result = new HashMap<>();
        result.put("Register error messages: ", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalErroException(Exception ex){
        Map<String, String> mapError = new HashMap<>();
        mapError.put("ErrorMessage", ex.getMessage());
        return new ResponseEntity<>(mapError, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
