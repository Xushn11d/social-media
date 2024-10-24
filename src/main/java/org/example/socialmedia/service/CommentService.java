package org.example.socialmedia.service;

import org.example.socialmedia.entity.Comment;
import org.example.socialmedia.entity.Post;
import org.example.socialmedia.repository.CommentRepository;
import org.example.socialmedia.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Comment createComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() ->new RuntimeException("Post not found" + postId));
        comment.setPost(post);
        return commentRepository.save(comment);
    }
    public List<Comment> getComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found" + postId));
        return post.getComments();
    }





}
