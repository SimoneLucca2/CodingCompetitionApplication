package com.polimi.ckb.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class EmailDto {
    @NotBlank(message = "recipient cannot be blank")
    private String to;
    private String from;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String body;
}
