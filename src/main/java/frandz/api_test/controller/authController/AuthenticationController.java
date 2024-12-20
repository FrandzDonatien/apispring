package frandz.api_test.controller.authController;

import frandz.api_test.exception.EmailExistException;
import frandz.api_test.exception.ExpiredTokenException;
import frandz.api_test.exception.InvalidTokenException;
import frandz.api_test.requests.RegisterRequest;
import frandz.api_test.responses.AuthenticationResponse;
import frandz.api_test.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static frandz.api_test.constant.SecurityConstant.APP_BASE_URL;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value ="/api/v1/auth/")
public class AuthenticationController {

    private final AuthService authService;

    //register
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws EmailExistException {
        return ResponseEntity.ok(this.authService.register(request));
    }

    //verification email
    @PostMapping(value = "verification-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthenticationResponse> verificationToken(@RequestBody Map<String, String> payload) throws InvalidTokenException, ExpiredTokenException
    {
        String token = payload.get("token");
        System.out.println(token);
        return ResponseEntity.ok(this.authService.validationToken(token));
    }

    //login

}
