package com.socket.chatzalo.service;

import model.dto.UserDTO;
import com.socket.chatzalo.entities.User;
import com.socket.chatzalo.exception.UserException;

import java.util.List;

public interface UserService {

    User findUserById(Long id) throws UserException;

    User updateUser(UserDTO userDTO);

    List<User> searchUser(String search);

    User addUser(UserDTO userDTO);

    User findUserProfile( String email) throws UserException;

    User findUserByEmail(String email) throws UserException;
}
