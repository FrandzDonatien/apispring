package frandz.api_test.service;

import frandz.api_test.model.User;
import frandz.api_test.repository.UserRepository;
import frandz.api_test.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {
    Optional<User> getByEmail(String email);
}
