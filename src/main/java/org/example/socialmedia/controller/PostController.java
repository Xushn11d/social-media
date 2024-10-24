package org.example.socialmedia.controller;

import org.example.socialmedia.entity.Post;
import org.example.socialmedia.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return ResponseEntity.ok(postService.createPost(post));
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

}
