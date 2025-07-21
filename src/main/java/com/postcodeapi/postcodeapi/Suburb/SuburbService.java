package com.postcodeapi.postcodeapi.Suburb;

import com.postcodeapi.postcodeapi.Postcode.PostCodeRepository;
import com.postcodeapi.postcodeapi.Postcode.Postcode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuburbService {
    private final SuburbRepository suburbRepository;
    private final PostCodeRepository postCodeRepository;

    public SuburbService(SuburbRepository suburbRepository, PostCodeRepository postCodeRepository) {
        this.suburbRepository = suburbRepository;
        this.postCodeRepository = postCodeRepository;
    }


    public List<Suburb> getAll() {
        return this.suburbRepository.findAll();
    }

    public Suburb assignPostCodeToSuburb(Long suburbId, Long postId) {
        Suburb suburb = suburbRepository.findById(suburbId).orElseThrow();
        Postcode postCode = postCodeRepository.findById(postId).orElseThrow();

        suburb.getPostcodeSet().add(postCode);
        return suburbRepository.save(suburb);
    }
    // assign postcode to suburb

    public void addSuburb(String sub) {
        Suburb suburb = new Suburb();
        suburb.setSuburb(sub);
        this.suburbRepository.save(suburb);

    }

    public Optional<Suburb> findSuburbBySuburb(String suburb) {
        return this.suburbRepository.findByValue(suburb);
    }

}
