package com.edu.javasport.dal.repository;

import com.edu.javasport.dal.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {}
