<<<<<<<< HEAD:Implementation/user-service/src/main/java/com/polimi/ckb/user/config/RestTemplateConfig.java
package com.polimi.ckb.user.config;
========
package com.polimi.ckb.tournament.config;
>>>>>>>> origin/tournament_testing:Implementation/ckb/src/main/java/com/polimi/ckb/tournament/config/RestTemplateConfig.java

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}