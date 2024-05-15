package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLibraryDto {
    private String name;

    private String surname;

    private String login;

    private String password;

    private Date birthday;

    private String avatar;
}
