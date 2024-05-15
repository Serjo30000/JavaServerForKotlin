package org.example.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.sql.Date;

@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserLibrary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="surname", nullable = false)
    private String surname;

    @Column(name="login", nullable = false, unique=true)
    private String login;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="birthday", nullable = false)
    private Date birthday;

    @Column(name="avatar")
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "roleId", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    private Role role;
}
