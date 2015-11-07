package org.rapidpm.microservice.optionals.metrics.health.rest.api;

import io.undertow.server.session.SessionManagerStatistics;
import org.rapidpm.microservice.optionals.metrics.health.rest.SessionHealth;

import java.util.Set;
import java.util.function.Function;

/**
 * Created by b.bosch on 04.11.2015.
 */
public class SessionHealthInfo {

   public final Set<String> transientSessions;
   public final String deploymentName;
   public final Set<String> allSessions;
   public final Set<String> activeSessions;
   public final long startTime;
   public final long maxActiveSessions;
   public final long activeSessionCount;
   public final long averageSessionAliveTime;
   public final long createdSessionCount;
   public final long expiredSessionCount;
   public final long maxSessionAliveTime;
   public final long rejectedSessions;

    public SessionHealthInfo(SessionManagerStatistics statistics) {
        rejectedSessions = statistics.getRejectedSessions();
        maxSessionAliveTime = statistics.getMaxSessionAliveTime();
        expiredSessionCount = statistics.getExpiredSessionCount();
        createdSessionCount = statistics.getCreatedSessionCount();
        averageSessionAliveTime = statistics.getAverageSessionAliveTime();
        activeSessionCount = statistics.getActiveSessionCount();
        maxActiveSessions = statistics.getMaxActiveSessions();
        startTime = statistics.getStartTime();
        activeSessions = statistics.getActiveSessions();
        allSessions = statistics.getAllSessions();
        deploymentName = statistics.getDeploymentName();
        transientSessions = statistics.getTransientSessions();
    }

    public static SessionHealthInfo fromStatistics(SessionManagerStatistics statistics){
        return new SessionHealthInfo(statistics);
    }



}
