package com.epam.esm.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AuthenticationRequestDto {
    @NotBlank(message = "{authentication.request.dto.username.not.blank}")
    private String username;

    @NotBlank(message = "{authentication.request.dto.password.not.blank}")
    @Size(min = 1, max = 30, message = "{authentication.request.dto.password.size}")
    private String password;

    @NotBlank(message = "{authentication.request.dto.email.not.blank}")
    private String email;
}
