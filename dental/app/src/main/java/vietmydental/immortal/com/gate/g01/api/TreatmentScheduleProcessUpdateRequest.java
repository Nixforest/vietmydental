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

public class TreatmentScheduleProcessUpdateRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Id */
    private final String id;
    /** Teeth id */
    private final String teethId;
    /** Name */
    private final String name;
    /** Content */
    private final String content;
    /** Note */
    private final String note;
    /** Status */
    private final String status;

    /**
     * Constructor
     * @param token Token
     * @param id Id
     * @param teethId Id of teeth
     * @param name Name
     * @param content Content
     * @param note Note
     * @param status Status
     */
    public TreatmentScheduleProcessUpdateRequest(String token, String id, String teethId, String name, String content, String note, String status) {
        super(G01Const.PATH_TREATMENT_PROCESS_UPDATE);
        this.token = token;
        this.id = id;
        this.teethId = teethId;
        this.name = name;
        this.content = content;
        this.note = note;
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
            object.put(DomainConst.KEY_TEETH_ID, teethId);
            object.put(DomainConst.KEY_NAME, name);
            object.put(DomainConst.KEY_CONTENT, content);
            object.put(DomainConst.KEY_NOTE, note);
            object.put(DomainConst.KEY_STATUS, status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
