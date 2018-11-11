package vietmydental.immortal.com.gate.g05.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.g03.G03Const;
import vietmydental.immortal.com.gate.g05.G05Const;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0109-IMT (KhoiVT20181105) [Android] Login and make schedule for customer
public class MakeScheduleRequest extends BaseRequest {
    /** Token */
    private final String name;
    /** Token */
    private final String phone;
    /** Token */
    private final String content;
    /** Month */
    private final String date;
    /** Token */
    private final String token;

    /**
     * Constructor
     * @param name Name
     * @param phone Phone
     * @param date Date
     * @param content Content
     * @param token Token
     */
    public MakeScheduleRequest(String token,String name,String phone,String date,String content) {
        super(G05Const.PATH_MAKE_SCHEDULE);
        this.name      = name;
        this.phone     = phone;
        this.content   = content;
        this.date      = date;
        this.token     = token;

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
            object.put(DomainConst.KEY_NAME, name);
            object.put(DomainConst.KEY_PHONE, phone);
            object.put(DomainConst.KEY_CONTENT, content);
            object.put(DomainConst.KEY_DATE, date);
            object.put(DomainConst.KEY_TOKEN, token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
//-- BUG0109-IMT (KhoiVT20181105) [Android] Login and make schedule for customer