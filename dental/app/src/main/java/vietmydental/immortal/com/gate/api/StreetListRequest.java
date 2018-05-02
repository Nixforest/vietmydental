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

    /**
     * Constructor
     * @param id Id of city
     * @param page Page of list
     */
    public StreetListRequest(String id, String page) {
        super(DomainConst.PATH_LIST_STREETS);
        this.id = id;
        this.page = page;
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
            object.put(DomainConst.KEY_ID, id);
            object.put(DomainConst.KEY_PAGE, page);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
