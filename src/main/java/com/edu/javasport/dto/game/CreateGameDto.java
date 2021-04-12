package com.edu.javasport.dto.game;

import com.edu.javasport.dal.entity.DetailedStatistics;
import com.edu.javasport.dal.entity.Team;


public class CreateGameDto {

    public Team homeTeam;
    public Team guestTeam;
    public int homeCount;
    public int guestCount;
    public DetailedStatistics homeStatistics;
    public DetailedStatistics guestStatistics;
}
