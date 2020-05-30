package com.publicis.sapient.microservice.footballleague.controller;

import com.publicis.sapient.microservice.footballleague.exception.DataNotFoundException;
import com.publicis.sapient.microservice.footballleague.model.Standings;
import com.publicis.sapient.microservice.footballleague.service.StandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "/api/football")
public class FootBallLeagueController {

    @Autowired
    private StandingService standingService;

    @GetMapping("/standings")
    public Standings get(@RequestParam String countryName,
                         @RequestParam String leagueName,
                         @RequestParam String teamName) {
            return standingService.getStandings(countryName, leagueName, teamName);
    }

}
