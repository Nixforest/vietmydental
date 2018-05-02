package vietmydental.immortal.com.gate.g00.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class ChangeProfileRequest extends BaseRequest {
    /** Name */
    private final String name;
    /** Id of city */
    private final String cityId;
    /** Id of district */
    private final String districtId;
    /** Id of ward */
    private final String wardId;
    /** Id of street */
    private final String streetId;
    /** House number */
    private final String houseNumber;
    /** Email */
    private final String email;
    /** Id of agent */
    private final String agentId;
    /** Token */
    private final String token;

    /**
     * Constructor
     * @param name Name
     * @param cityId Id of city
     * @param districtId Id of district
     * @param wardId Id of ward
     * @param streetId Id of street
     * @param houseNumber House number
     * @param email Email
     * @param agentId Id of agent
     * @param token
     */
    public ChangeProfileRequest(String name, String cityId, String districtId, String wardId,
                                String streetId, String houseNumber, String email, String agentId,
                                String token) {
        super(DomainConst.PATH_USER_CHANGE_PROFILE);
        this.name = name;
        this.cityId = cityId;
        this.districtId = districtId;
        this.wardId = wardId;
        this.streetId = streetId;
        this.houseNumber = houseNumber;
        this.email = email;
        this.agentId = agentId;
        this.token = token;
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
            object.put(DomainConst.KEY_CITY_ID, cityId);
            object.put(DomainConst.KEY_DISTRICT_ID, districtId);
            object.put(DomainConst.KEY_WARD_ID, wardId);
            object.put(DomainConst.KEY_STREET_ID, streetId);
            object.put(DomainConst.KEY_HOUSE_NUMBER, houseNumber);
            object.put(DomainConst.KEY_EMAIL, email);
            object.put(DomainConst.KEY_AGENT_ID, agentId);
            object.put(DomainConst.KEY_TOKEN, token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
