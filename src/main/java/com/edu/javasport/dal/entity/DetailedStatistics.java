package com.edu.javasport.dal.entity;

import com.edu.javasport.dto.detailedstatistics.CreateDetailedStatisticsDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "detailed_statistics")
@NoArgsConstructor
public class DetailedStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ballPossession;
    private int totalShots;
    private int shotsOnGoals;
    private int freeKicks;
    private int corners;
    private int fouls;
    private int offsides;
    private int yellowCards;
    private int redCards;


    public DetailedStatistics (CreateDetailedStatisticsDto createDetailedStatisticsDto) {
        this.ballPossession = createDetailedStatisticsDto.ballPossession;
        this.totalShots = createDetailedStatisticsDto.totalShots;
        this.shotsOnGoals = createDetailedStatisticsDto.shotsOnGoals;
        this.freeKicks = createDetailedStatisticsDto.freeKicks;
        this.corners = createDetailedStatisticsDto.corners;
        this.fouls = createDetailedStatisticsDto.fouls;
        this.offsides = createDetailedStatisticsDto.offsides;
        this.yellowCards = createDetailedStatisticsDto.yellowCards;
        this.redCards = createDetailedStatisticsDto.redCards;
    }
}
