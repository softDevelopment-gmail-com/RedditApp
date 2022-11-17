package uz.company.redditapp.repository;

import uz.company.redditapp.domain.Post;

import java.util.Optional;

public interface PostRepository extends BaseRepository<Post, Long> {

    Optional<Post> findByIdAndDeletedFalse(Long id);

    Optional<Post> findAllByUserIdAndDeletedFalse(Long userId);

}
