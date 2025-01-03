package com.socket.chatzalo.repository;

import com.socket.chatzalo.entities.Chat;
import com.socket.chatzalo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(" select c from Chat c join c.users u where u.id = :userId")
    List<Chat> findAllChatByUserId(@Param("userId") Long userId);

    @Query(" select c from Chat c where c.isGroup=false and :user member of c.users and :reqUser member of c.users" )
    Chat findSingleChatWithUsersId(@Param("user") User user, @Param("reqUser") User reqUser);
}
