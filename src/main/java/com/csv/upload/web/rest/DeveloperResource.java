package com.csv.upload.web.rest;
import com.csv.upload.domain.Developer;
import com.csv.upload.service.DeveloperService;
import com.csv.upload.web.rest.errors.BadRequestAlertException;
import com.csv.upload.web.rest.util.HeaderUtil;
import com.csv.upload.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Developer.
 */
@RestController
@RequestMapping("/api")
public class DeveloperResource {

    private final Logger log = LoggerFactory.getLogger(DeveloperResource.class);

    private static final String ENTITY_NAME = "developer";

    private final DeveloperService developerService;

    public DeveloperResource(DeveloperService developerService) {
        this.developerService = developerService;
    }

    /**
     * POST  /developers : Create a new developer.
     *
     * @param developer the developer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new developer, or with status 400 (Bad Request) if the developer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/developers")
    public ResponseEntity<Developer> createDeveloper(@Valid @RequestBody Developer developer) throws URISyntaxException {
        log.debug("REST request to save Developer : {}", developer);
        if (developer.getId() != null) {
            throw new BadRequestAlertException("A new developer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Developer result = developerService.save(developer);
        return ResponseEntity.created(new URI("/api/developers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /developers : Updates an existing developer.
     *
     * @param developer the developer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated developer,
     * or with status 400 (Bad Request) if the developer is not valid,
     * or with status 500 (Internal Server Error) if the developer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/developers")
    public ResponseEntity<Developer> updateDeveloper(@Valid @RequestBody Developer developer) throws URISyntaxException {
        log.debug("REST request to update Developer : {}", developer);
        if (developer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Developer result = developerService.save(developer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, developer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /developers : get all the developers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of developers in body
     */
    @GetMapping("/developers")
    public ResponseEntity<List<Developer>> getAllDevelopers(Pageable pageable) {
        log.debug("REST request to get a page of Developers");
        Page<Developer> page = developerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/developers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /developers/:id : get the "id" developer.
     *
     * @param id the id of the developer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the developer, or with status 404 (Not Found)
     */
    @GetMapping("/developers/{id}")
    public ResponseEntity<Developer> getDeveloper(@PathVariable Long id) {
        log.debug("REST request to get Developer : {}", id);
        Optional<Developer> developer = developerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(developer);
    }

    /**
     * DELETE  /developers/:id : delete the "id" developer.
     *
     * @param id the id of the developer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/developers/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        log.debug("REST request to delete Developer : {}", id);
        developerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
