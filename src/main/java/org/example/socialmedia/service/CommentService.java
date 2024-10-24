package org.example.socialmedia.service;

import org.example.socialmedia.entity.Comment;
import org.example.socialmedia.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }
    public Comment getComment(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
