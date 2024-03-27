package br.com.satrtup.Startup.exception;

import br.com.satrtup.Startup.exception.auth.InvalidAuthenticationException;
import br.com.satrtup.Startup.exception.books.BooksExceptionNotFound;
import br.com.satrtup.Startup.exception.person.PersonExceptionNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandlerResponse extends ResponseEntityExceptionHandler{

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionsResponse> handleAllExceptions(
            Exception ex, WebRequest request) {

        ExceptionsResponse exceptionResponse = new ExceptionsResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PersonExceptionNotFound.class)
    public final ResponseEntity<ExceptionsResponse> PersonExceptionNotFound(
            Exception ex, WebRequest request) {

        ExceptionsResponse exceptionResponse = new ExceptionsResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BooksExceptionNotFound.class)
    public final ResponseEntity<ExceptionsResponse> BooksExceptionNotFound(
            Exception ex, WebRequest request) {

        ExceptionsResponse exceptionResponse = new ExceptionsResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RequiredObjectIsNullException.class)
    public final ResponseEntity<ExceptionsResponse> handleBadRequestExceptions(
            Exception ex, WebRequest request) {

        ExceptionsResponse exceptionResponse = new ExceptionsResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAuthenticationException.class)
    public final ResponseEntity<ExceptionsResponse> InvalidAuthenticationException(
            Exception ex, WebRequest request) {

        ExceptionsResponse exceptionResponse = new ExceptionsResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

}