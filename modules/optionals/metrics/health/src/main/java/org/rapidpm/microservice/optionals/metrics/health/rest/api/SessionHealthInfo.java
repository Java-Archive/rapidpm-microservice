package org.rapidpm.microservice.optionals.metrics.health.rest.api;

import io.undertow.server.session.SessionManager;
import io.undertow.server.session.SessionManagerStatistics;

import java.util.Objects;
import java.util.Set;

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


  public <T extends SessionManager & SessionManagerStatistics> SessionHealthInfo(T sessionManager) {
    rejectedSessions = sessionManager.getRejectedSessions();
    maxSessionAliveTime = sessionManager.getMaxSessionAliveTime();
    expiredSessionCount = sessionManager.getExpiredSessionCount();
    createdSessionCount = sessionManager.getCreatedSessionCount();
    averageSessionAliveTime = sessionManager.getAverageSessionAliveTime();
    activeSessionCount = sessionManager.getActiveSessionCount();
    maxActiveSessions = sessionManager.getMaxActiveSessions();
    startTime = sessionManager.getStartTime();
    activeSessions = sessionManager.getActiveSessions();
    allSessions = sessionManager.getAllSessions();
    deploymentName = sessionManager.getDeploymentName();
    transientSessions = sessionManager.getTransientSessions();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof SessionHealthInfo)) return false;
    final SessionHealthInfo that = (SessionHealthInfo) o;
    return startTime == that.startTime &&
        maxActiveSessions == that.maxActiveSessions &&
        activeSessionCount == that.activeSessionCount &&
        averageSessionAliveTime == that.averageSessionAliveTime &&
        createdSessionCount == that.createdSessionCount &&
        expiredSessionCount == that.expiredSessionCount &&
        maxSessionAliveTime == that.maxSessionAliveTime &&
        rejectedSessions == that.rejectedSessions &&
        Objects.equals(transientSessions, that.transientSessions) &&
        Objects.equals(deploymentName, that.deploymentName) &&
        Objects.equals(allSessions, that.allSessions) &&
        Objects.equals(activeSessions, that.activeSessions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(transientSessions, deploymentName, allSessions, activeSessions, startTime, maxActiveSessions, activeSessionCount, averageSessionAliveTime, createdSessionCount, expiredSessionCount, maxSessionAliveTime, rejectedSessions);
  }

  @Override
  public String toString() {
    return "SessionHealthInfo{" +
        "activeSessionCount=" + activeSessionCount +
        ", activeSessions=" + activeSessions +
        ", allSessions=" + allSessions +
        ", averageSessionAliveTime=" + averageSessionAliveTime +
        ", createdSessionCount=" + createdSessionCount +
        ", deploymentName='" + deploymentName + '\'' +
        ", expiredSessionCount=" + expiredSessionCount +
        ", maxActiveSessions=" + maxActiveSessions +
        ", maxSessionAliveTime=" + maxSessionAliveTime +
        ", rejectedSessions=" + rejectedSessions +
        ", startTime=" + startTime +
        ", transientSessions=" + transientSessions +
        '}';
  }
}
