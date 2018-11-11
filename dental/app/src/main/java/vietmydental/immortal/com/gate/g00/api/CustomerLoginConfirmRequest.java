package vietmydental.immortal.com.gate.g00.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class CustomerLoginConfirmRequest extends BaseRequest {
    /** Username */
    private final String phone;
    /** Password */
    private final String otp;
    /** GCM device token */
    private final String gcmDeviceToken;
    /** GCM device token */
    private final String apnsDeviceToken;
    /** GCM device token */
    private final String appType;

    /**
     * Constructor
     * @param phone Username
     * @param otp Password
     * @param gcmDeviceToken GCM token
     */
    public CustomerLoginConfirmRequest(String phone, String otp, String gcmDeviceToken) {
        super(DomainConst.PATH_DEFAULT_CUSTOMER_LOGIN_CONFIRM);
        this.phone = phone;
        this.otp = otp;
        this.gcmDeviceToken = gcmDeviceToken;
        this.apnsDeviceToken = DomainConst.BLANK;
        this.appType = DomainConst.BLANK;
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
            object.put(DomainConst.KEY_PHONE, phone);
            object.put(DomainConst.KEY_OTP, otp);
            object.put(DomainConst.KEY_GCM_DEVICE_TOKEN, gcmDeviceToken);
            object.put(DomainConst.KEY_APNS_DEVICE_TOKEN, apnsDeviceToken);
            object.put(DomainConst.KEY_APP_TYPE, appType);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }

}
