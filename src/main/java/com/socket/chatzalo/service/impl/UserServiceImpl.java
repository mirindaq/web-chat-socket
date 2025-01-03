package com.socket.chatzalo.service.impl;

import com.socket.chatzalo.enums.RoleEnum;
import model.dto.UserDTO;
import com.socket.chatzalo.entities.User;
import com.socket.chatzalo.exception.UserException;
import com.socket.chatzalo.repository.UserRepository;
import com.socket.chatzalo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserById(Long id) throws UserException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("User not found with id " + id);
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        return null;
    }

    @Override
    public List<User> searchUser(String search) {

        return userRepository.findAllByFullNameContaining(search);
    }

    @Override
    public User addUser(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        String fullName = userDTO.getFullName();

        if ( userRepository.findByEmail(email) != null ) {
            throw new UserException("User with email " + email + " already exists");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole( RoleEnum.USER );

        userRepository.save(user);
        return user;
    }

    @Override
    public User findUserProfile(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if ( user == null ){
            throw new UserException("User not found with email " + email);
        }

        return user;

    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if ( user == null ){
            throw new UserException("User not found with email " + email);
        }
        return user;
    }
}
