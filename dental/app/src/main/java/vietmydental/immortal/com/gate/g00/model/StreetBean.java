package vietmydental.immortal.com.gate.g00.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class StreetBean extends ConfigBean {
    public String data = DomainConst.BLANK;
    public String total_page = DomainConst.BLANK;
    public String total_record = DomainConst.BLANK;
    public ArrayList<ConfigBean> list;


    /**
     * Constructor.
     */
    public StreetBean() {
        super();
    }
    /**
     * Constructor.
     */
    public StreetBean(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        try {
            this.data = data.getString("data");
            if (!this.data.isEmpty()){
                JsonObject jsonObject = (JsonObject) jsonParser.parse(this.data);
                this.total_page = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_TOTAL_PAGE);
                this.total_record = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_TOTAL_RECORD);
                list = CommonProcess.getListConfig(jsonObject, DomainConst.KEY_LIST);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}