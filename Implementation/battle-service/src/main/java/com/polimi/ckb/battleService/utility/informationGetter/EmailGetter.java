package com.polimi.ckb.battleService.utility.informationGetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.UserEmailDto;
import com.polimi.ckb.battleService.dto.UsernameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class EmailGetter {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public String getEmail(Long id) throws JsonProcessingException, UnsupportedEncodingException {
        String USER_SERVICE_URL = "http://USER-SERVICE";
        // Encode the id to ensure it is correctly represented in the URL
        String encodedId = URLEncoder.encode(String.valueOf(id), StandardCharsets.UTF_8);
        String user = restTemplate.getForObject(USER_SERVICE_URL + "/user/email/" + encodedId, String.class);
        return objectMapper.readValue(user, UserEmailDto.class).getEmail();
    }
}
