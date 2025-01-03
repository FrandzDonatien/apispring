package frandz.api_test.controller.userController;


import frandz.api_test.model.User;
import frandz.api_test.responses.AuthenticationResponse;
import frandz.api_test.responses.HttpResponse;
import frandz.api_test.responses.UserResponse;
import frandz.api_test.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/avis-user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HomeController {
    private final AuthService authService;

    @PostMapping("/")
    public String home(){
        return "hello world";
    }

    @PostMapping("/me")
    public ResponseEntity<HttpResponse<User>> me(){
        return ResponseEntity.ok(this.authService.me(SecurityContextHolder.getContext().getAuthentication()));
    }

}
