package br.com.satrtup.Startup.exception.file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileExeption extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FileExeption(String ex) {
        super(ex);
    }

    public FileExeption(String ex, Throwable cause) {
        super(ex, cause);
    }
}