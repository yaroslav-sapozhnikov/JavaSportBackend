package com.edu.javasport.bll.service;

import com.edu.javasport.bll.constants.CountryConstants;
import com.edu.javasport.bll.constants.LeagueConstants;
import com.edu.javasport.bll.errors.CountryErrors;
import com.edu.javasport.bll.errors.GeneralErrors;
import com.edu.javasport.bll.errors.LeagueErrors;
import com.edu.javasport.dal.entity.Country;
import com.edu.javasport.dal.entity.League;
import com.edu.javasport.dal.repository.CountryRepository;
import com.edu.javasport.dal.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private CountryRepository countryRepository;

    public String createLeague (League league) {

        League leagueName = new League();
        leagueName.setName(league.getName());
        Example<League> leagueNameExample = Example.of(leagueName);
        Optional<League> leagueNameOptional = this.leagueRepository.findOne(leagueNameExample);

        Long countryId = league.getCountry().getId();
        Optional<Country> countryIdOptional = this.countryRepository.findById(countryId);

        if (leagueNameOptional.isEmpty() && !countryIdOptional.isEmpty()) {
            leagueRepository.save(league);
            return LeagueConstants.LEAGUE_CREATED;
        } else if (!leagueNameOptional.isEmpty()) {
            return LeagueErrors.LEAGUE_ALREADY_EXISTS;
        } else if (countryIdOptional.isEmpty()) {
            return LeagueErrors.INVALID_COUNTRY_ID;
        }
        return GeneralErrors.UNKNOWN_ERROR;
    }

    public List<League> getAll () {
        return leagueRepository.findAll();
    }

    public Optional<League> getOneById (Long id) {
        return leagueRepository.findById(id);
    }

    public String deleteById (Long id) {

        League leagueId = new League();
        leagueId.setId(id);
        Example<League> leagueNameExample = Example.of(leagueId);
        Optional<League> leagueNameOptional = this.leagueRepository.findOne(leagueNameExample);

        if (!leagueNameOptional.isEmpty()) {
            leagueRepository.deleteById(id);
            return LeagueConstants.LEAGUE_DELETED;
        } else {
            return LeagueErrors.LEAGUE_DOES_NOT_EXIST;
        }

    }

    public List<League> getAllByCountry (Long id) {

        Country countryId = new Country();
        countryId.setId(id);

        League leagueCountry = new League();
        leagueCountry.setCountry(countryId);
        Example<League> leagueCountryExample = Example.of(leagueCountry);

        return leagueRepository.findAll(leagueCountryExample);
    }

    public String editOneById(League league) {
        League leagueId = new League();
        leagueId.setId(league.getId());
        Example<League> leagueIdExample = Example.of(leagueId);
        Optional<League> leagueIdOptional = this.leagueRepository.findOne(leagueIdExample);

        if (!leagueIdOptional.isEmpty()) {
            leagueRepository.save(league);
            return LeagueConstants.LEAGUE_EDITED;
        } else {
            return LeagueErrors.LEAGUE_DOES_NOT_EXIST;
        }
    }
}
