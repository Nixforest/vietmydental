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

public class MedicalRecordUpdateRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Id */
    private final String id;
    /** Record number */
    private final String recordNumber;
    /** Medical history */
    private final String medicalHistory;

    /**
     * Constructor
     * @param token Token
     * @param id Id
     * @param recordNumber Record number
     * @param medicalHistory Medical history
     */
    public MedicalRecordUpdateRequest(String token, String id, String recordNumber, String medicalHistory) {
        super(G01Const.PATH_MEDICAL_RECORD_UPDATE);
        this.token = token;
        this.id = id;
        this.recordNumber = recordNumber;
        this.medicalHistory = medicalHistory;
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
            object.put(DomainConst.KEY_RECORD_NUMBER, recordNumber);
            object.put(DomainConst.KEY_MEDICAL_HISTORY, medicalHistory);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
