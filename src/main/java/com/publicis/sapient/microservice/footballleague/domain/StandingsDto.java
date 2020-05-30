package com.publicis.sapient.microservice.footballleague.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class StandingsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("team_id")
    private Integer teamId;

    @JsonProperty("team_name")
    private String teamName;

    @JsonProperty("overall_league_position")
    private Integer overAllLeaguePosition;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getOverAllLeaguePosition() {
        return overAllLeaguePosition;
    }

    public void setOverAllLeaguePosition(Integer overAllLeaguePosition) {
        this.overAllLeaguePosition = overAllLeaguePosition;
    }
}
