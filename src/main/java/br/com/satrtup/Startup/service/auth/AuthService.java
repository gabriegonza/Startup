package br.com.satrtup.Startup.service.auth;

import br.com.satrtup.Startup.domain.vo.security.AccountCredentialsVO;
import br.com.satrtup.Startup.domain.vo.security.TokenVO;
import br.com.satrtup.Startup.repository.user.UserRepository;
import br.com.satrtup.Startup.securiry.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity signin(AccountCredentialsVO vo) {
        if (checkIfParamsIsNotNullAccess(vo)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }

        try {
            var username = vo.getUsername();
            var password = vo.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);
            var tokenResponse = new TokenVO();

            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username: " + username + " not found!");
            }

            return ResponseEntity.ok(tokenResponse);

        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password!");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity refreshToken(String userName, String refreshToken) {

        if (checkIfParamsIsNotNullRefresh(userName, refreshToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }

        var user = repository.findByUsername(userName);

        var tokenResponse = new TokenVO();
        if (user != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new UsernameNotFoundException("Username " + userName + " not found!");
        }
        return ResponseEntity.ok(tokenResponse);
    }

    private boolean checkIfParamsIsNotNullAccess(AccountCredentialsVO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }

    private boolean checkIfParamsIsNotNullRefresh(String userName, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank() ||
                userName == null || userName.isBlank();
    }
}
