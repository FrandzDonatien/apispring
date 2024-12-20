package frandz.api_test.service;

import frandz.api_test.config.JwtService;
import frandz.api_test.exception.EmailExistException;
import frandz.api_test.exception.ExceptionHandling;
import frandz.api_test.exception.ExpiredTokenException;
import frandz.api_test.exception.InvalidTokenException;
import frandz.api_test.model.User;
import frandz.api_test.model.VerificationToken;
import frandz.api_test.repository.UserRepository;
import frandz.api_test.repository.VerificationTokenRepository;
import frandz.api_test.requests.RegisterRequest;
import frandz.api_test.responses.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class AuthService extends ExceptionHandling {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtService jwtService;

    //register
    public AuthenticationResponse register(RegisterRequest request) throws EmailExistException {
        var user = this.userRepository.findByEmail(request.getEmail());
        if(user.isPresent())
            throw new EmailExistException("email is already exist");
        var newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .accountNonExpired(false)
                .enable(false)
                .accountNonLocked(false)
                .credentialsNonExpired(false)
                .build();
        this.userRepository.save(newUser);

        //generation de token pour la connexion
        var jwtToken = this.jwtService.generateToken(newUser);
        //generation random code
        String code = this.generateCode();
        //verification token
        VerificationToken verificationToken = new VerificationToken(code,newUser);
        this.verificationTokenRepository.save(verificationToken);

        //after save, send code to user

        return AuthenticationResponse.builder()
                .data(newUser)
                .token(jwtToken)
                .message("email send")
                .build();
    }

    //validation token
    public AuthenticationResponse validationToken(String code) throws InvalidTokenException, ExpiredTokenException {
        //get token
        VerificationToken token = this.verificationTokenRepository.findByToken(code);
        if(token ==null)
            throw new InvalidTokenException("invalid token");
        //get user via token
        User user = token.getUser();
        //verification de la validation du token
        Calendar calendar = Calendar.getInstance();
        if( (token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0 ){
            this.verificationTokenRepository.delete(token);
            throw new ExpiredTokenException("token expired");
        }
        //set user
        user.setEnable(true);
        this.userRepository.save(user);


        return AuthenticationResponse.builder()
                .message("email verifier")
                .data(user)
                .build();
    }

    public String generateCode() {
        Random random = new Random();
        Number code = 100000 + random.nextInt(900000);
        return code.toString();
    }

}
