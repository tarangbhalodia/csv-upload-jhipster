package com.csv.upload.service;

import com.csv.upload.domain.Developer;
import com.csv.upload.repository.DeveloperRepository;
import com.csv.upload.service.util.CsvReader;
import com.csv.upload.service.util.RandomUtil;
import com.csv.upload.web.api.ApiApiDelegate;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Developer.
 */
@Service
@Transactional
public class DeveloperService implements ApiApiDelegate {

    private final Logger log = LoggerFactory.getLogger(DeveloperService.class);

    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    @Override
    public ResponseEntity<String> uploadDevelopersCsvFile(MultipartFile multipartFile) {
        try {
            List<Developer> developers = CsvReader.parse(RandomUtil.convertMultipartFileToFile(multipartFile), Developer.class);

            List<Developer> upsertedList =  developers.stream().map(developer -> {
               Optional<Developer> optionalExistingDeveloper = developerRepository.findOneByFirstNameAndLastNameIgnoreCase(developer.getFirstName(), developer.getLastName());
               if (optionalExistingDeveloper.isPresent()) {
                   Developer existingDeveloper = optionalExistingDeveloper.get();
                   log.info("Developer: " + existingDeveloper.toString() + " already exists");
                   existingDeveloper.setTopSkill(developer.getTopSkill());
                  return developerRepository.save(existingDeveloper);
               } else {
                   return developerRepository.save(developer);
               }
            }).collect(Collectors.toList());
            return new ResponseEntity<>(Integer.toString(upsertedList.size()), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while reading file:", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Save a developer.
     *
     * @param developer the entity to save
     * @return the persisted entity
     */
    public Developer save(Developer developer) {
        log.debug("Request to save Developer : {}", developer);
        return developerRepository.save(developer);
    }

    /**
     * Get all the developers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Developer> findAll(Pageable pageable) {
        log.debug("Request to get all Developers");
        return developerRepository.findAll(pageable);
    }


    /**
     * Get one developer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Developer> findOne(Long id) {
        log.debug("Request to get Developer : {}", id);
        return developerRepository.findById(id);
    }

    /**
     * Delete the developer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Developer : {}", id);        developerRepository.deleteById(id);
    }
}
