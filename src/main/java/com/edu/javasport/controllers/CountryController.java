package com.edu.javasport.controllers;

import com.edu.javasport.bll.constants.CountryConstants;
import com.edu.javasport.bll.errors.CountryErrors;
import com.edu.javasport.bll.errors.GeneralErrors;
import com.edu.javasport.bll.service.LeagueService;
import com.edu.javasport.dto.country.CreateCountryDto;
import com.edu.javasport.dal.entity.Country;
import com.edu.javasport.bll.service.CountryService;

import com.edu.javasport.dto.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CountryController {

    private final CountryService countryService;
    private final LeagueService leagueService;

    @Autowired
    public CountryController(CountryService countryService, LeagueService leagueService) {
        this.countryService = countryService;
        this.leagueService = leagueService;
    }

    @PostMapping("/country/create")
    public ResponseEntity<?> createCountry (@RequestBody CreateCountryDto createCountryDto) {

        final Response response = new Response();
        String result = countryService.createCountry(createCountryDto);

        if (result == CountryConstants.COUNTRY_CREATED) {
            response.message = result;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if (result == CountryErrors.COUNTRY_ALREADY_EXISTS) {
            response.error= result;
            return new ResponseEntity<>(response, HttpStatus.FOUND);
        } else {
            response.error = GeneralErrors.UNKNOWN_ERROR;
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/country/getall")
    public ResponseEntity<List<Country>> getAll () {

        final List<Country> countries = countryService.getAll();

        return countries != null && !countries.isEmpty()
                ? new ResponseEntity<>(countries, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/country/get/{id}")
    public ResponseEntity<Response> getOneById (@PathVariable Long id) {

        final Optional<Country> country = countryService.getOneById(id);
        final Response response = new Response();
        response.message = country;

        if (country != null && !country.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = CountryErrors.COUNTRY_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/country/delete/{id}")
    public ResponseEntity<?> deleteOneById (@PathVariable Long id) {
        final Response response = new Response();
        response.message = countryService.deleteById(id);

        if (response.message == CountryConstants.COUNTRY_DELETED) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = CountryErrors.COUNTRY_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
