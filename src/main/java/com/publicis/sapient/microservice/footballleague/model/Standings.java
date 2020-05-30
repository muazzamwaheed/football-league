package com.publicis.sapient.microservice.footballleague.model;


public class Standings {
    private Integer countryId;
    private String countryName;

    private Integer leagueId;
    private String leagueName;

    private Integer teamId;
    private String teamName;

    private Integer overAllLeaguePosition;

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
