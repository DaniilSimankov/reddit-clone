package ru.simankovd.springredditclone.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.simankovd.springredditclone.dto.SubredditDto;
import ru.simankovd.springredditclone.service.SubredditService;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubRedditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddits(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubreddit(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(subredditService.getSubreddit(id));
    }

}
