package com.CityBoard.rest.data;

import com.CityBoard.models.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationData {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String middle_name;

    public Users mapToUsers() {
        return Users.builder()
                .username(username)
                .password(password)
                .name(name)
                .surname(surname)
                .middle_name(middle_name)
                .build();
    }
}
