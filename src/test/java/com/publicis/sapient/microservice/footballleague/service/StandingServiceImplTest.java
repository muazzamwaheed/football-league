package com.publicis.sapient.microservice.footballleague.service;

import com.publicis.sapient.microservice.footballleague.domain.Country;
import com.publicis.sapient.microservice.footballleague.domain.League;
import com.publicis.sapient.microservice.footballleague.domain.StandingsDto;
import com.publicis.sapient.microservice.footballleague.exception.DataNotFoundException;
import com.publicis.sapient.microservice.footballleague.model.Standings;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StandingServiceImplTest {

    private static final String COUNTRY_NAME = "England";
    private static final Integer COUNTRY_ID= 123;
    private static final String LEAGUE_NAME = "Championship";
    private static final Integer LEAGUE_ID= 444;
    private static final String TEAM_NAME = "liverpool";
    private static final Integer TEAM_ID= 555;

    @InjectMocks
    private StandingServiceImpl standingService;

    @Mock
    private RestTemplate restTemplate;


    @Before
    public void setup(){
        ReflectionTestUtils.setField(standingService,"baseUrl", "/api/football");
        ReflectionTestUtils.setField(standingService,"apiKey", "abvshsgdshdgsjdsjkdjsknasl");
    }
    @Test
    public void getStandings() {

        when(restTemplate.getForEntity("/api/football?action=get_countries&APIkey=abvshsgdshdgsjdsjkdjsknasl", Country[].class)).
                thenReturn(new ResponseEntity(getCountry(), HttpStatus.OK));
        when(restTemplate.getForEntity("/api/football?action=get_leagues&APIkey=abvshsgdshdgsjdsjkdjsknasl&country_id=123", League[].class)).
                thenReturn(new ResponseEntity(getLeagues(), HttpStatus.OK));
        when(restTemplate.getForEntity("/api/football?action=get_standings&APIkey=abvshsgdshdgsjdsjkdjsknasl&league_id=444", StandingsDto[].class)).
                thenReturn(new ResponseEntity(getStandingDto(), HttpStatus.OK));

        Standings standings = standingService.getStandings(COUNTRY_NAME, LEAGUE_NAME, TEAM_NAME);

        assertEquals(COUNTRY_NAME,standings.getCountryName());
        assertEquals(COUNTRY_ID,standings.getCountryId());
        assertEquals(LEAGUE_NAME,standings.getLeagueName());
        assertEquals(LEAGUE_ID,standings.getLeagueId());
        assertEquals(TEAM_NAME,standings.getTeamName());
        assertEquals(TEAM_ID,standings.getTeamId());
        assertEquals(1,standings.getOverAllLeaguePosition());
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowExceptionWhenNoCountryFound() {
        when(restTemplate.getForEntity("/api/football?action=get_countries&APIkey=abvshsgdshdgsjdsjkdjsknasl", Country[].class)).
                thenReturn(new ResponseEntity(new Country[0], HttpStatus.OK));
       standingService.getStandings(COUNTRY_NAME, LEAGUE_NAME, TEAM_NAME);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowExceptionWhenNoMatchingCountryFound() {
        when(restTemplate.getForEntity("/api/football?action=get_countries&APIkey=abvshsgdshdgsjdsjkdjsknasl", Country[].class)).
                thenReturn(new ResponseEntity(getCountry(), HttpStatus.OK));
        standingService.getStandings("invalid country", LEAGUE_NAME, TEAM_NAME);
    }


    @Test(expected = DataNotFoundException.class)
    public void shouldThrowExceptionWhenNoLeagueFound() {
        when(restTemplate.getForEntity("/api/football?action=get_countries&APIkey=abvshsgdshdgsjdsjkdjsknasl", Country[].class)).
                thenReturn(new ResponseEntity(getCountry(), HttpStatus.OK));
        when(restTemplate.getForEntity("/api/football?action=get_leagues&APIkey=abvshsgdshdgsjdsjkdjsknasl&country_id=123", League[].class)).
                thenReturn(new ResponseEntity(new League[0], HttpStatus.OK));

        standingService.getStandings(COUNTRY_NAME, LEAGUE_NAME, TEAM_NAME);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowExceptionWhenNoMatchingLeagueFound() {
        when(restTemplate.getForEntity("/api/football?action=get_countries&APIkey=abvshsgdshdgsjdsjkdjsknasl", Country[].class)).
                thenReturn(new ResponseEntity(getCountry(), HttpStatus.OK));
        when(restTemplate.getForEntity("/api/football?action=get_leagues&APIkey=abvshsgdshdgsjdsjkdjsknasl&country_id=123", League[].class)).
                thenReturn(new ResponseEntity(getLeagues(), HttpStatus.OK));
        standingService.getStandings(COUNTRY_NAME, "invalid League", TEAM_NAME);
    }


    @Test(expected = DataNotFoundException.class)
    public void shouldThrowExceptionWhenNoStandingTeamFound() {
        when(restTemplate.getForEntity("/api/football?action=get_countries&APIkey=abvshsgdshdgsjdsjkdjsknasl", Country[].class)).
                thenReturn(new ResponseEntity(getCountry(), HttpStatus.OK));
        when(restTemplate.getForEntity("/api/football?action=get_leagues&APIkey=abvshsgdshdgsjdsjkdjsknasl&country_id=123", League[].class)).
                thenReturn(new ResponseEntity(getLeagues(), HttpStatus.OK));
        when(restTemplate.getForEntity("/api/football?action=get_standings&APIkey=abvshsgdshdgsjdsjkdjsknasl&league_id=444", StandingsDto[].class)).
                thenReturn(new ResponseEntity(new StandingsDto[0], HttpStatus.OK));

        standingService.getStandings(COUNTRY_NAME, LEAGUE_NAME, TEAM_NAME);
    }

    @Test(expected = DataNotFoundException.class)
    public void shouldThrowExceptionWhenNoMatchingTeamFound() {
        when(restTemplate.getForEntity("/api/football?action=get_countries&APIkey=abvshsgdshdgsjdsjkdjsknasl", Country[].class)).
                thenReturn(new ResponseEntity(getCountry(), HttpStatus.OK));
        when(restTemplate.getForEntity("/api/football?action=get_leagues&APIkey=abvshsgdshdgsjdsjkdjsknasl&country_id=123", League[].class)).
                thenReturn(new ResponseEntity(getLeagues(), HttpStatus.OK));
        when(restTemplate.getForEntity("/api/football?action=get_standings&APIkey=abvshsgdshdgsjdsjkdjsknasl&league_id=444", StandingsDto[].class)).
                thenReturn(new ResponseEntity(getStandingDto(), HttpStatus.OK));

        standingService.getStandings(COUNTRY_NAME, LEAGUE_NAME, "invalid team");
    }
    private StandingsDto[] getStandingDto() {
        StandingsDto[] standingsDtos = new StandingsDto[1];
        StandingsDto standingsDto = new StandingsDto();
        standingsDto.setTeamName(TEAM_NAME);
        standingsDto.setTeamId(TEAM_ID);
        standingsDto.setOverAllLeaguePosition(1);
        standingsDtos[0] = standingsDto;
        return standingsDtos;
    }

    private League[] getLeagues() {
        League[] leagues = new League[1];
        League league = new League();
        league.setLeagueName(LEAGUE_NAME);
        league.setLeagueId(LEAGUE_ID);
        leagues[0] = league;
        return leagues;
    }

    private Country[] getCountry() {
        Country[] countries = new Country[1];
        Country country = new Country();
        country.setCountryName(COUNTRY_NAME);
        country.setCountryId(COUNTRY_ID);
        countries[0] = country;
        return countries;
    }

}