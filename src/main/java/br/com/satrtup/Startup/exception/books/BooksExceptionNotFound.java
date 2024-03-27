package br.com.satrtup.Startup.exception.books;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BooksExceptionNotFound extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BooksExceptionNotFound() {
        super("Book not found!");
    }

    public BooksExceptionNotFound(String ex) {
        super(ex);
    }

}