package org.example.socialmedia.service;

import org.example.socialmedia.entity.Post;
import org.example.socialmedia.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private void addPost(String title, String body, String imageUrl) {
        Post post = Post.builder().title(title).body(body).imageUrl(imageUrl).commentIsActive(Boolean.TRUE).build();
        if(post!= null){
            postRepository.save(post);
        }
    }


}
