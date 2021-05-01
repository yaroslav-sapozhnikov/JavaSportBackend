package com.edu.javasport.bll.service;

import com.edu.javasport.bll.constants.UserConstants;
import com.edu.javasport.bll.errors.UserErrors;
import com.edu.javasport.dal.entity.User;
import com.edu.javasport.dal.repository.UserRepository;
import com.edu.javasport.dto.user.AuthorizeUserDto;
import com.edu.javasport.dto.user.RegisterUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String registerUser (RegisterUserDto createUserDto) {

        User user = new User(createUserDto);

        User userName = new User();
        userName.setUsername(user.getUsername());
        Example<User> userNameExample = Example.of(userName);
        Optional<User> userNameOptional = this.userRepository.findOne(userNameExample);

        if (userNameOptional.isEmpty()) {
            userRepository.save(user);
            return UserConstants.USER_REGISTERED;
        } else {
            return UserErrors.USER_ALREADY_EXISTS;
        }

    }

    public String authorizeUser (AuthorizeUserDto authorizeUserDto) {

        User user = new User(authorizeUserDto);

        User userName = new User();
        userName.setUsername(user.getUsername());
        Example<User> userNameExample = Example.of(userName);
        Optional<User> userNameOptional = this.userRepository.findOne(userNameExample);

        if (userNameOptional.isEmpty()) {
            return UserErrors.USER_DOES_NOT_EXIST;
        } else if (user.getPassword().equals(userNameOptional.get().getPassword())) {
            return UserConstants.USER_AUTHORIZED;
        } else {
            return UserErrors.WRONG_PASSWORD;
        }
    }

}
