package com.postcodeapi.postcodeapi.Suburb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SuburbRepository extends JpaRepository<Suburb, Long> {
    @Query(value = "SELECT * FROM suburbs WHERE suburb = :suburb", nativeQuery = true)
    Optional<Suburb> findByValue(@Param("suburb") String suburb);
}
