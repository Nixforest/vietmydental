package vietmydental.immortal.com.gate.g00.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class UpdateConfigurationRequest extends BaseRequest {
    /** Token */
    private final String token;
    /**
     * Constructor.
     * @param token
     */
    protected UpdateConfigurationRequest(String token) {
        super(DomainConst.PATH_SITE_UPDATE_CONFIG);
        this.token = token;
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
