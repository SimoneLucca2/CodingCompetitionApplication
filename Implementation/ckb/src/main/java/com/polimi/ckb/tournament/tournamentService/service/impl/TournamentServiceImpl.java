package com.polimi.ckb.tournament.tournamentService.service.impl;

import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final RestTemplate restTemplate;

    // URL of the data tier service
    @Value("${tournamentDataTier.service.url}")
    private String dataTierServiceUrl;

    @Autowired
    public TournamentServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Tournament createTournament(CreateTournamentMessage createTournamentMessage) {
        // Call the data tier REST API to create a new tournament
        return restTemplate.postForObject(dataTierServiceUrl + "/tournaments", createTournamentMessage, Tournament.class);
    }
}
