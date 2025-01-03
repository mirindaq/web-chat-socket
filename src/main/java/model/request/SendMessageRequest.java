package model.request;

import lombok.Data;

@Data
public class SendMessageRequest {
    private Long userId;
    private Long chatId;
    private String content;
}
