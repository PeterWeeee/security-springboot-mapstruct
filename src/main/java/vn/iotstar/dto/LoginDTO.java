package vn.iotstar.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "Username hoặc email không được để trống")
    private String username;

    @NotBlank(message = "Password không được để trống")
    private String password;
}
