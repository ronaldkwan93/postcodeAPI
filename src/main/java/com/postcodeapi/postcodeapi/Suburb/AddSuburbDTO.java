package com.postcodeapi.postcodeapi.Suburb;

import jakarta.validation.constraints.NotBlank;

public class AddSuburbDTO {

    @NotBlank
    private String suburb;

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
}
