package org.example.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.models.Role;
import org.example.models.UserLibrary;
import org.example.repositories.RoleRepo;
import org.example.repositories.UserLibraryRepo;
import org.example.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.example.exceptions.UserLibraryNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.auth0.jwt.JWTVerifier;
import org.springframework.stereotype.Service;

@Service
public class UserLibraryService {
    @Autowired
    private UserLibraryRepo userLibraryRepo;
    @Autowired
    private RoleRepo pRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtil jwtUtil;

    public List<UserLibrary> getAllUserLibrary(){
        return userLibraryRepo.findAll();
    }

    public List<UserLibrary> getAllUserLibraryByRoleId(int role){
        return userLibraryRepo.findByRoleId(role);
    }

    public UserLibrary getByIdUserLibrary(int id){
        return userLibraryRepo.findById(id)
                .orElseThrow(UserLibraryNotFoundException::new);
    }

    @Transactional
    public int saveUserLibrary(UserLibrary a){
        var r = pRepository.findByNameRole("ROLE_USER")
                .orElse(new Role("ROLE_USER"));
        a.setRole(r);
        var us = Optional.ofNullable(r.getUserLibraries());
        var l = us.orElse(new ArrayList<>());
        l.add(a);
        r.setUserLibraries(l);
        a.setPassword(passwordEncoder.encode(a.getPassword()));

        if (a.getAvatar()==null || a.getAvatar().isEmpty()){
            a.setAvatar("default.jpg");
        }

        for (UserLibrary usr: userLibraryRepo.findAll()){
            if (a.getLogin().equals(usr.getLogin()) || a.getAvatar().equals(usr.getAvatar()) && !a.getAvatar().equals("default.jpg")){
                return 0;
            }
        }
        return userLibraryRepo.save(a).getId();
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public Integer updatePasswordUserByToken(String token, String password){
        if (token==null || token.equals("")){
            return 0;
        }

        String login = jwtUtil.validateTokenAndRetrieveClaim(token);

        if (login==null || password==null || login.equals("") || password.equals("")){
            return 0;
        }

        Optional<UserLibrary> user = userLibraryRepo.findByLogin(login);

        if (user.isEmpty()){
            return 0;
        }

        user.get().setPassword(passwordEncoder.encode(password));

        userLibraryRepo.updateUserPasswordByLogin(login, user.get().getPassword());
        return 1;
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public Integer updateNotPasswordUserByToken(String token, UserLibrary user){

        if (token==null || token.equals("")){
            return 0;
        }

        String login = jwtUtil.validateTokenAndRetrieveClaim(token);

        if (login==null || login.equals("")){
            return 0;
        }

        Optional<UserLibrary> userCheck = userLibraryRepo.findByLogin(login);

        if (userCheck.isEmpty()){
            return 0;
        }

        int countLogin = 0;
        int countAvatar = 0;

        if (user.getAvatar()==null || user.getAvatar().isEmpty()){
            user.setAvatar(userCheck.get().getAvatar());
        }

        for (UserLibrary usr: userLibraryRepo.findAll()){
            if (user.getLogin().equals(usr.getLogin())){
                countLogin++;
            }
            if (user.getAvatar().equals(usr.getAvatar()) && !user.getAvatar().equals("default.jpg")){
                countAvatar++;
            }
            if (countAvatar>1 || countLogin>1){
                return 0;
            }
        }

        userLibraryRepo.updateUserNotPasswordByLogin(login, user.getLogin(), user.getName(), user.getSurname(), user.getBirthday(), user.getAvatar());
        return 1;
    }

    public UserLibrary getByLogin(String login){
        return userLibraryRepo.findByLogin(login)
                .orElseThrow(UserLibraryNotFoundException::new);
    }
}
