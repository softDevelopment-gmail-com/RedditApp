package uz.company.redditapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.company.redditapp.constants.ApiConstants;
import uz.company.redditapp.dto.SubredditDTO;
import uz.company.redditapp.dto.SubredditDetailDTO;
import uz.company.redditapp.service.SubRedditService;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.subredditRootApi)
@AllArgsConstructor
public class SubRedditController {
    private final SubRedditService subRedditService;

    @PostMapping()
    public ResponseEntity<SubredditDTO> create(@RequestBody SubredditDTO subredditDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subRedditService.save(subredditDTO));
    }

    @GetMapping(ApiConstants.all)
    public ResponseEntity<List<SubredditDetailDTO>> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getAll());
    }

    @GetMapping(ApiConstants.id)
    public ResponseEntity<SubredditDetailDTO> getById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subRedditService.get(id));
    }
}
