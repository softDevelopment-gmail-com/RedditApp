package uz.company.redditapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.company.redditapp.errors.NotFoundException;
import uz.company.redditapp.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component("DomainUserDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        String login = username.toLowerCase();
        return userRepository.findOneWithAuthoritiesByUsernameAndDeletedFalse(login)
                .map(this::createSpringFrameworkUser).orElseThrow(() -> new NotFoundException("User with this username: " + username + " not found", "username not found"));
    }

    private User createSpringFrameworkUser(uz.company.redditapp.domain.User user) {
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new UserAuth(
                user.getUsername(),
                user.getPassword(),
                authorities,
                user.getId()
        );
    }
}
