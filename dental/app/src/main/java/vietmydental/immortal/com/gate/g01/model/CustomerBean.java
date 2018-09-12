package vietmydental.immortal.com.gate.g01.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class CustomerBean extends ConfigBean {
    // MARK: Properties
    /** Gender */
    private String gender               = DomainConst.BLANK;
    /** Age */
    private String age                  = DomainConst.BLANK;
    /** Phone */
    private String phone                = DomainConst.BLANK;
    /** Address */
    private String address              = DomainConst.BLANK;
    /** Birthday */
    private String birthday             = DomainConst.BLANK;
    /** Email */
    private String email                = DomainConst.BLANK;
    /** Agent */
    private String agent                = DomainConst.BLANK;
    /** Career */
    private String career               = DomainConst.BLANK;
    /** Characteristics */
    private String characteristics      = DomainConst.BLANK;
    /** Record number */
    private String record_number        = DomainConst.BLANK;
    /** Medical history */
    private ArrayList<ConfigBean> medical_history   = new ArrayList<>();

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
        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        this.id                 = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name               = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NAME);
        this.gender             = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_GENDER);
        this.age                = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_AGE);
        this.phone              = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_PHONE);
        this.address            = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ADDRESS);
        this.birthday           = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_BIRTHDAY);
        this.email              = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_EMAIL);
        this.agent              = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_AGENT);
        this.career             = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_CAREER);
        this.characteristics    = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_CHARACTERISTICS);
        this.record_number      = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_RECORD_NUMBER);
        this.medical_history    = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_MEDICAL_HISTORY);
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getAgent() {
        return agent;
    }

    public String getCareer() {
        return career;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public String getRecord_number() {
        return record_number;
    }

    public ArrayList<ConfigBean> getMedical_history() {
        return medical_history;
    }
}
