package vietmydental.immortal.com.gate.g03.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.g03.G03Const;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.
public class DailyReportListRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Month */
    private final String month;

    /**
     * Constructor
     * @param token Token
     * @param month Month
     */
    public DailyReportListRequest(String token, String month) {
        super(G03Const.PATH_DAILY_REPORT_LIST);
        this.token      = token;
        this.month      = month;
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
            object.put(DomainConst.KEY_MONTH, month);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.