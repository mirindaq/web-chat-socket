package com.socket.chatzalo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandle(UserException e, WebRequest rq){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(LocalDateTime.now());
        errorDetail.setMessage(e.getMessage());
        errorDetail.setError(rq.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetail> messageExceptionHandle(MessageException e, WebRequest rq){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(LocalDateTime.now());
        errorDetail.setMessage(e.getMessage());
        errorDetail.setError(rq.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetail> messageExceptionHandle(NoHandlerFoundException e, WebRequest rq){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(LocalDateTime.now());
        errorDetail.setMessage("Endpoint Not Found");
        errorDetail.setError(rq.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandle(UserException e, WebRequest rq){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(LocalDateTime.now());
        errorDetail.setMessage(e.getMessage());
        errorDetail.setError(rq.getDescription(false));
        return new ResponseEntity<ErrorDetail>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetail> chatExceptionHandle(ChatException e, WebRequest rq){
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(LocalDateTime.now());
        errorDetail.setMessage(e.getMessage());
        errorDetail.setError(rq.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

}
