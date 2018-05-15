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

public class TreatmentScheduleDetailCreateRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Schedule id */
    private final String scheduleId;
    /** Time */
    private final String time;
    /** Date */
    private final String date;
    /** Teeth id */
    private final String teethId;
    /** Diagnosis id */
    private final String diagnosisId;
    /** Treatment */
    private final String treatment;
    /** Status */
    private final ArrayList<ConfigExtBean> teeth_info;

    /**
     * Constructor
     * @param token Token
     * @param scheduleId
     * @param time
     * @param date
     * @param teethId Id of teeth
     * @param diagnosisId Id of diagnosis
     * @param treatment Id of treatment type
     * @param teeth_info
     */
    public TreatmentScheduleDetailCreateRequest(String token, String scheduleId, String time, String date, String teethId, String diagnosisId, String treatment, ArrayList<ConfigExtBean> teeth_info) {
        super(G01Const.PATH_TREATMENT_DETAIL_CREATE);
        this.token = token;
        this.scheduleId = scheduleId;
        this.time = time;
        this.date = date;
        this.teethId = teethId;
        this.diagnosisId = diagnosisId;
        this.treatment = treatment;
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
            object.put(DomainConst.KEY_SCHEDULE_ID, scheduleId);
            object.put(DomainConst.KEY_TIME, time);
            object.put(DomainConst.KEY_DATE, date);
            object.put(DomainConst.KEY_TEETH_ID, teethId);
            object.put(DomainConst.KEY_DIAGNOSIS_ID, diagnosisId);
            object.put(DomainConst.KEY_TREATMENT_TYPE_ID, treatment);
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
