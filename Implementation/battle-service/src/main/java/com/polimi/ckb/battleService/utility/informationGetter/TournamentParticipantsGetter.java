package com.polimi.ckb.battleService.utility.informationGetter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentParticipantsGetter {
    private final RestTemplate restTemplate;
    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    public List<Long> getParticipants(Long id) {
        String encodedId = URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        String url = apiGatewayUrl + "/tournament/students/" + encodedId;

        ParameterizedTypeReference<List<Long>> typeRef = new ParameterizedTypeReference<List<Long>>() {};
        ResponseEntity<List<Long>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, typeRef);

        return responseEntity.getBody();
    }
}
