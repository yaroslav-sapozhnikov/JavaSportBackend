package com.edu.javasport.dal.entity;


import com.edu.javasport.dto.country.CreateCountryDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "countries")
@NoArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String shortName;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    public List<League> leagues;

    public Country (CreateCountryDto createCountryDto) {
        this.name = createCountryDto.name;
        this.shortName = createCountryDto.shortName;
    }
}
