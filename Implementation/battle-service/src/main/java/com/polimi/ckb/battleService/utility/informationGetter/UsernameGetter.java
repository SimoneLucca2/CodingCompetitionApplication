package com.polimi.ckb.battleService.utility.informationGetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.UsernameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class UsernameGetter {

    private final String USER_SERVICE_URL = "http://USER-SERVICE";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public String getUsername(Long id) throws JsonProcessingException {
        String user = restTemplate.getForObject(USER_SERVICE_URL + "/user/name/" + URLEncoder.encode(id.toString(), StandardCharsets.UTF_8), String.class);
        return objectMapper.readValue(user, UsernameDto.class).getUsername();
    }
}
