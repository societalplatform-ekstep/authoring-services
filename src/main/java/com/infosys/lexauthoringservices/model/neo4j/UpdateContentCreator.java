package com.infosys.lexauthoringservices.model.neo4j;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContentCreator {

    @NotBlank(message = "UserId may not be blank")
    @JsonProperty("user_id")
    private String userId;

    @NotBlank(message = "targetUser may not be blank")
    @JsonProperty("target_user")
    private String targetUser;
}
