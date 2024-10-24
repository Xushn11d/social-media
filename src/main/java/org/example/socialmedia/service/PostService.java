package org.example.socialmedia.service;

import org.example.socialmedia.entity.Post;
import org.example.socialmedia.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void addPost(String title, String body, String imageUrl) {
        Post post = Post.builder().title(title).body(body).imageUrl(imageUrl).commentIsActive(Boolean.TRUE).build();
        if(post!= null){
            postRepository.save(post);
        }
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
    public Post getPostByTitle(String title) {

    }



}
