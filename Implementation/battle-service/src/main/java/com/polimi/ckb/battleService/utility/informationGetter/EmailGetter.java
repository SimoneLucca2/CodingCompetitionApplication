package com.polimi.ckb.battleService.utility.informationGetter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class EmailGetter {

    private final RestTemplate restTemplate;
    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    public String getEmail(Long id) {
        // Encode the id to ensure it is correctly represented in the URL
        String encodedId = URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        return restTemplate.getForObject(apiGatewayUrl + "/user/email/" + encodedId, String.class);
    }
}
