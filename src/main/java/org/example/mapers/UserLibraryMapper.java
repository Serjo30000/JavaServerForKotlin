package org.example.mapers;

import org.example.dto.UserLibraryDto;
import org.example.dto.UserLibraryDtoOut;
import org.example.models.UserLibrary;
import org.springframework.stereotype.Component;

@Component
public class UserLibraryMapper {
    public UserLibraryDtoOut toDto(UserLibrary aDto){
        var a = new UserLibraryDtoOut();
        a.setName(aDto.getName());
        a.setSurname(aDto.getSurname());
        a.setLogin(aDto.getLogin());
        a.setBirthday(aDto.getBirthday());
        a.setAvatar(aDto.getAvatar());
        a.setRole(aDto.getRole().getNameRole());
        return a;
    }

    public UserLibrary toEntity(UserLibraryDto aDto){
        var a = new UserLibrary();
        a.setName(aDto.getName());
        a.setSurname(aDto.getSurname());
        a.setLogin(aDto.getLogin());
        a.setAvatar(aDto.getAvatar());
        a.setBirthday(aDto.getBirthday());
        a.setPassword(aDto.getPassword());
        return a;
    }
}
