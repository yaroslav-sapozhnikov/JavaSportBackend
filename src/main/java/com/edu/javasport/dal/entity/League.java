package com.edu.javasport.dal.entity;

import com.edu.javasport.dto.league.CreateLeagueDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "leagues")
@Data
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country", referencedColumnName = "id")
    private Country country;

    @JsonBackReference(value = "teams")
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<Team> teams;

    public League (CreateLeagueDto createLeagueDto) {
        this.name = createLeagueDto.name;
        this.country = createLeagueDto.country;
    }
}