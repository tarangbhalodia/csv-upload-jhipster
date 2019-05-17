package com.csv.upload.repository;

import com.csv.upload.domain.Developer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Developer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findOneByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);
}
