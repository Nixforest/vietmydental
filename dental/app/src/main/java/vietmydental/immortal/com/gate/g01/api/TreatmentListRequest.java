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

public class TreatmentListRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Page */
    private final String page;
    /** Id */
    private final String id;

    /**
     * Constructor
     * @param token Token
     * @param page page
     * @param id Id
     */
    public TreatmentListRequest(String token, String page, String id) {
        super(G01Const.PATH_TREATMENT_LIST);
        this.token = token;
        this.page = page;
        this.id = id;
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
            object.put(DomainConst.KEY_PAGE, page);
            object.put(DomainConst.KEY_ID, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
