package com.postcodeapi.postcodeapi.Suburb;

import jakarta.validation.constraints.NotBlank;

public class AddSuburbDTO {

    @NotBlank
    private String suburb;

    @NotBlank
    private String postCode;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
}
