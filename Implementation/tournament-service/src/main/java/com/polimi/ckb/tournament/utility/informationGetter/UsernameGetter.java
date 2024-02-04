package com.polimi.ckb.tournament.utility.informationGetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.UsernameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class UsernameGetter {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    public String getUsername(Long id) {
        return restTemplate.getForObject(apiGatewayUrl + "/user/name/" + URLEncoder.encode(id.toString(), StandardCharsets.UTF_8), String.class);
    }

}
