package com.technobel.restapiroomequipment.models.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

    @NotBlank(message = "enter your login")
    private String username;
    @NotBlank(message = "enter your password")
    private String password;
}
