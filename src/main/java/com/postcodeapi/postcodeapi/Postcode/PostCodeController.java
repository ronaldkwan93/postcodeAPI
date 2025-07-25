package com.postcodeapi.postcodeapi.Postcode;

import com.postcodeapi.postcodeapi.Suburb.Suburb;
import com.postcodeapi.postcodeapi.Suburb.SuburbService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postcode")
public class PostCodeController {

    private final PostCodeService postCodeService;
    private final SuburbService suburbService;

    public PostCodeController(PostCodeService postCodeService, SuburbService suburbService) {
        this.postCodeService = postCodeService;
        this.suburbService = suburbService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Postcode>> getPostCodes() {

        List<Postcode> postcodes = postCodeService.getAll();

        return new ResponseEntity<>(postcodes, HttpStatus.OK);
    }

    @GetMapping("/findSuburbs")
    public Optional<Postcode> getPostCode(@RequestParam String postcode ) {
        return this.postCodeService.findPostCodeByCode(postcode);
    }

    @GetMapping("/find")
    public ResponseEntity<String> getPostCodeBySuburbAndState(@RequestParam String suburb, @RequestParam String state) {
        Optional<Postcode> existing = this.postCodeService.findPostCodeByStateAndSuburb(suburb,state);

        if (existing.isPresent()) {
            return ResponseEntity.ok(existing.get().getCode());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping
    public ResponseEntity<Postcode> addPostCode(@RequestBody @Valid AddPostcodeDTO data) {
        String suburb = data.getSuburb();
        String postcode = data.getPostcode();
        String state = this.postCodeService.getStateFromPostCode(postcode);

        // Solution to prevent others from adding suburbs that already exists in the area code, 409 status code
        Optional<Postcode> distinctPostcode = this.postCodeService.findPostCodeByStateAndSuburb(suburb, state);
        if(distinctPostcode.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(null);
        }

        // Does this postcode/suburb currently exists?
        Optional<Postcode> existing = this.postCodeService.findPostCodeByCode(postcode);
        Postcode newPostCode = null;
        if (existing.isEmpty()) {
            newPostCode = this.postCodeService.addNewPostCode(postcode);
        }

        Optional<Suburb> existingSuburb = this.suburbService.findSuburbBySuburb(suburb);
        if(existingSuburb.isEmpty()) {
            this.suburbService.addSuburb(suburb);
        }
            this.postCodeService.assignSuburbToPostCode(postcode, suburb);

        //Automation of adding state to postcode
            this.postCodeService.assignState(postcode);


        return new ResponseEntity<>(newPostCode, HttpStatus.CREATED);
    }

}
