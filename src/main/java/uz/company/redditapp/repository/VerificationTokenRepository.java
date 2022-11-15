package uz.company.redditapp.repository;

import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import uz.company.redditapp.domain.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository extends BaseRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByTokenAndDeletedFalse(String token);
}
