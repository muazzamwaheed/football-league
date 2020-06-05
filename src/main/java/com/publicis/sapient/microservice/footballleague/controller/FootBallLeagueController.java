package com.publicis.sapient.microservice.footballleague.controller;

import com.publicis.sapient.microservice.footballleague.model.Standings;
import com.publicis.sapient.microservice.footballleague.service.StandingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.nonNull;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/api/football")
public class FootBallLeagueController {

    @Autowired
    private StandingService standingService;

    @GetMapping("/standings")
    public ResponseEntity<Standings> get(@RequestParam String countryName, @RequestParam String leagueName, @RequestParam String teamName) {
        if (nonNull(countryName) && nonNull(leagueName) && nonNull(teamName)) {
            final Standings standings = standingService.getStandings(countryName, leagueName, teamName);
            if (nonNull(standings.getTeamId())) return ok(standings);
            return ResponseEntity.notFound().build();
        }
        return badRequest().build();
    }

}
