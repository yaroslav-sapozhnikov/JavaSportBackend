package com.edu.javasport.dal.entity;

import com.edu.javasport.dto.user.AuthorizeUserDto;
import com.edu.javasport.dto.user.RegisterUserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "users")
@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    public User(RegisterUserDto registerUserDto) {
        this.username = registerUserDto.username;
        this.password = registerUserDto.password;
    }

    public User(AuthorizeUserDto authorizeUserDto) {
        this.username = authorizeUserDto.username;
        this.password = authorizeUserDto.password;
    }
}
