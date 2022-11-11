package uz.company.redditapp.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.company.redditapp.enums.VoteType;

import javax.persistence.*;

@Entity
@Table(name = "Vote")
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Vote extends AbstractAuditingEntity {

    @Column(name = "vote_type")
    VoteType voteType;

    @Column(name = "post_id")
    Long postId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",insertable = false,updatable = false)
    Post post;


    @Column(name = "user_id")
    Long userId;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    User user;

}
