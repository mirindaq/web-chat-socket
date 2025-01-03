package com.socket.chatzalo.service.impl;

import com.socket.chatzalo.entities.Chat;
import com.socket.chatzalo.entities.Message;
import com.socket.chatzalo.entities.User;
import com.socket.chatzalo.exception.ChatException;
import com.socket.chatzalo.exception.MessageException;
import com.socket.chatzalo.exception.UserException;
import com.socket.chatzalo.repository.ChatRepository;
import com.socket.chatzalo.repository.MessageRepository;
import com.socket.chatzalo.service.ChatService;
import com.socket.chatzalo.service.MessageService;
import com.socket.chatzalo.service.UserService;
import lombok.RequiredArgsConstructor;
import model.request.SendMessageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatService chatService;

    @Override
    @Transactional
    public Message sendMessage(SendMessageRequest sendMessageRequest) throws UserException, ChatException {
        User user = userService.findUserById(sendMessageRequest.getUserId());
        Chat chat = chatService.findChatById(sendMessageRequest.getChatId(), sendMessageRequest.getUserId());

        Message message = new Message();
        message.setUser(user);
        message.setChat(chat);
        message.setContent(sendMessageRequest.getContent());
        message.setTimeStamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages(Long chatId, Long userRqId) throws ChatException {
        Chat chat = chatService.findChatById(chatId, userRqId);
        if (chat != null) {
            return messageRepository.findByChatId(chatId);
        }
        return null;
    }

    @Override
    public Message getMessageById(Long messageId) throws MessageException {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent()) {
            return optionalMessage.get();
        }
        throw new MessageException("Message not found");
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId, Long userRqId) throws MessageException {
        Message message = getMessageById(messageId);

        if (message.getUser().getId().equals(userRqId)) {
            messageRepository.delete(message);
        }
        throw new MessageException("It's not your message");
    }
}
