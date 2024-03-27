package br.com.satrtup.Startup.exception.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidAuthenticationException extends AuthenticationException{

    private static final long serialVersionUID = 1L;

    public InvalidAuthenticationException(String ex) {
        super(ex);
    }
}