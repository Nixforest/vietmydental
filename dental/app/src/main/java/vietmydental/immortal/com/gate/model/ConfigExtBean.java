package vietmydental.immortal.com.gate.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class ConfigExtBean extends ConfigBean {
    /** String */
    private String                          dataStr     = DomainConst.BLANK;
    /** Data ext */
    private ArrayList<ConfigExtBean>        dataExt     = new ArrayList<>();
    /** Data object */
    private JsonObject                      dataObj     = new JsonObject();

    /**
     * Constructor.
     */
    public ConfigExtBean() {
        super();
    }

    /**
     * Constructor
     * @param data JSONObject data
     */
    public ConfigExtBean(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        this.id         = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name       = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NAME);
        this.dataStr    = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_DATA);
        this.dataExt    = CommonProcess.getListConfigExt(gsonObject, DomainConst.KEY_DATA);
        this.dataObj    = CommonProcess.getJsonObject(gsonObject, DomainConst.KEY_DATA);
    }

    /**
     * Constructor
     * @param gsonObject JsonObject data
     */
    public ConfigExtBean(JsonObject gsonObject) {
        this.id         = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name       = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NAME);
        this.dataStr    = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_DATA);
        this.dataExt    = CommonProcess.getListConfigExt(gsonObject, DomainConst.KEY_DATA);
        this.dataObj    = CommonProcess.getJsonObject(gsonObject, DomainConst.KEY_DATA);
    }

    public String getDataStr() {
        return dataStr;
    }

    public ArrayList<ConfigExtBean> getDataExt() {
        return dataExt;
    }

    public JsonObject getDataObj() {
        return dataObj;
    }
}
