package vietmydental.immortal.com.vietmydental.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.utils.DomainConst;

/**
 * Created by nguyenpt on 4/16/18.
 */

public class LoginRequest extends BaseRequest {
    /** Username */
    private final String username;
    /** Password */
    private final String password;
    /** GCM device token */
    private final String gcmDeviceToken;
    /** APNS device token */
    private final String apnsDeviceToken;
    private String token;

    /**
     * Constructor
     * @param username
     * @param password
     * @param token
     * @param gcmDeviceToken
     */
    protected LoginRequest(String username, String password, String token, String gcmDeviceToken) {
        super(DomainConst.PATH_SITE_LOGIN);
        this.token = token;
        setRequestType(DomainConst.REQUEST_POST);

        // For custom params
        this.username = username;
        this.password = password;
        this.gcmDeviceToken = gcmDeviceToken;
        this.apnsDeviceToken = DomainConst.BLANK;
    }

    @Override
    protected List<NameValuePair> buildParameter() {
        ArrayList<NameValuePair> ret = new ArrayList<>();
        JSONObject object = new JSONObject();
        try {
            object.put(DomainConst.KEY_USERNAME, username);
            object.put(DomainConst.KEY_PASSWORD, password);
            object.put(DomainConst.KEY_GCM_DEVICE_TOKEN, gcmDeviceToken);
            object.put(DomainConst.KEY_APNS_DEVICE_TOKEN, apnsDeviceToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
