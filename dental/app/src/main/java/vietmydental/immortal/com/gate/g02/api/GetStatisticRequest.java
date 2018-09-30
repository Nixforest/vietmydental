package vietmydental.immortal.com.gate.g02.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.g01.G01Const;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
public class GetStatisticRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Date From */
    private final String fromDate;
    /** Date To */
    private final String toDate;
    /** Medical history */
    private final ArrayList<String> agentId;

    /**
     * Constructor
     * @param token Token
     * @param fromDate From Date
     * @param toDate To Date
     * @param agentId List Agent
     */
    public GetStatisticRequest(String token, String fromDate, String toDate, ArrayList<String> agentId) {
        super(DomainConst.PATH_GET_STATISTICS);
        this.token = token;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.agentId = agentId;
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
            object.put(DomainConst.KEY_DATE_FROM, fromDate);
            object.put(DomainConst.KEY_DATE_TO, toDate);
            //object.put(DomainConst.KEY_AGENT_ID, agentId);
            JSONArray array = new JSONArray();
            for (String agent_id :
                    agentId) {
                array.put(agent_id);
            }
            object.put(DomainConst.KEY_AGENT_ID, array);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
//-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.