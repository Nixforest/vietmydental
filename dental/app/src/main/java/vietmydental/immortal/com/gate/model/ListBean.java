package vietmydental.immortal.com.gate.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.Serializable;

import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class ListBean implements Serializable {
    /** Number of record */
    private int total_record        = 0;
    /** Number of page */
    private int total_page          = 0;

    /**
     * Constructor
     * @param record Record number
     * @param page Page number
     */
    public ListBean(int record, int page) {
        this.total_record = record;
        this.total_page = page;
    }

    /**
     * Constructor
     * @param gsonObject JsonObject data
     */
    public ListBean(JsonObject gsonObject) {
        this.total_record   = CommonProcess.getJsonInt(gsonObject, DomainConst.KEY_TOTAL_RECORD);
        this.total_page     = CommonProcess.getJsonInt(gsonObject, DomainConst.KEY_TOTAL_PAGE);
    }
}
