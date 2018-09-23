package vietmydental.immortal.com.gate.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.utils.DomainConst;

public class StreetListRequest extends BaseRequest {
    /** City id */
    private final String id;
    /** Page */
    private final String page;
    /** Token */
    //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    private final String token;
    //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    /**
     * Constructor
     * @param id Id of city
     * @param page Page of list
     * @param token Token
     */
    //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    public StreetListRequest(String id, String page,String token) {
        super(DomainConst.PATH_LIST_STREETS);
        this.id = id;
        this.page = page;
        this.token = token;
    }
    //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh

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
            //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
            object.put(DomainConst.KEY_TOKEN, token);
            //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
            object.put(DomainConst.KEY_ID, id);
            object.put(DomainConst.KEY_PAGE, page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
