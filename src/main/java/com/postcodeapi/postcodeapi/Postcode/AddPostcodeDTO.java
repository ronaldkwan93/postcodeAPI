package com.postcodeapi.postcodeapi.Postcode;

import jakarta.validation.constraints.NotBlank;

public class AddPostcodeDTO {

    @NotBlank
    private String postcode;

    @NotBlank
    private String suburb;

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
