package com.springbootblog.blog.service.impl;

import com.springbootblog.blog.entity.Post;
import com.springbootblog.blog.exception.ResourceNotFoundException;
import com.springbootblog.blog.payload.PostDTO;
import com.springbootblog.blog.payload.PostResponse;
import com.springbootblog.blog.repository.PostRepository;
import com.springbootblog.blog.service.PostService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public PostResponse getAllPosts(final int pageNo, final int pageSize, String sortBy) {
        final var pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        final var posts = postRepository.findAll(pageable);
        final var content =  mapListToDTO(posts.getContent());
        final var postResponse = new PostResponse();

        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
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
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return mapToDTO(post);
    }

    @Override
    public void deletePost(final Long id) {
        final var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private PostDTO mapToDTO(final Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        return postDTO;
    }

    private Post mapToEntity(final PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return post;
    }

    private List<PostDTO> mapListToDTO(final List<Post> entities) {
        return entities.stream().map(this::mapToDTO).toList();
    }
}
