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

   public Post createPost(Post post) {
        return postRepository.save(post);
   }
   public List<Post> getAllPosts() {
        return postRepository.findAll();
   }
   public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
   }
   public void deletePostById(Long id) {
        postRepository.deleteById(id);
   }



}
