package com.postcodeapi.postcodeapi.Postcode;

import com.postcodeapi.postcodeapi.Suburb.Suburb;
import com.postcodeapi.postcodeapi.Suburb.SuburbRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostCodeService {

    private final PostCodeRepository postCodeRepository;
    private final SuburbRepository suburbRepository;

    public PostCodeService(PostCodeRepository postCodeRepository, SuburbRepository suburbRepository) {
        this.postCodeRepository = postCodeRepository;
        this.suburbRepository = suburbRepository;
    }



    public Optional<Postcode> findPostCodeByCode(String postcode) {
        return this.postCodeRepository.findByValue(postcode);
    }

    public Postcode addNewPostCode(String code) {
        Postcode newPostcode = new Postcode();
        newPostcode.setCode(code);
        return this.postCodeRepository.save(newPostcode);
    }

    public List<Postcode> getAll() {
        return this.postCodeRepository.findAll();
    }

    public void assignSuburbToPostCode(String code, String sub ) {
        Postcode postcode = postCodeRepository.findByValue(code).orElseThrow();
        Suburb suburb = suburbRepository.findByValue(sub).orElseThrow();

        postcode.getAssignedSuburbs().add(suburb);
        postCodeRepository.save(postcode);
    }

    public void assignState(String code) {
        Postcode postcode = postCodeRepository.findByValue(code).orElseThrow();
        String state = getStateFromPostCode(code);
        postcode.setState(state);
        this.postCodeRepository.save(postcode);
    }

    public String getStateFromPostCode(String code) {
        int numericPostcode = Integer.parseInt(code);
        if (numericPostcode >= 200 && numericPostcode <= 299) {
            return "ACT";
        }

        if (numericPostcode >= 800 && numericPostcode <= 899) {
            return "NT";
        }

        char firstDigit = code.charAt(0);

        return switch (firstDigit) {
            case '1', '2' -> "NSW";
            case '3' -> "VIC";
            case '4' -> "QLD";
            case '5' -> "SA";
            case '6' -> "WA";
            case '7' -> "TAS";
            default -> "Unknown state";
        };
    }

    public Optional<Postcode> findPostCodeByStateAndSuburb(String suburb, String state) {
        return this.postCodeRepository.findByStateAndSuburb(suburb,state);
    }
}
