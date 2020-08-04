package allthings.iot.dos.dto.oauth2;

import allthings.iot.dos.dto.AbstractIotDosDTO;

import java.util.Calendar;
import java.util.Date;

/**
 * @description:
 * @author: fengchangxin
 * @create: 2018-11-14 18:49
 */

public class ApprovalsDTO extends AbstractIotDosDTO {
    private String userId;

    private String clientId;

    private String scope;
    private ApprovalStatus status;
    private Date expiresAt;
    private Date lastUpdatedAt;

    public ApprovalsDTO(String userId, String clientId, String scope, int expiresIn, ApprovalStatus status) {
        this(userId, clientId, scope, new Date(), status, new Date());
        Calendar expiresAt = Calendar.getInstance();
        expiresAt.add(Calendar.MILLISECOND, expiresIn);
        setExpiresAt(expiresAt.getTime());
    }

    public ApprovalsDTO(String userId, String clientId, String scope, Date expiresAt, ApprovalStatus status) {
        this(userId, clientId, scope, expiresAt, status, new Date());
    }

    public ApprovalsDTO(String userId, String clientId, String scope, Date expiresAt, ApprovalStatus status,
                        Date lastUpdatedAt) {
        this.userId = userId;
        this.clientId = clientId;
        this.scope = scope;
        this.expiresAt = expiresAt;
        this.status = status;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    protected ApprovalsDTO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? "" : userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? "" : clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope == null ? "" : scope;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        if (expiresAt == null) {
            Calendar thirtyMinFromNow = Calendar.getInstance();
            thirtyMinFromNow.add(Calendar.MINUTE, 30);
            expiresAt = thirtyMinFromNow.getTime();
        }
        this.expiresAt = expiresAt;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public boolean isCurrentlyActive() {
        return expiresAt != null && expiresAt.after(new Date());
    }

    public boolean isApproved() {
        return isCurrentlyActive() && status == ApprovalStatus.APPROVED;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + userId.hashCode();
        result = prime * result + clientId.hashCode();
        result = prime * result + scope.hashCode();
        result = prime * result + status.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof ApprovalsDTO)) {
            return false;
        }
        ApprovalsDTO other = (ApprovalsDTO) o;
        return userId.equals(other.userId) && clientId.equals(other.clientId) && scope.equals(other.scope) &&
                status == other.status;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %s, %s, %s]", userId, scope, clientId, expiresAt, status.toString(),
                lastUpdatedAt);
    }

    public enum ApprovalStatus {
        APPROVED,
        DENIED;
    }
}
