package com.infosys.lexauthoringservices.controller;

import com.infosys.lexauthoringservices.model.neo4j.UpdateContentCreator;
import com.infosys.lexauthoringservices.service.AdminContentControlService;
import com.infosys.lexauthoringservices.util.LexConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1/admin/content")
public class AdminContentControlController {
    @Autowired
    AdminContentControlService adminContentControlService;

    @PutMapping("/update/creator")
    public ResponseEntity<?> updateContentCreator(@RequestParam(value = LexConstants.ROOT_ORG, defaultValue = "Infosys") String rootOrg,
                                                  @RequestParam(value = LexConstants.ORG, defaultValue = "Infosys Ltd") String org,
                                                  @RequestBody UpdateContentCreator updateContentCreator) throws Exception {
        adminContentControlService.updateContentCreator(rootOrg, org, updateContentCreator);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
