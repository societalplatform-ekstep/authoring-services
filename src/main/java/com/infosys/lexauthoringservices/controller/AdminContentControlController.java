package com.infosys.lexauthoringservices.controller;

import com.infosys.lexauthoringservices.model.neo4j.UpdateContentCreator;
import com.infosys.lexauthoringservices.service.AdminContentControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1/admin/content")
@Validated
public class AdminContentControlController {
    @Autowired
    AdminContentControlService adminContentControlService;

    @PutMapping("/update/creator")
    public ResponseEntity<?> updateContentCreator(@NotBlank @RequestHeader String rootOrg,
                                                  @NotBlank @RequestHeader String org,
                                                  @Valid @RequestBody UpdateContentCreator updateContentCreator) throws Exception {
        adminContentControlService.updateContentCreator(rootOrg, org, updateContentCreator);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
