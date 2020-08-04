package allthings.iot.dos.open.security.oauth2;

import allthings.iot.dos.client.open.IotOauth2Api;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;

import java.util.Collection;

public class RpcApprovalStore implements ApprovalStore {
    private IotOauth2Api iotOauth2Api;

    public RpcApprovalStore(IotOauth2Api iotOauth2Api) {
        this.iotOauth2Api = iotOauth2Api;
    }

    @Override
    public boolean addApprovals(Collection<Approval> approvals) {
        return false;
    }

    @Override
    public boolean revokeApprovals(Collection<Approval> approvals) {
        return false;
    }

    @Override
    public Collection<Approval> getApprovals(String userId, String clientId) {
        return null;
    }
}
