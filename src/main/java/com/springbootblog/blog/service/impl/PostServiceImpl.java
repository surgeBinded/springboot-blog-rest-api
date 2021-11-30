package com.springbootblog.blog.service.impl;

import com.springbootblog.blog.entity.Comment;
import com.springbootblog.blog.entity.Post;
import com.springbootblog.blog.exception.ResourceNotFoundException;
import com.springbootblog.blog.payload.CommentDTO;
import com.springbootblog.blog.payload.PostDTO;
import com.springbootblog.blog.payload.PostResponse;
import com.springbootblog.blog.repository.PostRepository;
import com.springbootblog.blog.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(final PostDTO postDTO) {
        final var post = mapToEntity(postDTO);
        final var newPost = postRepository.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(final int pageNo, final int pageSize, String sortBy, String sortDir) {

        final var sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        final var pageable = PageRequest.of(pageNo, pageSize, sort);
        final var posts = postRepository.findAll(pageable);
        final var content = mapListToDTO(posts.getContent());

        return new PostResponse(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
    }

    @Override
    public PostDTO getPostById(final Long id) {
        return mapToDTO(postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id)));
    }

    @Transactional
    @Override
    public PostDTO updatePost(final PostDTO postDTO, final Long id) {
        final var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDTO.title());
        post.setDescription(postDTO.description());
        post.setContent(postDTO.content());
        return mapToDTO(post);
    }

    @Override
    public void deletePost(final Long id) {
        final var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private PostDTO mapToDTO(final Post post) {
        return new PostDTO(post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getContent(),
                post.getComments().stream().map(this::mapToDTO).collect(Collectors.toSet()));
    }


    private CommentDTO mapToDTO(final Comment comment) {
        return new CommentDTO(comment.getId(),
                comment.getName(),
                comment.getEmail(),
                comment.getBody());
    }

    private Post mapToEntity(final PostDTO postDTO) {
        final var post = new Post();
        post.setTitle(postDTO.title());
        post.setDescription(postDTO.description());
        post.setContent(postDTO.content());
        return post;
    }

    private List<PostDTO> mapListToDTO(final List<Post> entities) {
        return entities.stream().map(this::mapToDTO).toList();
    }
}