package model.request;

import lombok.Data;

import java.util.List;

@Data
public class GroupChatRequest {

    private List<Long> usersId;
    private String chatImage;
    private String chatName;

}
