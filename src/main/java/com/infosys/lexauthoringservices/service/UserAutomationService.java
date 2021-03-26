package com.infosys.lexauthoringservices.service;

import java.util.Map;

public interface UserAutomationService {
    Map<String, Boolean> checkOrgAdmin(String rootOrg, String wid) throws Exception;
}
