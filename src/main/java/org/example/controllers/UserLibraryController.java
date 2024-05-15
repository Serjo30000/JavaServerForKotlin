package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.UserLibraryDto;
import org.example.dto.UserLibraryDtoOut;
import org.example.mapers.UserLibraryMapper;
import org.example.models.UserLibrary;
import org.example.services.UserLibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userLibraries")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.GET,  RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class UserLibraryController {
    private final UserLibraryService aService;
    private final UserLibraryMapper mapper;

    @PutMapping("/save")
    public ResponseEntity<Integer> saveUserLibraries(@RequestBody UserLibraryDto aDto){
        var a = mapper.toEntity(aDto);
        return ResponseEntity.ok().body(aService.saveUserLibrary(a));

    }

    @GetMapping
    public ResponseEntity<List<UserLibraryDtoOut>> getAllUserLibraries(){
        return ResponseEntity.ok().body(aService.getAllUserLibrary().stream().map(mapper::toDto).toList());
    }

    @GetMapping("/user/{login}")
    public ResponseEntity<UserLibraryDtoOut> getByLoginUserLibrary(@PathVariable("login") String login){
        return ResponseEntity.ok().body(mapper.toDto(aService.getByLogin(login)));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserLibraryDtoOut>> getAllUserLibraryByRoleId(@PathVariable("role") int role){
        return ResponseEntity.ok().body(aService.getAllUserLibraryByRoleId(role).stream().map(mapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLibraryDtoOut> getByIdUserLibrary(@PathVariable("id") int id){
        return ResponseEntity.ok().body(mapper.toDto(aService.getByIdUserLibrary(id)));
    }

    @PutMapping("/updatePasswordByToken/{token}")
    public ResponseEntity<Integer> updateUserPasswordByToken(@PathVariable("token") String token, @RequestParam("password") String password){
        return ResponseEntity.ok().body(aService.updatePasswordUserByToken(token, password));
    }

    @PutMapping("/updateNotPasswordByToken/{token}")
    public ResponseEntity<Integer> updateUSerNotPasswordByToken(@PathVariable("token") String token, @RequestBody UserLibraryDto aDto){
        var a = mapper.toEntity(aDto);
        return ResponseEntity.ok().body(aService.updateNotPasswordUserByToken(token,a));
    }
}
