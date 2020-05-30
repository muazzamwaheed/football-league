package com.publicis.sapient.microservice.footballleague.controller;

import com.publicis.sapient.microservice.footballleague.model.Standings;
import com.publicis.sapient.microservice.footballleague.service.StandingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FootBallLeagueControllerTest {

    private static final String COUNTRY_NAME = "England";
    private static final String LEAGUE_NAME = "Championship";
    private static final String TEAM_NAME = "liverpool";
    private static final String BASE_URL = "api/football/standings";

    @InjectMocks
    private FootBallLeagueController footBallLeagueController;

    @Mock
    private StandingService standingService;

    @Test
    public void shouldGetStandingsOfTeam() {
        Standings expectedStandingTeam = new Standings();
        when(standingService.getStandings(COUNTRY_NAME, LEAGUE_NAME, TEAM_NAME)).thenReturn(expectedStandingTeam);

        Standings actualStandingTeam = footBallLeagueController.get(COUNTRY_NAME, LEAGUE_NAME, TEAM_NAME);

        assertEquals(expectedStandingTeam, actualStandingTeam);
    }


}