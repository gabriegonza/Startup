package br.com.satrtup.Startup.exception.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileExceptionNotFound extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FileExceptionNotFound(String ex) {
        super(ex);
    }

    public FileExceptionNotFound(String ex, Throwable cause) {
        super(ex, cause);
    }
}