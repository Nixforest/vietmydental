package vietmydental.immortal.com.gate.g00.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class LoginRequest extends BaseRequest {
    /** Username */
    private final String username;
    /** Password */
    private final String password;
    /** GCM device token */
    private final String gcmDeviceToken;
    /** Token */
    private final String token;

    /**
     * Constructor
     * @param username Username
     * @param password Password
     * @param gcmDeviceToken GCM token
     * @param token Token
     */
    public LoginRequest(String username, String password, String gcmDeviceToken, String token) {
        super(DomainConst.PATH_SITE_LOGIN);
        this.username = username;
        this.password = password;
        this.gcmDeviceToken = gcmDeviceToken;
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
            object.put(DomainConst.KEY_USERNAME, username);
            object.put(DomainConst.KEY_PASSWORD, password);
            object.put(DomainConst.KEY_GCM_DEVICE_TOKEN, gcmDeviceToken);
            object.put(DomainConst.KEY_APNS_DEVICE_TOKEN, DomainConst.BLANK);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }

    /**
     * Get user name value
     * @return Username
     */
    public String getUsername() {
        return username;
    }
}
