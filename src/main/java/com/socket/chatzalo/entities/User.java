package com.socket.chatzalo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String profile_picture;
    private String password;
    private String role;

    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<Notification>();


}
