package com.edu.javasport.dal.repository;

import com.edu.javasport.dal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
