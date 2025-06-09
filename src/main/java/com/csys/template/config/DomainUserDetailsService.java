package com.csys.template.config;

import com.csys.template.domain.User;
import com.csys.template.domain.enums.Role;
import com.csys.template.service.UserService;
import java.util.ArrayList;
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

    private final UserService userService;

    public DomainUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.error("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = userService.findUserByUsername(lowercaseLogin);
    
        return userFromDatabase.map(user -> {
            String password = user.getPassword();
            // Vérifier si le mot de passe est déjà crypté
            if (!password.startsWith("{bcrypt}")) {
                password = "{bcrypt}" + new BCryptPasswordEncoder().encode(password.toLowerCase(Locale.ENGLISH));
            }
          
            // Utiliser le rôle de l'utilisateur
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            if (user.getRole() != null) {
                grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name()));
                log.debug("Rôle de l'utilisateur : {}", user.getRole().name());
            } else {
                // Rôle par défaut si aucun rôle n'est défini
                grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_AUTRE.name()));
            }
            
            return new org.springframework.security.core.userdetails.User(lowercaseLogin, password, grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }
}
