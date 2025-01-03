package com.socket.chatzalo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String chatName;

    @Column
    private String chatImage;

    @Column
    private boolean isGroup;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToMany
    private Set<User> admins = new HashSet<>();

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @OneToMany( mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();
}
