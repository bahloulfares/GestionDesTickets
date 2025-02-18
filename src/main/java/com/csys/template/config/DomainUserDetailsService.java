package com.csys.template.config;

import com.csys.template.domain.User;
import com.csys.template.repository.UserRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
//        final User.UserBuilder userBuilder = User.builder().passwordEncoder(encoder::encode);
        log.error("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = userRepository
                .findOneByUsername(lowercaseLogin);

        return userFromDatabase.map(user -> {
            String mdpe = new BCryptPasswordEncoder().encode(user.getPassword().toLowerCase(Locale.ENGLISH));
            List<GrantedAuthority> grantedAuthorities = java.util.Arrays.asList(new SimpleGrantedAuthority("template-core"));
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,"{bcrypt}"+mdpe,
                    grantedAuthorities);
        }).orElseThrow(
                () -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " + "database"));
    }
}
