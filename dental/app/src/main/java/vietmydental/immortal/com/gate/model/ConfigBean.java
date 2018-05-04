package vietmydental.immortal.com.gate.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class ConfigBean implements Serializable {
    /** Id */
    protected String id;
    /** Name */
    protected String name;
    /** Data */
    protected ArrayList<ConfigBean> data;

    /**
     * Default constructor.
     */
    public ConfigBean() {
        this.id = "";
        this.name = "";
        this.data = new ArrayList<>();
    }

    /**
     * Constructor.
     * @param id Id
     * @param name Name
     */
    public ConfigBean(String id, String name) {
        this.id = id;
        this.name = name;
        this.data = new ArrayList<>();
    }

    /**
     * Constructor.
     * @param id Id
     * @param name Name
     * @param data Data
     */
    public ConfigBean(String id, String name, ArrayList<ConfigBean> data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    /**
     * Constructor
     * @param data JSONObject
     */
    public ConfigBean(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        this.id         = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name       = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NAME);
        this.data       = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_DATA);
    }

    /**
     * Constructor
     * @param gsonObject JsonObject
     */
    public ConfigBean(JsonObject gsonObject) {
        this.id         = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name       = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NAME);
        this.data       = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_DATA);
    }

    /**
     * Get id.
     * @return Id
     */
    public String getId() {
        return id;
    }

    /**
     * Get id (Integer value).
     * @return Id
     */
    public int getIdInt() {
        try {
            return Integer.valueOf(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Get name.
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Get data.
     * @return Data
     */
    public ArrayList<ConfigBean> getData() {
        return data;
    }

    /**
     * Get value by id
     * @param id Id of value
     * @return Name value
     */
    public String getValueById(String id) {
        for (ConfigBean item :
                data) {
            if (item.id.equals(id)) {
                return item.name;
            }
        }
        return "";
    }
}
