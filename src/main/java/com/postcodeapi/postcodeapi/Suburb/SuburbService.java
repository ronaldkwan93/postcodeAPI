package com.postcodeapi.postcodeapi.Suburb;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuburbService {
    private final SuburbRepository suburbRepository;

    public SuburbService(SuburbRepository suburbRepository) {
        this.suburbRepository = suburbRepository;
    }

    public Suburb save(String suburb) {
        Suburb sub = new Suburb();
        sub.setSuburb(suburb);
        return suburbRepository.save(sub);
    }

    public List<Suburb> getAll() {
        return this.suburbRepository.findAll();
    }
}
