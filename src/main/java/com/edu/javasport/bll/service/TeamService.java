package com.edu.javasport.bll.service;

import com.edu.javasport.bll.constants.LeagueConstants;
import com.edu.javasport.bll.constants.TeamConstants;
import com.edu.javasport.bll.errors.GeneralErrors;
import com.edu.javasport.bll.errors.LeagueErrors;
import com.edu.javasport.bll.errors.TeamErrors;
import com.edu.javasport.dal.entity.Country;
import com.edu.javasport.dal.entity.League;
import com.edu.javasport.dal.entity.Team;
import com.edu.javasport.dal.repository.CountryRepository;
import com.edu.javasport.dal.repository.LeagueRepository;
import com.edu.javasport.dal.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    public String createTeam (Team team) {

        Team teamName = new Team();
        teamName.setName(team.getName());
        Example<Team> teamNameExample = Example.of(teamName);
        Optional<Team> teamNameOptional = this.teamRepository.findOne(teamNameExample);

        Long leagueId = team.getLeague().getId();
        Optional<League> leagueIdOptional = this.leagueRepository.findById(leagueId);

        if (teamNameOptional.isEmpty() && !leagueIdOptional.isEmpty()) {
            teamRepository.save(team);
            return TeamConstants.TEAM_CREATED;
        } else if (!teamNameOptional.isEmpty()) {
            return TeamErrors.TEAM_ALREADY_EXISTS;
        } else if (leagueIdOptional.isEmpty()) {
            return TeamErrors.INVALID_LEAGUE_ID;
        }
        return GeneralErrors.UNKNOWN_ERROR;
    }

    public List<Team> getAll () {
        return teamRepository.findAll();
    }

    public Optional<Team> getOneById (Long id) {
        return teamRepository.findById(id);
    }

    public String deleteById (Long id) {

        Team teamId = new Team();
        teamId.setId(id);
        Example<Team> teamNameExample = Example.of(teamId);
        Optional<Team> teamNameOptional = this.teamRepository.findOne(teamNameExample);

        if (!teamNameOptional.isEmpty()) {
            teamRepository.deleteById(id);
            return TeamConstants.TEAM_DELETED;
        } else {
            return TeamErrors.TEAM_DOES_NOT_EXIST;
        }

    }

    public List<Team> getAllByLeague (Long id) {

        League leagueId = new League();
        leagueId.setId(id);

        Team teamLeague = new Team();
        teamLeague.setLeague(leagueId);
        Example<Team> teamCountryExample = Example.of(teamLeague);

        return teamRepository.findAll(teamCountryExample);
    }

    public String editOneById(Team team) {
        Team teamId = new Team();
        teamId.setId(team.getId());
        Example<Team> teamIdExample = Example.of(teamId);
        Optional<Team> teamIdOptional = this.teamRepository.findOne(teamIdExample);

        if (!teamIdOptional.isEmpty()) {
            teamRepository.save(team);
            return TeamConstants.TEAM_EDITED;
        } else {
            return TeamErrors.TEAM_DOES_NOT_EXIST;
        }
    }
}
