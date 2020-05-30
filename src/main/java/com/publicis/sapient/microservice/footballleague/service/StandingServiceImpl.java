package com.publicis.sapient.microservice.footballleague.service;

import com.publicis.sapient.microservice.footballleague.domain.Country;
import com.publicis.sapient.microservice.footballleague.domain.League;
import com.publicis.sapient.microservice.footballleague.domain.StandingsDto;
import com.publicis.sapient.microservice.footballleague.exception.DataNotFoundException;
import com.publicis.sapient.microservice.footballleague.model.Standings;
import com.publicis.sapient.microservice.footballleague.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
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
        Standings standings = new Standings();
        if (nonNull(countryName) && nonNull(leagueName) && nonNull(teamName)) {
            StringBuilder getCountries = Utils.formRequestUrl(baseUrl, apiKey, "get_countries");
            Country[] countriesArr = restTemplate.getForEntity(getCountries.toString(), Country[].class).getBody();
            List<Country> countries =  asList(Objects.requireNonNull(countriesArr));
            if (countries.isEmpty()) {
                throw new DataNotFoundException("Failed to get countries");
            }
            countries.forEach(country -> {
                if (countryName.equalsIgnoreCase(country.getCountryName())) {
                    standings.setCountryId(country.getCountryId());
                    standings.setCountryName(country.getCountryName());
                }
            });
            if (isNull(standings.getCountryId())) {
                throw new DataNotFoundException("Data Not found");
            }

            StringBuilder getLeagues = Utils.formRequestUrl(baseUrl, apiKey, "get_leagues");
            getLeagues.append("&country_id=").append(standings.getCountryId());
            League[] leagueObject = restTemplate.getForEntity(getLeagues.toString(), League[].class).getBody();
            List<League> leagues = asList(Objects.requireNonNull(leagueObject));
            if (leagues.isEmpty()) {
                throw new DataNotFoundException("Failed to get leagues");
            }
            leagues.forEach(league -> {
                if (leagueName.equalsIgnoreCase(league.getLeagueName())) {
                    standings.setLeagueId(league.getLeagueId());
                    standings.setLeagueName(league.getLeagueName());
                }
            });

            if (isNull(standings.getLeagueId())) {
                throw new DataNotFoundException("Data Not found");
            }

            StringBuilder getStandings = Utils.formRequestUrl(baseUrl, apiKey, "get_standings");
            getStandings.append("&league_id=").append(standings.getLeagueId());
            StandingsDto[] standingObject = restTemplate.getForEntity(getStandings.toString(), StandingsDto[].class).getBody();
            List<StandingsDto> standingsDtos = asList(Objects.requireNonNull(standingObject));
            if (standingsDtos.isEmpty()) {
                throw new DataNotFoundException("Failed to get standings");
            }
            standingsDtos.forEach(standingsDto -> {
                if (teamName.equalsIgnoreCase(standingsDto.getTeamName())) {
                    standings.setTeamId(standingsDto.getTeamId());
                    standings.setTeamName(standingsDto.getTeamName());
                    standings.setOverAllLeaguePosition(standingsDto.getOverAllLeaguePosition());
                }
            });
            if (isNull(standings.getTeamId())) {
                throw new DataNotFoundException("Data Not found");
            }
        }
    return standings;
    }
}
