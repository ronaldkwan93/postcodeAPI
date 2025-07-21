package com.postcodeapi.postcodeapi.Suburb;

import jakarta.validation.constraints.NotNull;

public class AssignPostCodeDTO {

    @NotNull
    private Long suburbId;

    @NotNull
    private Long postCodeId;

    public Long getSuburbId() {
        return suburbId;
    }

    public void setSuburbId(Long suburbId) {
        this.suburbId = suburbId;
    }

    public Long getPostCodeId() {
        return postCodeId;
    }

    public void setPostCodeId(Long postCodeId) {
        this.postCodeId = postCodeId;
    }
}
