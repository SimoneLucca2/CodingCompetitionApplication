package com.polimi.ckb.battleService.dto;

import java.io.Serializable;
import com.polimi.ckb.battleService.entity.StudentGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * DTO for {@link StudentGroup}
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewPushDto implements Serializable {
    @NotNull
    private String repositoryUrl;
    @NotNull
    private String githubToken;
    @NotNull
    private String githubName;
}
