package com.edu.javasport.bll.service;

import com.edu.javasport.bll.constants.DetailedStatisticsConstants;
import com.edu.javasport.bll.errors.DetailedStatisticsErrors;
import com.edu.javasport.dal.entity.DetailedStatistics;
import com.edu.javasport.dal.repository.DetailedStatisticsRepository;
import com.edu.javasport.dto.detailedstatistics.CreateDetailedStatisticsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class DetailedStatisticsService {

    @Autowired
    private DetailedStatisticsRepository detailedStatisticsRepository;


    public String createDetailedStatistics(CreateDetailedStatisticsDto createDetailedStatisticsDto) {

        DetailedStatistics detailedStatistics = new DetailedStatistics(createDetailedStatisticsDto);
        detailedStatisticsRepository.save(detailedStatistics);

        return DetailedStatisticsConstants.DETAILED_STATISTICS_CREATED;
    }

    public List<DetailedStatistics> getAll() {
        return detailedStatisticsRepository.findAll();
    }

    public Optional<DetailedStatistics> getOneById(Long id) {
        return detailedStatisticsRepository.findById(id);
    }

    public String deleteById(Long id) {

        DetailedStatistics detailedStatisticsId = new DetailedStatistics();
        detailedStatisticsId.setId(id);
        Example<DetailedStatistics> detailedStatisticsNameExample = Example.of(detailedStatisticsId);
        Optional<DetailedStatistics> detailedStatisticsNameOptional = this.detailedStatisticsRepository.findOne(detailedStatisticsNameExample);

        if (!detailedStatisticsNameOptional.isEmpty()) {
            detailedStatisticsRepository.deleteById(id);
            return DetailedStatisticsConstants.DETAILED_STATISTICS_DELETED;
        } else {
            return DetailedStatisticsErrors.DETAILED_STATISTICS_DOES_NOT_EXIST;
        }
    }
}
