package uz.company.redditapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    String postName;

    String subredditName;

    String url;

    String description;
}
