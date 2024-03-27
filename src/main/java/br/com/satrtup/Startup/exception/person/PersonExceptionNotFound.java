package br.com.satrtup.Startup.exception.person;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonExceptionNotFound extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public PersonExceptionNotFound() {
        super("Person not found!");
    }

    public PersonExceptionNotFound(String ex) {
        super(ex);
    }
}