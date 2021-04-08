package com.edu.javasport.controllers;

import com.edu.javasport.bll.constants.LeagueConstants;
import com.edu.javasport.bll.errors.GeneralErrors;
import com.edu.javasport.bll.errors.LeagueErrors;
import com.edu.javasport.bll.service.LeagueService;
import com.edu.javasport.dto.league.CreateLeagueDto;
import com.edu.javasport.dal.entity.League;

import com.edu.javasport.dto.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LeagueController {

    private final LeagueService leagueService;

    @Autowired
    public LeagueController (LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @PostMapping("/league/create")
    public ResponseEntity<?> createLeague (@RequestBody CreateLeagueDto createLeagueDto) {

        League league = new League(createLeagueDto);
        final Response response = new Response();
        String result = leagueService.createLeague(league);

        if (result == LeagueConstants.LEAGUE_CREATED) {
            response.message = result;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (result == LeagueErrors.LEAGUE_ALREADY_EXISTS) {
            response.error= result;
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } else if (result == LeagueErrors.INVALID_COUNTRY_ID) {
            response.error = result;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {
            response.error = GeneralErrors.UNKNOWN_ERROR;
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/league/getall")
    public ResponseEntity<List<League>> getAll () {

        final List<League> countries = leagueService.getAll();

        return countries != null && !countries.isEmpty()
                ? new ResponseEntity<>(countries, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/league/get/{id}")
    public ResponseEntity<Response> getOneById (@PathVariable Long id) {

        final Optional<League> league = leagueService.getOneById(id);
        final Response response = new Response();
        response.message = league;

        if (league != null && !league.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = LeagueErrors.LEAGUE_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/league/delete/{id}")
    public ResponseEntity<?> deleteOneById (@PathVariable Long id) {

        final Response response = new Response();
        response.message = leagueService.deleteById(id);

        if (response.message == LeagueConstants.LEAGUE_DELETED) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = LeagueErrors.LEAGUE_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/league/getbycountryid/{id}")
    public ResponseEntity<Response> getAllByCountry (@PathVariable Long id) {

        final List<League> leagues = leagueService.getAllByCountry(id);
        final Response response = new Response();
        response.message = leagues;

        if (leagues != null && !leagues.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = LeagueErrors.LEAGUES_DO_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}