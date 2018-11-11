package vietmydental.immortal.com.gate.g00.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class CustomerLoginRequest extends BaseRequest {
    /** Phone */
    private final String phone;

    /**
     * Constructor
     * @param phone Phone
     */
    public CustomerLoginRequest(String phone) {
        super(DomainConst.PATH_DEFAULT_CUSTOMER_LOGIN);
        this.phone = phone;
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
    public String getPhone() {
        return phone;
    }
}
