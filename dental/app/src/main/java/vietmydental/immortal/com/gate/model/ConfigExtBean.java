package vietmydental.immortal.com.gate.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.core.Searchable;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class ConfigExtBean extends ConfigBean {
    /** String */
    private String                          dataStr     = DomainConst.BLANK;
    /** Data ext */
    private ArrayList<ConfigExtBean>        dataExt     = new ArrayList<>();
    /** Data object */
    private JsonObject                      dataObj     = new JsonObject();
    /** Data array object */
    private JsonArray                       dataArrObj  = new JsonArray();

    /**
     * Constructor.
     */
    public ConfigExtBean() {
        super();
    }

    /**
     * Constructor.
     * @param id Id
     * @param name Name
     */
    public ConfigExtBean(String id, String name) {
        super(id, name);
    }

    /**
     * Constructor.
     * @param id Id
     * @param name Name
     * @param data String data
     */
    public ConfigExtBean(String id, String name, String data) {
        super(id, name);
        dataStr = data;
    }

    /**
     * Constructor
     * @param data JSONObject data
     */
    public ConfigExtBean(JSONObject data) {
        super(data);
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        this.dataStr    = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_DATA);
        this.dataExt    = CommonProcess.getListConfigExt(gsonObject, DomainConst.KEY_DATA);
        this.dataObj    = CommonProcess.getJsonObject(gsonObject, DomainConst.KEY_DATA);
        this.dataArrObj = CommonProcess.getJsonArray(gsonObject, DomainConst.KEY_DATA);
    }

    /**
     * Constructor
     * @param gsonObject JsonObject data
     */
    public ConfigExtBean(JsonObject gsonObject) {
        super(gsonObject);
        this.dataStr    = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_DATA);
        this.dataExt    = CommonProcess.getListConfigExt(gsonObject, DomainConst.KEY_DATA);
        this.dataObj    = CommonProcess.getJsonObject(gsonObject, DomainConst.KEY_DATA);
        this.dataArrObj = CommonProcess.getJsonArray(gsonObject, DomainConst.KEY_DATA);
    }

    public String getDataStr() {
        return dataStr;
    }
    public void setDataStr(String value) {
        this.dataStr = value;
    }

    public ArrayList<ConfigExtBean> getDataExt() {
        return dataExt;
    }

    public JsonObject getDataObj() {
        return dataObj;
    }

    public JsonArray getDataArrObj() {
        return dataArrObj;
    }
    /**
     * Get value by id
     * @param id Id of value
     * @return Name value
     */
    public String getValueByIdExt(String id) {
        for (ConfigExtBean item :
                dataExt) {
            if (item.id.equals(id)) {
                return item.dataStr;
            }
        }
        return "";
    }
}
