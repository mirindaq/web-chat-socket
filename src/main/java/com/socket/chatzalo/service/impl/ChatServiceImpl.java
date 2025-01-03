package com.socket.chatzalo.service.impl;

import com.socket.chatzalo.entities.Chat;
import com.socket.chatzalo.entities.User;
import com.socket.chatzalo.exception.ChatException;
import com.socket.chatzalo.exception.UserException;
import com.socket.chatzalo.repository.ChatRepository;
import com.socket.chatzalo.repository.UserRepository;
import com.socket.chatzalo.service.ChatService;
import com.socket.chatzalo.service.UserService;
import lombok.RequiredArgsConstructor;
import model.request.GroupChatRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserService userService;

    @Override
    @Transactional
    public Chat createChat(Long reqUserId, Long userId2) throws UserException {
        User userRequest = userService.findUserById(reqUserId);
        User userTarget = userService.findUserById(userId2);

        Chat chat = chatRepository.findSingleChatWithUsersId(userRequest, userTarget);

        if (chat != null) return chat;

        Chat newChat = new Chat();
        newChat.setCreatedBy(userRequest);
        newChat.getUsers().add(userTarget);
        newChat.getUsers().add(userRequest);
        newChat.setGroup(false);
        chatRepository.save(newChat);
        return newChat;
    }

    @Override
    public Chat findChatById(Long chatId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        if (chat.isPresent()) return chat.get();
        else throw new ChatException("Chat not found");
    }

    @Override
    public List<Chat> findAllChatByUserId(Long userId) throws UserException {
        return chatRepository.findAllChatByUserId(userId);
    }

    @Override
    @Transactional
    public Chat createGroupChat(Long reqUserId, GroupChatRequest groupChatRequest) throws UserException {
        User userRequest = userService.findUserById(reqUserId);

        Chat chat = new Chat();
        chat.setCreatedBy(userRequest);

        for ( Long id : groupChatRequest.getUsersId() ){
            User user = userService.findUserById(id);
            chat.getUsers().add(user);
        }
        chat.setGroup(true);
        chat.setChatImage(groupChatRequest.getChatImage());
        chat.setChatName(groupChatRequest.getChatName());
        chat.getAdmins().add(userRequest);
        chatRepository.save(chat);
        return chat;
    }

    @Override
    @Transactional
    public Chat addUserToGroup(Long chatId, Long userId, Long reqUserId) throws UserException, ChatException {
        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        User rqUser = userService.findUserById(reqUserId);

        if (optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            if ( chat.getAdmins().contains(rqUser) ){
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            }
            else throw new UserException("User not have permission");
        }
        else {
            throw new ChatException("Chat not found");
        }

    }

    @Override
    @Transactional
    public Chat renameGroup(Long chatId, String groupName, Long reqUserId) throws UserException, ChatException {
        User user = userService.findUserById(reqUserId);

        Optional<Chat> optionalChat = chatRepository.findById(chatId);
        if (optionalChat.isPresent()){
            Chat chat = optionalChat.get();
            if ( chat.getAdmins().contains(user) ){
                chat.setChatName(groupName);
                return chatRepository.save(chat);
            }
            else throw new UserException("User not have permission");
        }
        else throw new ChatException("User not found");
    }

    @Override
    public Chat removeFromGroup(Long chatId, Long userId, Long reqUserId) throws UserException, ChatException {
        return null;
    }

    @Override
    public Chat deleteChat(Long chatId, Long reqUserId) throws UserException, ChatException {
        return null;
    }
}
