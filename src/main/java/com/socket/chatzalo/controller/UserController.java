package com.socket.chatzalo.controller;

import com.socket.chatzalo.entities.User;
import com.socket.chatzalo.repository.UserRepository;
import com.socket.chatzalo.service.UserService;
import lombok.RequiredArgsConstructor;
import model.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUser(@PathVariable String query) {
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserDTO userDTO) {
        User user = userService.updateUser(userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
