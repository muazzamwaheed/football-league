package com.publicis.sapient.microservice.footballleague.service;

import com.publicis.sapient.microservice.footballleague.domain.Country;
import com.publicis.sapient.microservice.footballleague.domain.League;
import com.publicis.sapient.microservice.footballleague.domain.StandingsDto;
import com.publicis.sapient.microservice.footballleague.model.Standings;
import com.publicis.sapient.microservice.footballleague.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.nonNull;

@Service
@Slf4j
public class StandingServiceImpl implements StandingService{

    @Autowired
    private RestTemplate restTemplate;

    @Value("${sprint.application.baseUrl}")
    private String baseUrl;

    @Value("${sprint.application.apiKey}")
    private String apiKey;

    @Override
    public Standings getStandings(String countryName, String leagueName, String teamName)  {
        return getStandings(teamName, getLeagues(leagueName, getCountries(countryName)));
    }

    private Standings getCountries(String countryName) {
        StringBuilder getCountriesUrl = Utils.formRequestUrl(baseUrl, apiKey, "get_countries");
        Country[] countriesArr = restTemplate.getForEntity(getCountriesUrl.toString(), Country[].class).getBody();
        Standings standings = new Standings();
        if (nonNull(countriesArr)) {
            for (Country country : countriesArr) {
                if (countryName.equalsIgnoreCase(country.getCountryName())) {
                    standings.setCountryId(country.getCountryId());
                    standings.setCountryName(country.getCountryName());
                }
            }
        }
        return standings;
    }

    private Standings getLeagues(String leagueName, Standings standings) {
        if (nonNull(standings.getCountryId())) {
            StringBuilder getLeaguesUrl = Utils.formRequestUrl(baseUrl, apiKey, "get_leagues");
            getLeaguesUrl.append("&country_id=").append(standings.getCountryId());
            League[] leagueObject = restTemplate.getForEntity(getLeaguesUrl.toString(), League[].class).getBody();
            if (nonNull(leagueObject)) {
                for (League league : leagueObject) {
                    if (leagueName.equalsIgnoreCase(league.getLeagueName())) {
                        standings.setLeagueId(league.getLeagueId());
                        standings.setLeagueName(league.getLeagueName());
                    }
                }
            }
        }
        return standings;
    }

    private Standings getStandings(String teamName, Standings standings) {
        if (nonNull(standings.getLeagueId())) {
            StringBuilder getStandingsUrl = Utils.formRequestUrl(baseUrl, apiKey, "get_standings");
            getStandingsUrl.append("&league_id=").append(standings.getLeagueId());
            StandingsDto[] standingObject = restTemplate.getForEntity(getStandingsUrl.toString(), StandingsDto[].class).getBody();
            if (nonNull(standingObject)) {
                for (StandingsDto standing : standingObject) {
                    if (teamName.equalsIgnoreCase(standing.getTeamName())) {
                        standings.setTeamId(standing.getTeamId());
                        standings.setTeamName(standing.getTeamName());
                        standings.setOverAllLeaguePosition(standing.getOverAllLeaguePosition());
                    }
                }
            }
        }
        return standings;
    }
}
