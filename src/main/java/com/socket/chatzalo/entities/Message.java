package com.socket.chatzalo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Setter
@Getter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private LocalDateTime timeStamp;

    @ManyToOne
    private User user;

    @ManyToOne
    private Chat chat;



}
