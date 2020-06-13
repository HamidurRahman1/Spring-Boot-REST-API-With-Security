package com.hamidur.ss.services;

import com.hamidur.ss.dao.models.Comment;
import com.hamidur.ss.dao.repos.CommentRepository;
import com.hamidur.ss.exceptions.custom.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService
{
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() throws NotFoundException
    {
        List<Comment> comments = commentRepository.findAll();
        if(comments == null)
            throw new NotFoundException("No comments found to return");
        return comments;
    }

    public Comment getCommentById(Integer commentId) throws NotFoundException
    {
        Comment comment = commentRepository.findByCommentId(commentId);
        if(comment == null)
            throw new NotFoundException("No comment found associated with commentId="+commentId);
        return comment;
    }

    public List<Comment> getAllCommentsByArticleId(Integer articleId) throws NotFoundException
    {
        List<Comment> comments = commentRepository.getCommentsByArticleId(articleId);
        if(comments == null)
            throw new NotFoundException("No comments found associated with articleId="+articleId);
        return comments;
    }

    public void updateCommentByCommentIdAndArticleId(Integer articleId, Integer commentId, String comment)
    {
        commentRepository.updateCommentByCommentIdAndArticleId(comment, commentId, articleId);
    }
}
