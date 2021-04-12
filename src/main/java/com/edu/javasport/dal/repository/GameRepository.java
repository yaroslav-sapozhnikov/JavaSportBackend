package com.edu.javasport.dal.repository;

import com.edu.javasport.dal.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {}
