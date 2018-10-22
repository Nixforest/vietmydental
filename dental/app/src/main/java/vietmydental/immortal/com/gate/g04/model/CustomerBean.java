package vietmydental.immortal.com.gate.g04.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
public class CustomerBean extends ConfigBean {
    public String dataParent = DomainConst.BLANK;
    public String dataChild = DomainConst.BLANK;

    /**
     * Constructor.
     */
    public CustomerBean() {
        super();
    }
    /**
     * Constructor.
     */
    public CustomerBean(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        try {
            this.dataParent = data.getString("data");
            if (!this.dataParent.isEmpty()){
                JsonObject jsonObject = (JsonObject) jsonParser.parse(this.dataParent);
                this.id = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_ID);
                this.name = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_NAME);
                this.dataChild = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_DATA);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
//-- BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.