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

public class CustomerListRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Page */
    private final String page;
    /** Type */
    private final String type;
    /** Date from */
    private final String date_from;
    /** Date to */
    private final String date_to;

    /**
     * Constructor
     * @param token Token
     * @param page page
     * @param type Type
     * @param date_from Date from
     * @param date_to Date to
     */
    public CustomerListRequest(String token, String page, String type, String date_from, String date_to) {
        super(G01Const.PATH_CUSTOMER_LIST);
        this.token = token;
        this.page = page;
        this.type = type;
        this.date_from = date_from;
        this.date_to = date_to;
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
            object.put(DomainConst.KEY_TYPE, type);
//            object.put(DomainConst.KEY_DATE_FROM, date_from);
//            object.put(DomainConst.KEY_DATE_TO, date_to);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
