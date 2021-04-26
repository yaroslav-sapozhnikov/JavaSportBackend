package com.edu.javasport.dal.entity;

import com.edu.javasport.dto.team.CreateTeamDto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "teams")
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String shortName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "league", referencedColumnName = "id")
    private League league;

    @JsonBackReference(value = "guestGame")
    @OneToMany(mappedBy = "guestTeam", cascade = CascadeType.ALL)
    private List<Game> guestGame;

    @JsonBackReference(value = "homeGame")
    @OneToMany(mappedBy = "homeTeam", cascade = CascadeType.ALL)
    private List<Game> homeGame;

    public Team (CreateTeamDto createTeamDto) {
        this.name = createTeamDto.name;
        this.shortName = createTeamDto.shortName;
        this.league = createTeamDto.league;
    }
}
