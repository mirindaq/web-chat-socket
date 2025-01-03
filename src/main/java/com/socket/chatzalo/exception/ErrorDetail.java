package com.socket.chatzalo.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDetail {
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
