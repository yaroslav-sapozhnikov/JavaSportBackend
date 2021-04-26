package com.edu.javasport.dal.entity;

import com.edu.javasport.dto.game.CreateGameDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "games")
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_team", referencedColumnName = "id")
    private Team homeTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_team", referencedColumnName = "id")
    private Team guestTeam;

    private int homeCount;
    private int guestCount;

    @OneToOne(cascade = CascadeType.REMOVE)
    private DetailedStatistics homeStatistics;

    @OneToOne(cascade = CascadeType.REMOVE)
    private DetailedStatistics guestStatistics;

    public Game (CreateGameDto createGameDto) {
        this.homeTeam = createGameDto.homeTeam;
        this.guestTeam = createGameDto.guestTeam;
        this.homeCount = createGameDto.homeCount;
        this.guestCount = createGameDto.guestCount;
        this.homeStatistics = createGameDto.homeStatistics;
        this.guestStatistics = createGameDto.guestStatistics;
    }
}
