package com.postcodeapi.postcodeapi.Postcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostCodeRepository extends JpaRepository<Postcode, Long> {

    @Query(value = "SELECT * FROM postcodes WHERE code = :postcode", nativeQuery = true)
    Optional<Postcode> findByValue(@Param("postcode") String postcode);

    @Query(value = "SELECT DISTINCT p.* FROM postcodes AS p JOIN suburb_postcode AS sp ON p.id = sp.postcode_id JOIN suburbs AS s ON s.id = sp.suburb_id WHERE s.suburb = :suburb AND p.state = :state ", nativeQuery = true)
    Optional<Postcode> findByStateAndSuburb(@Param("suburb") String state, @Param("state") String suburb);
}
