package uz.company.redditapp.repository;

import uz.company.redditapp.domain.SubReddit;

import java.util.Optional;

public interface SubRedditRepository extends BaseRepository<SubReddit, Long> {

    Optional<SubReddit> findByIdAndDeletedFalse(Long id);

}
