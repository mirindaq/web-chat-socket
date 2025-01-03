package com.socket.chatzalo.controller;

import com.socket.chatzalo.config.jwt.JwtUtil;
import com.socket.chatzalo.entities.User;
import com.socket.chatzalo.service.CustomUserService;
import com.socket.chatzalo.service.UserService;
import lombok.RequiredArgsConstructor;
import model.dto.UserDTO;
import model.request.LoginRequest;
import model.response.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CustomUserService customUserService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody UserDTO userDTO) {
        User user = userService.addUser(userDTO);

        Authentication authentication = new UsernamePasswordAuthenticationToken( user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateTokenFromUsername(userDetails);

        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAuth(true);
        authResponse.setToken(jwt);
        authResponse.setRole(role);

        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        Authentication authentication = authenticate(req.getEmail(), req.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtil.generateTokenFromUsername(userDetails);
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAuth(true);
        authResponse.setToken(jwt);
        authResponse.setRole(role);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if ( userDetails == null ) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        if ( !passwordEncoder.matches(password, userDetails.getPassword()) ) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
