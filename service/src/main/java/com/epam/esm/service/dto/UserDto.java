package com.epam.esm.service.dto;

import com.epam.esm.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends RepresentationModel<UserDto> {
    @Null(message = "{user.id.null}")
    private Long id;

    @NotBlank(message = "{user.username.not.blank}")
    @Size(min = 1, max = 30, message = "{user.username.size}")
    private String username;

    @NotBlank(message = "{user.email.not.blank}")
    private String email;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
