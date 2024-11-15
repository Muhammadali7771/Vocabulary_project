package org.example.config;

import org.example.authuser.AuthUser;
import org.example.authuser.AuthUserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record CustomUserDetailsService(AuthUserDao authUserDao) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username '%s'".formatted(username)));
      //  authorities.add(new SimpleGrantedAuthority("ROLE_" + authUser.getRole()));
        return new CustomUserDetails(authUser);
    }
}
