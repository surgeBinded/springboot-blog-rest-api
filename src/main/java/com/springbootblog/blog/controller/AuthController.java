package com.springbootblog.blog.controller;

import com.springbootblog.blog.entity.User;
import com.springbootblog.blog.exception.ResourceNotFoundException;
import com.springbootblog.blog.payload.JwtAuthResponse;
import com.springbootblog.blog.payload.LoginDTO;
import com.springbootblog.blog.payload.SignUpDTO;
import com.springbootblog.blog.repository.RoleRepository;
import com.springbootblog.blog.repository.UserRepository;
import com.springbootblog.blog.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthController(final AuthenticationManager authenticationManager,
                          final UserRepository userRepository,
                          final RoleRepository roleRepository,
                          final PasswordEncoder passwordEncoder,
                          final JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDTO loginDTO) {
        final var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.usernameOrEmail(), loginDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        final var token = tokenProvider.generateToken(auth);

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDTO) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpDTO.username()))) {
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpDTO.email()))) {
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }

        final var user = new User();
        user.setName(signUpDTO.name());
        user.setEmail(signUpDTO.email());
        user.setPassword(passwordEncoder.encode(signUpDTO.password()));
        user.setUsername(signUpDTO.username());

        final var roles = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new ResourceNotFoundException("Role", "ROLE_ADMIN"));
        user.setRoles(Set.of(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User successfully registered", HttpStatus.CREATED);
    }


}
