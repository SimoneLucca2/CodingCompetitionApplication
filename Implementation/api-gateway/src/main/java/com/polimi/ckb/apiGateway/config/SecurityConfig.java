package com.polimi.ckb.apiGateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {
            "/login",
            "/signup",
            "/**"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Define RequestMatchers for specific roles and endpoints
        RequestMatcher educatorEndpoints = new OrRequestMatcher(
                new AntPathRequestMatcher("/tournament/", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/tournament/educator", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/tournament/status", HttpMethod.PUT.name()),
                new AntPathRequestMatcher("/battle", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/battle/score", HttpMethod.PUT.name())
        );

        RequestMatcher studentEndpoints = new OrRequestMatcher(
                new AntPathRequestMatcher("/tournament/student", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/tournament/student", HttpMethod.DELETE.name()),
                new AntPathRequestMatcher("/battle/student", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/battle/student", HttpMethod.DELETE.name()),
                new AntPathRequestMatcher("/battle/group", HttpMethod.POST.name()),
                new AntPathRequestMatcher("/battle/group", HttpMethod.PUT.name()),
                new AntPathRequestMatcher("/battle/group", HttpMethod.DELETE.name())
        );

        RequestMatcher allUserEndpoints = new OrRequestMatcher(
                new AntPathRequestMatcher("/tournament/ranking", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/tournament/", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/battle/score", HttpMethod.GET.name()),
                new AntPathRequestMatcher("/user", HttpMethod.GET.name())
        );

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // White list URLs
                        .requestMatchers(WHITE_LIST_URL).permitAll()
                        // Educator endpoints
                        .requestMatchers(educatorEndpoints).hasAuthority("EDUCATOR")
                        // Student endpoints
                        .requestMatchers(studentEndpoints).hasAuthority("STUDENT")
                        // Endpoints open for both Educator and Student
                        .requestMatchers(allUserEndpoints).hasAnyAuthority("EDUCATOR", "STUDENT")
                        // Any other requests must be authenticated
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
