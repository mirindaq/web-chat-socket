package com.socket.chatzalo.service;


import com.socket.chatzalo.entities.Message;
import com.socket.chatzalo.exception.ChatException;
import com.socket.chatzalo.exception.MessageException;
import com.socket.chatzalo.exception.UserException;
import model.request.SendMessageRequest;

import java.util.List;

public interface MessageService {

    Message sendMessage(SendMessageRequest sendMessageRequest) throws UserException, ChatException;

    List<Message> getAllMessages( Long chatId, Long userRqId ) throws ChatException;

    Message getMessageById(Long messageId) throws MessageException;

    void deleteMessage( Long messageId, Long userRqId) throws MessageException;


}
