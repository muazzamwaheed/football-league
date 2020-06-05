package com.publicis.sapient.microservice.footballleague.controller;

import com.publicis.sapient.microservice.footballleague.model.Standings;
import com.publicis.sapient.microservice.footballleague.service.StandingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FootBallLeagueControllerTest {


    private static final String COUNTRY_NAME = "England";
    private static final String LEAGUE_NAME = "Championship";
    private static final String TEAM_NAME = "liverpool";
    private static final String BASE_URL = "/api/football/standings";

    private MockMvc mockMvc;

    @InjectMocks
    private FootBallLeagueController footBallLeagueController;

    @Mock
    private StandingService standingService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(footBallLeagueController).build();
    }

    @Test
    public void shouldGetStandingsOfTeamMock_OK() throws Exception {
        Standings expectedStandingTeam = new Standings();
        expectedStandingTeam.setTeamId(1);
        when(standingService.getStandings(COUNTRY_NAME, LEAGUE_NAME, TEAM_NAME)).thenReturn(expectedStandingTeam);
        mockMvc.perform(get(BASE_URL)
                .queryParam("countryName",COUNTRY_NAME)
                .queryParam("leagueName",LEAGUE_NAME)
                .queryParam("teamName",TEAM_NAME)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetStandingsOfTeamMock_NotFound() throws Exception {
        Standings expectedStandingTeam = new Standings();
        when(standingService.getStandings(COUNTRY_NAME, LEAGUE_NAME, TEAM_NAME)).thenReturn(expectedStandingTeam);
        mockMvc.perform(get(BASE_URL)
                .queryParam("countryName",COUNTRY_NAME)
                .queryParam("leagueName",LEAGUE_NAME)
                .queryParam("teamName",TEAM_NAME)
                .accept(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldGetStandingsOfTeamMock_BadRequest() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}