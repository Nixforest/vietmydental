package vietmydental.immortal.com.gate.g01.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class TreatmentBean extends ConfigBean {
    // MARK: Properties
    /** Start date */
    private String start_date               = DomainConst.BLANK;
    /** End date */
    private String end_date                 = DomainConst.BLANK;
    /** Diagnosis */
    private String diagnosis                = DomainConst.BLANK;
    /** Status */
    private String status                   = DomainConst.BLANK;
    /**
     * Constructor.
     */
    public TreatmentBean() {
        super();
    }
    /**
     * Constructor.
     */
    public TreatmentBean(JsonObject gsonObject) {
//        JsonParser jsonParser = new JsonParser();
//        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        this.id                 = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name               = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NAME);
        this.start_date         = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_START_DATE);
        this.end_date           = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_END_DATE);
        this.diagnosis          = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_DIAGNOSIS);
        this.status             = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_STATUS);
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getStatus() {
        return status;
    }
}
