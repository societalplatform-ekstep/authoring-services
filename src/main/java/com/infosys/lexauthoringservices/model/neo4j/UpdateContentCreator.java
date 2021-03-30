package com.infosys.lexauthoringservices.model.neo4j;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpdateContentCreator {

    @NotBlank(message = "UserId may not be blank")
    private String userId;

    @NotBlank(message = "creatorId may not be blank")
    private String creatorId;

    @NotBlank(message = "targetCreatorId may not be blank")
    private String targetCreatorId;
}
