package ru.simankovd.springredditclone.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.simankovd.springredditclone.dto.VoteDto;
import ru.simankovd.springredditclone.exception.PostNotFoundException;
import ru.simankovd.springredditclone.exception.SpringRedditException;
import ru.simankovd.springredditclone.model.Post;
import ru.simankovd.springredditclone.model.Vote;
import ru.simankovd.springredditclone.repository.PostRepository;
import ru.simankovd.springredditclone.repository.VoteRepository;
import ru.simankovd.springredditclone.service.AuthService;
import ru.simankovd.springredditclone.service.VoteService;

import java.util.Optional;

import static ru.simankovd.springredditclone.model.enums.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Override
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(()-> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));

        Optional<Vote> voteByPostAndUser  = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());

        if(voteByPostAndUser.isPresent() &&
        voteByPostAndUser.get().getVoteType()
                .equals(voteDto.getVoteType())){
            throw new SpringRedditException("You have already "
            + voteDto.getVoteType() + "'d for this post");
        }
        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() -1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);

    }

    private Vote mapToVote(VoteDto voteDto, Post post){
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
