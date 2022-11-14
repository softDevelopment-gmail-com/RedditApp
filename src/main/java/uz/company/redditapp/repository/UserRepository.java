package uz.company.redditapp.repository;

import uz.company.redditapp.domain.User;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsernameAndDeletedFalse(String username);

    boolean existsByEmailAndDeletedFalse(String email);

    Optional<User> findOneWithAuthoritiesByUsernameAndDeletedFalse(String username);
}
