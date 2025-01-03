package com.socket.chatzalo.service;

import com.socket.chatzalo.entities.Chat;
import com.socket.chatzalo.entities.User;
import com.socket.chatzalo.exception.ChatException;
import com.socket.chatzalo.exception.UserException;
import model.request.GroupChatRequest;

import java.util.List;

public interface ChatService {

    Chat createChat(Long reqUserId, Long userId2) throws UserException;

    Chat findChatById(Long chatId, Long userId) throws ChatException;

    List<Chat> findAllChatByUserId(Long userId) throws UserException;

    Chat createGroupChat(Long reqUserId, GroupChatRequest groupChatRequest) throws UserException;

    Chat addUserToGroup(Long chatId, Long userId, Long reqUserId) throws UserException, ChatException;

    Chat renameGroup(Long chatId, String groupName, Long reqUserId) throws UserException, ChatException;

    Chat removeFromGroup(Long chatId, Long userId, Long reqUserId) throws UserException, ChatException;

    void deleteChat(Long chatId, Long reqUserId) throws UserException, ChatException;
}
