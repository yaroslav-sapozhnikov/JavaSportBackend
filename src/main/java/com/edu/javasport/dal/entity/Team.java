package com.edu.javasport.dal.entity;

import com.edu.javasport.dto.team.CreateTeamDto;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league", referencedColumnName = "id")
    private League league;

    public Team (CreateTeamDto createTeamDto) {
        this.name = createTeamDto.name;
        this.shortName = createTeamDto.shortName;
        this.league = createTeamDto.league;
    }
}
