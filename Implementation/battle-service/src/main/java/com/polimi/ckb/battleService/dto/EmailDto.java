package com.polimi.ckb.battleService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDto {
    @NotBlank(message = "recipient cannot be blank")
    private String to;
    private String from;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String body;
}
