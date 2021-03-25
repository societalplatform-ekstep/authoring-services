package com.infosys.lexauthoringservices.service;

import java.util.Map;

public interface AdminContentControlService {
    void updateContentCreator(String rootOrg, String org, String adminWid, Map<String, Object> requestMap) throws Exception;
}
