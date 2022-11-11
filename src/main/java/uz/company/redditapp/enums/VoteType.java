package uz.company.redditapp.enums;

import lombok.Getter;

@Getter
public enum VoteType {
    UPVOTE(1),DOWNVOTE(-1);

    VoteType(int direction) {
    }
}
