package com.edu.javasport.dal.entity;

import com.edu.javasport.dto.league.CreateLeagueDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "leagues")
@Data
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country", referencedColumnName = "id")
    private Country country;

    public League (CreateLeagueDto createLeagueDto) {
        this.name = createLeagueDto.name;
        this.country = createLeagueDto.country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() { return country; }

    public void setCountry(Country country) {
        this.country = country;
    }
}