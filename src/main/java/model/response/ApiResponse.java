package model.response;

import lombok.Data;

@Data
public class ApiResponse {
    private boolean success;
    private String message;

}
