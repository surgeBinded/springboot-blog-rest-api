package com.springbootblog.blog.service.impl;

import com.springbootblog.blog.entity.Post;
import com.springbootblog.blog.exception.ResourceNotFoundException;
import com.springbootblog.blog.payload.PostDTO;
import com.springbootblog.blog.repository.PostRepository;
import com.springbootblog.blog.service.PostService;
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
    public List<PostDTO> getAllPosts() {
        return mapListToDTO(postRepository.findAll());
    }

    @Override
    public PostDTO getPostById(Long id) {
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

    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        return postDTO;
    }

    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setContent(postDTO.getContent());
        return post;
    }

    private List<PostDTO> mapListToDTO(List<Post> entities){
        return entities.stream().map(this::mapToDTO).toList();
    }
}
