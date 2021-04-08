package com.edu.javasport.controllers;

import com.edu.javasport.bll.constants.TeamConstants;
import com.edu.javasport.bll.errors.GeneralErrors;
import com.edu.javasport.bll.errors.TeamErrors;
import com.edu.javasport.bll.service.TeamService;
import com.edu.javasport.dal.entity.Team;
import com.edu.javasport.dto.team.CreateTeamDto;
import com.edu.javasport.dto.response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController (TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/team/create")
    public ResponseEntity<?> createTeam (@RequestBody CreateTeamDto createTeamDto) {

        Team team = new Team(createTeamDto);
        final Response response = new Response();
        String result = teamService.createTeam(team);

        if (result == TeamConstants.TEAM_CREATED) {
            response.message = result;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (result == TeamErrors.TEAM_ALREADY_EXISTS) {
            response.error= result;
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } else if (result == TeamErrors.INVALID_LEAGUE_ID) {
            response.error = result;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.error = GeneralErrors.UNKNOWN_ERROR;
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/team/getall")
    public ResponseEntity<List<Team>> getAll () {

        final List<Team> countries = teamService.getAll();

        return countries != null && !countries.isEmpty()
                ? new ResponseEntity<>(countries, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/team/get/{id}")
    public ResponseEntity<Response> getOneById (@PathVariable Long id) {

        final Optional<Team> team = teamService.getOneById(id);
        final Response response = new Response();
        response.message = team;

        if (team != null && !team.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = TeamErrors.TEAM_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/team/delete/{id}")
    public ResponseEntity<?> deleteOneById (@PathVariable Long id) {

        final Response response = new Response();
        response.message = teamService.deleteById(id);

        if (response.message == TeamConstants.TEAM_DELETED) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = TeamErrors.TEAM_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/team/getbyleagueid/{id}")
    public ResponseEntity<Response> getAllByLeague (@PathVariable Long id) {

        final List<Team> teams = teamService.getAllByLeague(id);
        final Response response = new Response();
        response.message = teams;

        if (teams != null && !teams.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = TeamErrors.TEAMS_DO_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
