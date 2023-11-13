package ru.simankovd.springredditclone.service;

import ru.simankovd.springredditclone.dto.PostRequest;
import ru.simankovd.springredditclone.dto.PostResponse;
import ru.simankovd.springredditclone.model.Post;

import java.util.List;

public interface PostService {
    void save(PostRequest postRequest);

    List<PostResponse> getAllPosts();

    PostResponse getPost(Long id);

    List<PostResponse> getPostsBySubreddit(Long subredditId);

    List<PostResponse> getPostsByUsername(String username);
}
