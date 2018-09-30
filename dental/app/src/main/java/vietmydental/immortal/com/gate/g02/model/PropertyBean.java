package vietmydental.immortal.com.gate.g02.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import ir.mirrajabi.searchdialog.core.Searchable;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
public class PropertyBean implements Serializable, Searchable {
    /** Id */
    protected String id;
    /** Name */
    protected String name;
    /** Data */
    protected String data;

    /**
     * Default constructor.
     */
    public PropertyBean() {
        this.id = "";
        this.name = "";
        this.data = "";
    }

    /**
     * Constructor.
     *
     * @param id   Id
     * @param name Name
     */
    public PropertyBean(String id, String name, String data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    /**
     * Constructor
     *
     * @param data JSONObject
     */
    public PropertyBean(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        this.id = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NAME);
        this.data = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_DATA);
    }

    /**
     * Constructor
     *
     * @param gsonObject JsonObject
     */
    public PropertyBean(JsonObject gsonObject) {
        this.id = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NAME);
        this.data = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_DATA);
    }

    /**
     * Get id.
     *
     * @return Id
     */
    public String getId() {
        return id;
    }

    /**
     * Get id (Integer value).
     *
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
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * Set name
     *
     * @param name Name value
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get data.
     *
     * @return Data
     */
    public String getData() {
        return data;
    }


    @Override
    public String getTitle() {
        return name;
    }

    /**
     * Check if keyword
     *
     * @param keyword
     * @return
     */
    public boolean contains(String keyword) {
        String formedKeywork = CommonProcess.removeSign4VietNameseString(keyword).toLowerCase();
        String searchString = CommonProcess.removeSign4VietNameseString(getTitle()).toLowerCase();
        return searchString.contains(formedKeywork);
    }
}
//-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.