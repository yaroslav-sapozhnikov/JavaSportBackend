package com.edu.javasport.controllers;

import com.edu.javasport.bll.constants.DetailedStatisticsConstants;
import com.edu.javasport.bll.errors.DetailedStatisticsErrors;
import com.edu.javasport.bll.errors.GeneralErrors;
import com.edu.javasport.bll.service.DetailedStatisticsService;
import com.edu.javasport.dal.entity.DetailedStatistics;

import com.edu.javasport.dto.detailedstatistics.CreateDetailedStatisticsDto;
import com.edu.javasport.dto.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DetailedStatisticsController {

    private final DetailedStatisticsService detailedStatisticsService;

    @Autowired
    public DetailedStatisticsController (DetailedStatisticsService detailedStatisticsService) {
        this.detailedStatisticsService = detailedStatisticsService;
    }

    @PostMapping("/detailedStatistics/create")
    public ResponseEntity<?> createDetailedStatistics (@RequestBody CreateDetailedStatisticsDto createDetailedStatisticsDto) {

        final Response response = new Response();
        String result = detailedStatisticsService.createDetailedStatistics(createDetailedStatisticsDto);

        response.message = result;
        return new ResponseEntity<>(response, HttpStatus.CREATED);

//        if (result == DetailedStatisticsConstants.DETAILED_STATISTICS_CREATED) {
//            response.message = result;
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        } else if (result == DetailedStatisticsErrors.DETAILED_STATISTICS_ALREADY_EXISTS) {
//            response.error= result;
//            return new ResponseEntity<>(response, HttpStatus.FOUND);
//        } else if (result == DetailedStatisticsErrors.INVALID_GAME_ID) {
//            response.error = result;
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        } else {
//            response.error = GeneralErrors.UNKNOWN_ERROR;
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }


    }

    @GetMapping("/detailedStatistics/getall")
    public ResponseEntity<List<DetailedStatistics>> getAll () {

        final List<DetailedStatistics> countries = detailedStatisticsService.getAll();

        return countries != null && !countries.isEmpty()
                ? new ResponseEntity<>(countries, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/detailedStatistics/get/{id}")
    public ResponseEntity<Response> getOneById (@PathVariable Long id) {

        final Optional<DetailedStatistics> detailedStatistics = detailedStatisticsService.getOneById(id);
        final Response response = new Response();
        response.message = detailedStatistics;

        if (detailedStatistics != null && !detailedStatistics.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = DetailedStatisticsErrors.DETAILED_STATISTICS_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/detailedStatistics/delete/{id}")
    public ResponseEntity<?> deleteOneById (@PathVariable Long id) {

        final Response response = new Response();
        response.message = detailedStatisticsService.deleteById(id);

        if (response.message == DetailedStatisticsConstants.DETAILED_STATISTICS_DELETED) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.error = DetailedStatisticsErrors.DETAILED_STATISTICS_DOES_NOT_EXIST;
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
