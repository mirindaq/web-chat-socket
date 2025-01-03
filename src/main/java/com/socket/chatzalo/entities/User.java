package com.socket.chatzalo.entities;

import com.socket.chatzalo.enums.RoleEnum;
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

    @Column
    private String fullName;
    @Column
    private String email;
    @Column
    private String profile_picture;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @OneToMany( mappedBy = "user", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<Notification>();


}
