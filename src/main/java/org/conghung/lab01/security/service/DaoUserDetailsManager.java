package org.conghung.lab01.security.service;

/*
import lombok.RequiredArgsConstructor;
import org.conghung.lab01.security.dao.UserDAO;
import org.conghung.lab01.security.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DaoUserDetailsManager implements UserDetailsService {

    private final UserDAO userDAO;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findById(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String password = user.getPassword();
        String[] roles = user.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getId().substring(5))
                .toArray(String[]::new);
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(password)
                .roles(roles)
                .build();
    }

}
*/
