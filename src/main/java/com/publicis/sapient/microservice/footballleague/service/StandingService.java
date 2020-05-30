package com.publicis.sapient.microservice.footballleague.service;


import com.publicis.sapient.microservice.footballleague.model.Standings;

public interface StandingService {
    Standings getStandings(String countryName, String leagueName, String teamName);
}
