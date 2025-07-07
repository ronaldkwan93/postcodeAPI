package com.postcodeapi.postcodeapi.Postcode;

import com.postcodeapi.postcodeapi.Suburb.Suburb;
import com.postcodeapi.postcodeapi.Suburb.SuburbRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostCodeService {

    private final PostCodeRepository postCodeRepository;
    private final SuburbRepository suburbRepository;

    public PostCodeService(PostCodeRepository postCodeRepository, SuburbRepository suburbRepository) {
        this.postCodeRepository = postCodeRepository;
        this.suburbRepository = suburbRepository;
    }

    public Postcode save(String code) {
        Postcode newPostcode = new Postcode();
        newPostcode.setCode(code);
        return this.postCodeRepository.save(newPostcode);
    }

    public List<Postcode> getAll() {
        return this.postCodeRepository.findAll();
    }

    public Postcode assignSuburbToPostCode(Long postId, Long suburbId) {
        Postcode postcode = postCodeRepository.findById(postId).orElseThrow();
        Suburb suburb = suburbRepository.findById(suburbId).orElseThrow();

        postcode.getAssignedSuburbs().add(suburb);
        return postCodeRepository.save(postcode);
    }

}
