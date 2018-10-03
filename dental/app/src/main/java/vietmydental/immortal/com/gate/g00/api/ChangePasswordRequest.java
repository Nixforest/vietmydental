package vietmydental.immortal.com.gate.g00.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class ChangePasswordRequest extends BaseRequest {
    /** Old password */
    private final String old_password;
    /** New password */
    private final String new_password;
    /** Token */
    private final String token;

    /**
     * Constructor
     * @param old_password Old password
     * @param new_password New password
     * @param token
     */
    public ChangePasswordRequest(String old_password, String new_password,
                                String token) {
        super(DomainConst.PATH_USER_CHANGE_PASS);
        this.old_password = old_password;
        this.new_password = new_password;
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
            object.put(DomainConst.KEY_OLD_PASSWORD, old_password);
            object.put(DomainConst.KEY_NEW_PASSWORD, new_password);
            object.put(DomainConst.KEY_TOKEN, token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
