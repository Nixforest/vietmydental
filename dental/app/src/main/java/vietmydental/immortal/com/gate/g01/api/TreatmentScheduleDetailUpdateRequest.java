package vietmydental.immortal.com.gate.g01.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.g01.G01Const;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class TreatmentScheduleDetailUpdateRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Id */
    private final String id;
    /** Teeth id */
    private final String teethId;
    /** Diagnosis id */
    private final String diagnosisId;
    /** Treatment */
    private final String treatment;
    /** Status */
    private final String status;
    /** Status */
    private final ArrayList<ConfigExtBean> teeth_info;

    /**
     * Constructor
     * @param token Token
     * @param id Id
     * @param teethId Id of teeth
     * @param diagnosisId Id of diagnosis
     * @param treatment Id of treatment type
     * @param status Status
     * @param teeth_info
     */
    public TreatmentScheduleDetailUpdateRequest(String token, String id, String teethId,
                                                String diagnosisId, String treatment, String status,
                                                ArrayList<ConfigExtBean> teeth_info) {
        super(G01Const.PATH_TREATMENT_DETAIL_UPDATE);
        this.token = token;
        this.id = id;
        this.teethId = teethId;
        this.diagnosisId = diagnosisId;
        this.treatment = treatment;
        this.status = status;
        this.teeth_info = teeth_info;
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
            object.put(DomainConst.KEY_TEETH_ID, teethId);
            object.put(DomainConst.KEY_DIAGNOSIS_ID, diagnosisId);
            object.put(DomainConst.KEY_TREATMENT_TYPE_ID, treatment);
            object.put(DomainConst.KEY_STATUS, status);
            JSONArray array = new JSONArray();
            for (ConfigExtBean bean :
                    teeth_info) {
                array.put(bean.getId());
            }
            object.put(DomainConst.KEY_TEETH_INFO, array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
