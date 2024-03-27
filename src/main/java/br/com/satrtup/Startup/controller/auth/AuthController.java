package br.com.satrtup.Startup.controller.auth;

import br.com.satrtup.Startup.domain.vo.security.AccountCredentialsVO;
import br.com.satrtup.Startup.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService authServices;
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Authenticates a user and returns a token")
	@PostMapping(value = "/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO vo) {
		return authServices.signin(vo);
	}
	
	@SuppressWarnings("rawtypes")
	@Operation(summary = "Refresh token for authenticated user and returns a token")
	@PutMapping(value = "/refresh/{userName}")
	public ResponseEntity refreshToken(@PathVariable("userName") String userName,
			@RequestHeader("Authorization") String refreshToken) {
		return authServices.refreshToken(userName,refreshToken);
	}
}
