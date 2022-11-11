package uz.company.redditapp.repository;

import uz.company.redditapp.domain.User;

public interface UserRepository extends BaseRepository<User, Long> {
    boolean existsByUsernameAndDeletedFalse(String username);

    boolean existsByEmailAndDeletedFalse(String email);
}
