package masha.courses.security;

import masha.courses.services.UsersDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

public class AuthProviderImpl implements AuthenticationProvider {
    private final UsersDetailsService usersDetailsService;

    public AuthProviderImpl(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        UserDetails userDetails= usersDetailsService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        if(!encoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, username, userDetails.getAuthorities());    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
