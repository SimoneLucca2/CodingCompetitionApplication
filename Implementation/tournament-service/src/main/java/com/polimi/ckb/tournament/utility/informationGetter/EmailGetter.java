package com.polimi.ckb.tournament.utility.informationGetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.UserEmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class EmailGetter {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public String getEmail(Long id) throws JsonProcessingException {
        String USER_SERVICE_URL = "http://USER-SERVICE";
        String user = restTemplate.getForObject(USER_SERVICE_URL + "/user/email/" + id, String.class);
        return objectMapper.readValue(user, UserEmailDto.class).getEmail();
    }
}
