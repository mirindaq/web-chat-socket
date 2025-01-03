package com.socket.chatzalo.repository;

import com.socket.chatzalo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    List<User> findAllByFullNameContaining(String query);
}
