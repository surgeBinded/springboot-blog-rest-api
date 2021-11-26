package com.springbootblog.blog.service;

import com.springbootblog.blog.payload.CommentDTO;

import java.util.List;

public interface CommentService {

    CommentDTO createComment(final Long postId, final CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPostId(final Long postId);
    CommentDTO getCommentById(final Long postId, final Long commentId);
    CommentDTO updateComment(final Long postId, final Long commentId, final CommentDTO commentRequest);

    void deleteComment(final Long postId, final Long commentId);
}
