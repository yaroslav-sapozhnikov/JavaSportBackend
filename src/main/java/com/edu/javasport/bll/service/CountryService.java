package com.edu.javasport.bll.service;

import com.edu.javasport.bll.constants.CountryConstants;
import com.edu.javasport.bll.errors.CountryErrors;
import com.edu.javasport.dto.country.CreateCountryDto;
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
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    public String createCountry (CreateCountryDto createCountryDto) {

        Country country = new Country(createCountryDto);

        Country countryName = new Country();
        countryName.setName(country.getName());
        Example<Country> countryNameExample = Example.of(countryName);
        Optional<Country> countryNameOptional = this.countryRepository.findOne(countryNameExample);

        Country countryShortName = new Country();
        countryShortName.setShortName(country.getShortName());
        Example<Country> countryShortNameExample = Example.of(countryShortName);
        Optional<Country> countryShortNameOptional = this.countryRepository.findOne(countryShortNameExample);

        if (countryShortNameOptional.isEmpty() && countryNameOptional.isEmpty()) {
            countryRepository.save(country);
            return CountryConstants.COUNTRY_CREATED;
        } else {
            return CountryErrors.COUNTRY_ALREADY_EXISTS;
        }

    }

    public List<Country> getAll () {
        return countryRepository.findAll();
    }

    public Optional<Country> getOneById (Long id) {
        return countryRepository.findById(id);
    }

    public String deleteById (Long id) {

        Country countryId = new Country();
        countryId.setId(id);
        Example<Country> countryIdExample = Example.of(countryId);
        Optional<Country> countryIdOptional = this.countryRepository.findOne(countryIdExample);

        if (!countryIdOptional.isEmpty()) {
            countryRepository.deleteById(id);
            return CountryConstants.COUNTRY_DELETED;
        } else {
            return CountryErrors.COUNTRY_DOES_NOT_EXIST;
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

    public String editOneById(Country country) {
        Country countryId = new Country();
        countryId.setId(country.getId());
        Example<Country> countryIdExample = Example.of(countryId);
        Optional<Country> countryIdOptional = this.countryRepository.findOne(countryIdExample);

        if (!countryIdOptional.isEmpty()) {
            countryRepository.save(country);
            return CountryConstants.COUNTRY_EDITED;
        } else {
            return CountryErrors.COUNTRY_DOES_NOT_EXIST;
        }
    }
}
