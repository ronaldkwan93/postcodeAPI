package com.postcodeapi.postcodeapi.Postcode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssignSuburbDTO {

    @NotNull
    private Long postCodeId;
    @NotNull
    private Long suburbId;

    public Long getPostCodeId() {
        return postCodeId;
    }

    public void setPostCodeId(Long postCodeId) {
        this.postCodeId = postCodeId;
    }

    public Long getSuburbId() {
        return suburbId;
    }

    public void setSuburbId(Long suburbId) {
        this.suburbId = suburbId;
    }
}
