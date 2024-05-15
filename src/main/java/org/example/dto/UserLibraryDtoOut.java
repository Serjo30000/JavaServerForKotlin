package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLibraryDtoOut {
    private String name;

    private String surname;

    private String login;

    private String role;

    private Date birthday;

    private String avatar;
}
