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

public class TreatmentCreateRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Customer id */
    private final String customerId;
    /** Time */
    private final String time;
    /** Date */
    private final String date;
    /** Doctor id */
    private final String doctorId;
    /** Type */
    private final String type;
    /** Note */
    private final String note;

    /**
     * Constructor
     * @param token Token
     * @param customerId Id of customer
     * @param time Time
     * @param date Date
     * @param doctorId Id of doctor
     * @param type Type
     * @param note Note
     */
    public TreatmentCreateRequest(String token, String customerId, String time, String date, String doctorId, String type, String note) {
        super(G01Const.PATH_TREATMENT_CREATE);
        this.token = token;
        this.customerId = customerId;
        this.time = time;
        this.date = date;
        this.doctorId = doctorId;
        this.type = type;
        this.note = note;
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
            object.put(DomainConst.KEY_CUSTOMER_ID, customerId);
            object.put(DomainConst.KEY_TIME, time);
            object.put(DomainConst.KEY_DATE, date);
            object.put(DomainConst.KEY_DOCTOR_ID, doctorId);
            object.put(DomainConst.KEY_TYPE, type);
            object.put(DomainConst.KEY_NOTE, note);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
