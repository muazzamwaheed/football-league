package com.publicis.sapient.microservice.footballleague.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class League implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("league_id")
    private Integer leagueId;

    @JsonProperty("league_name")
    private String leagueName;

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }
}
