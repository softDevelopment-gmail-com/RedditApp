package uz.company.redditapp.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.redditapp.constants.ApiConstants;
import uz.company.redditapp.dto.PostDTO;
import uz.company.redditapp.dto.PostDetailDTO;
import uz.company.redditapp.service.PostService;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.posts)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PostController {

    PostService postService;

    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO postDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.create(postDTO));
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<PostDetailDTO> get(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.get(id));
    }

    @GetMapping(ApiConstants.all)
    public ResponseEntity<List<PostDetailDTO>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getAll());
    }

    @GetMapping(ApiConstants.byUser)
    public ResponseEntity<List<PostDetailDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getByUser(userId));
    }

    @GetMapping(ApiConstants.bySubreddit)
    public ResponseEntity<List<PostDetailDTO>> getBySubreddit(@PathVariable Long subredditId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getBySubreddit(subredditId));
    }
}
