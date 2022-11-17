package uz.company.redditapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import uz.company.redditapp.domain.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsernameAndDeletedFalse(String username);

    boolean existsByEmailAndDeletedFalse(String email);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsernameAndDeletedFalse(String username);

    Optional<User> findByUsernameAndDeletedFalse(String username);
}
