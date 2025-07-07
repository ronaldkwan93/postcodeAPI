package com.postcodeapi.postcodeapi.Postcode;

import com.postcodeapi.postcodeapi.Suburb.Suburb;
import com.postcodeapi.postcodeapi.Suburb.SuburbService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postcode")
public class PostCodeController {

    private final PostCodeService postCodeService;

    public PostCodeController(PostCodeService postCodeService, SuburbService suburbService) {
        this.postCodeService = postCodeService;
    }

    @GetMapping
    public ResponseEntity<List<Postcode>> getPostCodes() {

        List<Postcode> postcodes = postCodeService.getAll();

        return new ResponseEntity<>(postcodes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Postcode> addPostCode(@RequestBody @Valid AddPostcodeDTO data) {
        // get code from requestbody
        // use service to save

        Postcode createdPost = postCodeService.save(data.getPostcode());
        // return created status

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping
    public Postcode assignSuburbToPostCode(@RequestBody @Valid AssignSuburbDTO data ) {
        Long postId = data.getPostCodeId();
        Long suburbId = data.getSuburbId();

        return postCodeService.assignSuburbToPostCode(postId, suburbId);
    }

    // input, would be body of postcode and suburb

    // adding suburb
    // check if postcode is existing
    // if existing, add suburb to postcode

    //get suburbs
    // get all suburbs that have the postcode of input
}
