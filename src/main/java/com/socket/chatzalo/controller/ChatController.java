package com.socket.chatzalo.controller;

import com.socket.chatzalo.entities.Chat;
import com.socket.chatzalo.entities.User;
import com.socket.chatzalo.exception.ChatException;
import com.socket.chatzalo.service.ChatService;
import com.socket.chatzalo.service.UserService;
import lombok.RequiredArgsConstructor;
import model.request.GroupChatRequest;
import model.request.SingleChatRequest;
import model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createSingleChat(@RequestBody SingleChatRequest singleChatRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(username);

        Chat chat = chatService.createChat(user.getId(), singleChatRequest.getUserTargetId());
        return new ResponseEntity<Chat>( chat, HttpStatus.CREATED);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupChat(@RequestBody GroupChatRequest groupChatRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(username);

        Chat chat = chatService.createGroupChat(user.getId(), groupChatRequest);
        return new ResponseEntity<Chat>( chat, HttpStatus.CREATED);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> getChat(@PathVariable Long chatId) throws ChatException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(username);

        Chat chat = chatService.findChatById(chatId, user.getId());
        return new ResponseEntity<Chat>( chat, HttpStatus.OK);
    }

    @GetMapping("/user/chats")
    public ResponseEntity<List<Chat>> getListChat() throws ChatException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(username);

        List<Chat> chat = chatService.findAllChatByUserId(user.getId());
        return new ResponseEntity<List<Chat>>( chat, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToChat(@PathVariable Long chatId, @PathVariable Long userId) throws ChatException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(username);

        Chat chat = chatService.addUserToGroup(chatId, userId, user.getId());
        return new ResponseEntity<Chat>( chat, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> removeChat(@PathVariable Long chatId ) throws ChatException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(username);

        chatService.deleteChat(chatId, user.getId());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Successfully deleted chat");
        apiResponse.setSuccess(true);

        return new ResponseEntity<ApiResponse>( apiResponse, HttpStatus.OK);
    }

}
