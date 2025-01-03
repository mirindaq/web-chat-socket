package com.socket.chatzalo.controller;

import com.socket.chatzalo.entities.Message;
import com.socket.chatzalo.entities.User;
import com.socket.chatzalo.exception.ChatException;
import com.socket.chatzalo.exception.MessageException;
import com.socket.chatzalo.service.MessageService;
import com.socket.chatzalo.service.UserService;
import lombok.RequiredArgsConstructor;
import model.request.SendMessageRequest;
import model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest sendMessageRequest) throws ChatException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(username);

        sendMessageRequest.setUserId(user.getId());

        Message message = messageService.sendMessage(sendMessageRequest);
        return new ResponseEntity<Message>( message, HttpStatus.ACCEPTED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessages(@PathVariable Long chatId) throws ChatException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(username);

        List<Message> messageList = messageService.getAllMessages(chatId,user.getId());
        return new ResponseEntity<List<Message>>(messageList, HttpStatus.OK);
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessage(@PathVariable Long messageId) throws MessageException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(username);

        messageService.deleteMessage(messageId, user.getId());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Message deleted successfully");
        apiResponse.setSuccess(true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }
}
