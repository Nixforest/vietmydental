package vietmydental.immortal.com.gate.g03.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.g03.G03Const;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.
public class UpdateDailyReportRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Id */
    private final String id;
    /** Status */
    private final int status;

    /**
     * Constructor
     * @param token Token
     * @param id Id
     * @param id Status
     */
    public UpdateDailyReportRequest(String token, String id, int status) {
        super(G03Const.PATH_UPDATE_DAILY_REPORT);
        this.token      = token;
        this.id         = id;
        this.status     = status;
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
            object.put(DomainConst.KEY_ID, id);
            object.put(DomainConst.KEY_STATUS, String.valueOf(status));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.