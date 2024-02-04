package com.polimi.ckb.tournament.utility.informationGetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.UserEmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class EmailGetter {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    @Value("${api.gateway.url}")
    private String apiGatewayUrl;


    public String getEmail(Long id) {
        // Encode the id to ensure it is correctly represented in the URL
        String encodedId = URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        return restTemplate.getForObject(apiGatewayUrl + "/user/email/" + encodedId, String.class);
    }

}
