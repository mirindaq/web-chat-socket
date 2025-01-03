package model.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private String profile_picture;
    private String password;

}
