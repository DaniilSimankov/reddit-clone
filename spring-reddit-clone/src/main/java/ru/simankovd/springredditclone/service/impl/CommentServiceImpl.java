package ru.simankovd.springredditclone.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.simankovd.springredditclone.dto.CommentsDto;
import ru.simankovd.springredditclone.exception.PostNotFoundException;
import ru.simankovd.springredditclone.exception.SpringRedditException;
import ru.simankovd.springredditclone.mapper.CommentMapper;
import ru.simankovd.springredditclone.model.Comment;
import ru.simankovd.springredditclone.model.NotificationEmail;
import ru.simankovd.springredditclone.model.Post;
import ru.simankovd.springredditclone.model.User;
import ru.simankovd.springredditclone.repository.CommentRepository;
import ru.simankovd.springredditclone.repository.PostRepository;
import ru.simankovd.springredditclone.repository.UserRepository;
import ru.simankovd.springredditclone.service.AuthService;
import ru.simankovd.springredditclone.service.CommentService;
import ru.simankovd.springredditclone.service.MailContentBuilder;
import ru.simankovd.springredditclone.service.MailService;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final String POST_URL = "";

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Override
    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));

        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

//        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post. " + POST_URL);
//        sendCommentNotification(message, post.getUser()); // todo add mailDev

        System.out.println(post.getUser().getUsername() + " posted a comment on your post. " + POST_URL);
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    @Override
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).toList();
    }

    @Override
    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean containsBadWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringRedditException("Comments contains unacceptable language");
        }

        return false;
    }
}
