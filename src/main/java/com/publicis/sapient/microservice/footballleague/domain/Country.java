package com.publicis.sapient.microservice.footballleague.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("country_id")
    private Integer countryId;

    @JsonProperty("country_name")
    private String countryName;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
