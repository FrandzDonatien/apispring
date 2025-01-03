package frandz.api_test.service.impl;

import frandz.api_test.model.User;
import frandz.api_test.repository.UserRepository;
import frandz.api_test.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws BadCredentialsException {
        Optional<User> user = this.userRepository.findByEmail(username);
        if(user.isEmpty()){
            throw new BadCredentialsException("email/password incorrect");
        }else{
            return user.get();
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

}
