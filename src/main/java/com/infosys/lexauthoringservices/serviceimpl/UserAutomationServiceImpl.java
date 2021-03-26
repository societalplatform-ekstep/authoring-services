package com.infosys.lexauthoringservices.serviceimpl;

import com.infosys.lexauthoringservices.service.UserAutomationService;
import com.infosys.lexauthoringservices.util.LexLogger;
import com.infosys.lexauthoringservices.util.LexServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

@Service
public class UserAutomationServiceImpl implements UserAutomationService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LexServerProperties lexServerProps;
    @Autowired
    private LexLogger logger;

    @Override
    public Map<String, Boolean> checkOrgAdmin(String rootOrg, String wid) throws Exception {
        String baseUrl = lexServerProps.getUserAutomationServiceScheme() + lexServerProps.getUserAutomationServiceIp() + ":" + lexServerProps.getUserAutomationServicePort() + lexServerProps.getUserAutomationServiceEndpoint();
        URI uri = new URI(baseUrl);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("rootorg", rootOrg);
            headers.add("wid", wid);
            HttpEntity<Map<String, Boolean>> requestEntity = new HttpEntity<>(null, headers);

            return Objects.requireNonNull(restTemplate.exchange(uri, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<Map<String, Boolean>>() {
            }).getBody());
        } catch (ResourceAccessException rse) {
            logger.info("Exception occured while calling user-automation");
            throw new ResourceAccessException(rse.getLocalizedMessage());
        } catch (Exception e) {
            logger.info("Exception occured while calling user-automation");
            logger.info(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

}
