package com.springbootblog.blog.service.impl;

import com.springbootblog.blog.entity.Comment;
import com.springbootblog.blog.entity.Post;
import com.springbootblog.blog.exception.BlogAPIException;
import com.springbootblog.blog.exception.ResourceNotFoundException;
import com.springbootblog.blog.payload.CommentDTO;
import com.springbootblog.blog.repository.CommentRepository;
import com.springbootblog.blog.repository.PostRepository;
import com.springbootblog.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(final PostRepository postRepository,
                              final CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDTO createComment(final Long postId, final CommentDTO commentDTO) {
        final var comment = mapToEntity(commentDTO);
        final var post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);
        final var newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(final Long postId) {
        final var comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDTO).toList();
    }

    @Override
    public CommentDTO getCommentById(final Long postId, final Long commentId) {

        final var post = retrievePostChecked(postId);
        final var comment = retrieveCommentChecked(commentId);

        checkIfCommentBelongToPost(post, comment);

        return mapToDTO(comment);
    }

    @Transactional
    @Override
    public CommentDTO updateComment(final Long postId, final Long commentId, final CommentDTO commentRequest) {

        final var post = retrievePostChecked(postId);
        final var comment = retrieveCommentChecked(commentId);

        checkIfCommentBelongToPost(post, comment);

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        return mapToDTO(comment);
    }

    @Override
    public void deleteComment(final Long postId, final Long commentId) {
        retrievePostChecked(postId);
        retrieveCommentChecked(commentId);

        commentRepository.deleteById(commentId);
    }

    private void checkIfCommentBelongToPost(final Post post, final Comment comment) {
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
    }

    private Post retrievePostChecked(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    }

    private Comment retrieveCommentChecked(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
    }

    private CommentDTO mapToDTO(final Comment comment) {
        var commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setName(comment.getName());
        commentDTO.setEmail(comment.getEmail());
        commentDTO.setBody(comment.getBody());
        return commentDTO;
    }

    private Comment mapToEntity(final CommentDTO commentDTO) {
        var comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setName(commentDTO.getName());
        comment.setEmail(commentDTO.getEmail());
        comment.setBody(commentDTO.getBody());
        return comment;
    }
}
