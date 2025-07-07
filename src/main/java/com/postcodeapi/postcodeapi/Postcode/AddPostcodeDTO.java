package com.postcodeapi.postcodeapi.Postcode;

import jakarta.validation.constraints.NotBlank;

public class AddPostcodeDTO {

    @NotBlank
    private String postcode;

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
