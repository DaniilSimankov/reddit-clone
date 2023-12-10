package ru.simankovd.springredditclone.service;

import ru.simankovd.springredditclone.dto.CommentsDto;

import java.util.List;

public interface CommentService {
    void save(CommentsDto commentDto);

    List<CommentsDto> getAllCommentsForPost(Long postId);

    List<CommentsDto> getAllCommentsForUser(String userName);

    boolean containsBadWords(String comment);
}
