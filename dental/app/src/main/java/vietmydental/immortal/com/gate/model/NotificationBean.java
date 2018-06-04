package vietmydental.immortal.com.gate.model;

import com.google.android.gms.common.internal.service.Common;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class NotificationBean extends ConfigBean {
    /** Singleton instance */
//    private static NotificationBean instance;
    /**
     * Category.
     */
    protected String category;
    /**
     * Object id.
     */
    protected String objectId;
    /**
     * Body.
     */
    protected String body;

    /**
     * Constant
     */
    public static final String NOTIFY_CATEGORY_NEW_TREATMENT_SCHEDULE               = "1";

    /**
     * Constructor.
     */
//    public NotificationBean() {
//        super();
//    }

//    /**
//     * Get singleton instance.
//     * @return Singleton instance
//     */
//    public static NotificationBean getInstance() {
//        if (instance == null) {
//            instance = new NotificationBean();
//        }
//        return instance;
//    }

    /**
     * Constructor
     * @param data JSONObject
     */
    public NotificationBean(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        this.id         = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name       = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_BODY);
        this.category   = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_CATEGORY);
        this.objectId   = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_OBJECT_ID_NEW);
        this.body       = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_BODY);
    }

    /**
     * Clear data.
     */
    public void clear() {
        this.id         = DomainConst.BLANK;
        this.name       = DomainConst.BLANK;
        this.category   = DomainConst.BLANK;
        this.objectId   = DomainConst.BLANK;
        this.body       = DomainConst.BLANK;
    }

    /**
     * Get category value
     * @return
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Get object id value
     * @return
     */
    public String getObjectId() {
        return this.objectId;
    }

    /**
     * Get body value
     * @return
     */
    public String getBody() {
        return this.objectId;
    }
}
