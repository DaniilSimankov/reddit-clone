package ru.simankovd.springredditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.simankovd.springredditclone.dto.CommentsDto;
import ru.simankovd.springredditclone.model.Comment;
import ru.simankovd.springredditclone.model.Post;
import ru.simankovd.springredditclone.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);
}
