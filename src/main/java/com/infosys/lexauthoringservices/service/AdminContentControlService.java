package com.infosys.lexauthoringservices.service;

import com.infosys.lexauthoringservices.model.neo4j.UpdateContentCreator;

public interface AdminContentControlService {
    void updateContentCreator(String rootOrg, String org, UpdateContentCreator updateContentCreator) throws Exception;
}
