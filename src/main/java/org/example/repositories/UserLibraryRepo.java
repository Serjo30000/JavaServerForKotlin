package org.example.repositories;

import org.example.models.UserLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserLibraryRepo extends JpaRepository<UserLibrary,Integer> {
    List<UserLibrary> findByRoleId(int role);
    @Modifying
    @Query("update UserLibrary u set u.login=:newLogin, u.name=:name, u.surname=:surname, u.birthday=:birthday, u.avatar=:avatar where u.login=:oldLogin")
    void updateUserNotPasswordByLogin(@Param("oldLogin") String oldLogin, @Param("newLogin") String newLogin, @Param("name") String name, @Param("surname") String surname, @Param("birthday") Date birthday, @Param("avatar") String avatar);

    @Modifying
    @Query("update UserLibrary u set u.password=:password where u.login=:oldLogin")
    void updateUserPasswordByLogin(@Param("oldLogin") String oldLogin, @Param("password") String password);
    Optional<UserLibrary> findByLogin(String  login);
}
