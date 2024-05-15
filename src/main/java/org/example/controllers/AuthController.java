package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.AuthenticationDto;
import org.example.dto.CheckDto;
import org.example.dto.TokenDto;
import org.example.security.JWTUtil;
import org.example.services.UserLibraryService;
import org.example.util.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*", methods = {RequestMethod.POST,RequestMethod.GET})
public class AuthController {
    private final JWTUtil jwtUtil;
    private final UserLibraryService aService;
    private final AuthenticationManager aManager;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> logIn(@RequestBody AuthenticationDto aDto){
        var authenticationToken = new UsernamePasswordAuthenticationToken(aDto.getLogin(),aDto.getPassword());
        aManager.authenticate(authenticationToken);
        var p = aService.getByLogin(aDto.getLogin());
        String jwt = jwtUtil.generateToken(aDto.getLogin(), p.getRole().getNameRole());
        return ResponseEntity.ok().body(new TokenDto(jwt));

    }

    @PostMapping("/check")
    public ResponseEntity<TokenDto> check(@RequestBody CheckDto tokenUser){
        try{
            if (aService.getByLogin(tokenUser.getLogin()).getLogin().equals(tokenUser.getLogin())){
                String jwt = jwtUtil.generateToken(tokenUser.getLogin(), tokenUser.getRole());
                return ResponseEntity.ok().body(new TokenDto(jwt));
            }
            else{
                return ResponseEntity.ok().body(new TokenDto(""));
            }
        }
        catch(Exception error){
            return ResponseEntity.ok().body(new TokenDto(""));
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ResponseError> handleException(BadCredentialsException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseError("Bad login or password", LocalDateTime.now().toString()));
    }
}
