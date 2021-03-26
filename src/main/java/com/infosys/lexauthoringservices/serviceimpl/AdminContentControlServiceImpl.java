package com.infosys.lexauthoringservices.serviceimpl;

import com.infosys.lexauthoringservices.exception.BadRequestException;
import com.infosys.lexauthoringservices.model.UpdateMetaRequest;
import com.infosys.lexauthoringservices.model.neo4j.ContentNode;
import com.infosys.lexauthoringservices.model.neo4j.UpdateContentCreator;
import com.infosys.lexauthoringservices.service.AdminContentControlService;
import com.infosys.lexauthoringservices.service.GraphService;
import com.infosys.lexauthoringservices.service.UserAutomationService;
import com.infosys.lexauthoringservices.util.LexConstants;
import com.infosys.lexauthoringservices.util.LexLogger;
import com.infosys.lexauthoringservices.util.PIDConstants;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminContentControlServiceImpl implements AdminContentControlService {

    @Value("${check.org.admin.url}")
    private String checkOrgAdminUrl;

    @Autowired
    Driver neo4jDriver;
    @Autowired
    GraphService graphService;
    @Autowired
    private LexLogger logger;
    @Autowired
    private ContentCrudServiceImpl contentCrudServiceImpl;
    @Autowired
    UserAutomationService userAutomationService;

    public static SimpleDateFormat inputFormatterDate = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat inputFormatterDateTime = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");

    @Override
    public void updateContentCreator(String rootOrg, String org, UpdateContentCreator updateContentCreator)
            throws Exception {
        Session session = neo4jDriver.session();
        Transaction transaction = session.beginTransaction();

        String userId = updateContentCreator.getUserId();
        String targetUser = updateContentCreator.getTargetUser();

        Map<String, Boolean> result = userAutomationService.checkOrgAdmin(rootOrg, targetUser);
            if(!result.get(LexConstants.IS_ADMIN)){
                throw new BadRequestException(targetUser + " must be an admin");
            } else {
                logger.info("Starting PID Call");
                Map<String, Object> userData = contentCrudServiceImpl.getUserDataFromUserId(rootOrg, targetUser,
                        Arrays.asList(PIDConstants.UUID, PIDConstants.FIRST_NAME, PIDConstants.LAST_NAME));
                logger.info("PID Call is complete");

                if (userData == null || userData.isEmpty()) {
                    throw new BadRequestException("No user with id : " + targetUser);
                }

                Map<?, ?> userDetails = (Map<?, ?>) userData.get(targetUser);

                Map<String, Object> userRequiredDetails = new HashMap<>();
                userRequiredDetails.put(LexConstants.NAME, userDetails.get(PIDConstants.FIRST_NAME) + " "
                        + userDetails.get(PIDConstants.LAST_NAME));
                Object identifier = userDetails.get(PIDConstants.UUID);
                if (identifier == null) {
                    throw new BadRequestException("No user with id : " + targetUser);
                }
                userRequiredDetails.put(LexConstants.ID, userDetails.get(PIDConstants.UUID));

                List<ContentNode> contents;
                List<UpdateMetaRequest> metaContents = new ArrayList<>();

                try {
                    contents = graphService.getContentCreatorNode(rootOrg, userId, transaction);
                    for (ContentNode content : contents) {
                        content.getMetadata().put(LexConstants.CREATOR, targetUser);
                        content.getMetadata().put(LexConstants.CREATOR_CONTACTS, userRequiredDetails);
                        content.getMetadata().put(LexConstants.LAST_UPDATED, inputFormatterDateTime.format(Calendar.getInstance().getTime()));

                        UpdateMetaRequest metaRequest = new UpdateMetaRequest(content.getIdentifier(), content.getMetadata());
                        metaContents.add(metaRequest);
                    }
                    graphService.updateNodesV2(rootOrg, metaContents, transaction);
                    transaction.commitAsync().toCompletableFuture().get();
                } catch (Exception e) {
                    e.printStackTrace();
                    transaction.rollbackAsync().toCompletableFuture().get();
                    throw e;
                } finally {
                    transaction.close();
                    session.close();
                }
            }
    }
}
