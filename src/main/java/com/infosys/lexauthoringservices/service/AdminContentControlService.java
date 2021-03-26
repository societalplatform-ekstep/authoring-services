package com.infosys.lexauthoringservices.service;

import com.infosys.lexauthoringservices.model.neo4j.UpdateContentCreator;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
@Validated
public interface AdminContentControlService {
    void updateContentCreator(@NotBlank String rootOrg, @NotBlank String org, @Valid UpdateContentCreator updateContentCreator) throws Exception;
}
