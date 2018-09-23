package vietmydental.immortal.com.gate.g00.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class UserProfileBean extends ConfigBean {
    public String name = DomainConst.BLANK;
    public String phone = DomainConst.BLANK;
    public String address = DomainConst.BLANK;
    public String image = DomainConst.BLANK;
    public String email = DomainConst.BLANK;
    public String province_id = DomainConst.BLANK;
    public String district_id = DomainConst.BLANK;
    public String ward_id = DomainConst.BLANK;
    public String street_id = DomainConst.BLANK;
    public String house_numbers = DomainConst.BLANK;
    public String agent_id = DomainConst.BLANK;
    public String agent_name = DomainConst.BLANK;
    public String data = DomainConst.BLANK;


    /**
     * Constructor.
     */
    public UserProfileBean() {
        super();
    }
    /**
     * Constructor.
     */
    public UserProfileBean(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        try {
            this.data = data.getString("data");
            if (!this.data.isEmpty()){
                JsonObject jsonObject = (JsonObject) jsonParser.parse(this.data);
                this.id = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_ID);
                this.name = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_NAME);
                this.phone = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_PHONE);
                this.address = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_ADDRESS);
                this.email = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_EMAIL);
                this.image = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_IMAGE);
                this.province_id = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_CITY_ID);
                this.district_id = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_DISTRICT_ID);
                this.ward_id = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_WARD_ID);
                this.street_id = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_STREET_ID);
                this.house_numbers = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_HOUSE_NUMBER);
                this.agent_id = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_AGENT_ID);
                this.agent_name = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_AGENT_NAME);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
