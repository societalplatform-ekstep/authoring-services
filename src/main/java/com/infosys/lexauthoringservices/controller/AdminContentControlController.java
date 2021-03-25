package com.infosys.lexauthoringservices.controller;

import com.infosys.lexauthoringservices.service.AdminContentControlService;
import com.infosys.lexauthoringservices.util.LexConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AdminContentControlController {
    @Autowired
    AdminContentControlService adminContentControlService;

    @PutMapping("/update/creator")
    public ResponseEntity<?> updateContentCreator(@RequestParam(value = LexConstants.ROOT_ORG, defaultValue = "Infosys") String adminWid,
                                                  @RequestParam(value = LexConstants.ROOT_ORG, defaultValue = "Infosys") String rootOrg,
                                                  @RequestParam(value = LexConstants.ORG, defaultValue = "Infosys Ltd") String org,
                                                  @RequestBody Map<String, Object> requestMap) throws Exception {
        adminContentControlService.updateContentCreator(rootOrg, org, adminWid, requestMap);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
