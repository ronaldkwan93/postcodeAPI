package com.postcodeapi.postcodeapi.Suburb;

import com.postcodeapi.postcodeapi.Postcode.AddPostcodeDTO;
import com.postcodeapi.postcodeapi.Postcode.Postcode;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suburb")
public class SuburbController {


    private final SuburbService suburbService;

    public SuburbController(SuburbService suburbService) {
        this.suburbService = suburbService;
    }

    @GetMapping
    public ResponseEntity<List<Suburb>> getAllSuburbs() {
        List<Suburb> suburbs = this.suburbService.getAll();
        return new ResponseEntity<>(suburbs, HttpStatus.OK);
    }


    @PutMapping
    public Suburb assignPostCodeToSuburb(@RequestBody @Valid AssignPostCodeDTO data ) {
        Long suburbId = data.getSuburbId();
        Long postId = data.getPostCodeId();

        return suburbService.assignPostCodeToSuburb(suburbId, postId);
    }
}
