package vietmydental.immortal.com.gate.g01.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.g01.G01Const;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class TreatmentUpdateRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Id */
    private final String id;
    /** Diagnosis id */
    private final String diagnosisId;
    /** Pathological id */
    private final String pathologicalId;
    /** Healthy */
    private final String healthy;
    /** Status */
    private final String status;

    /**
     * Constructor
     * @param token Token
     * @param id Id
     * @param diagnosisId
     * @param pathologicalId
     * @param healthy
     * @param status
     */
    public TreatmentUpdateRequest(String token, String id, String diagnosisId, String pathologicalId, String healthy, String status) {
        super(G01Const.PATH_TREATMENT_UPDATE);
        this.token = token;
        this.id = id;
        this.diagnosisId = diagnosisId;
        this.pathologicalId = pathologicalId;
        this.healthy = healthy;
        this.status = status;
    }

    /**
     * Get body for request.
     *
     * @return Json string of body
     */
    @Override
    protected List<NameValuePair> buildParameter() {
        ArrayList<NameValuePair> ret = new ArrayList<>();
        JSONObject object = this.generateDefaultJSONObject();
        try {
            object.put(DomainConst.KEY_TOKEN, token);
            object.put(DomainConst.KEY_ID, id);
            object.put(DomainConst.KEY_DIAGNOSIS_ID, diagnosisId);
            object.put(DomainConst.KEY_PATHOLOGICAL_ID, pathologicalId);
            object.put(DomainConst.KEY_HEALTHY, healthy);
            object.put(DomainConst.KEY_STATUS, status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
