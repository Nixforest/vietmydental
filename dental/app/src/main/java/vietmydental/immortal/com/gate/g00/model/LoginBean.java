package vietmydental.immortal.com.gate.g00.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class LoginBean extends ConfigBean {
    /** Singleton instance */
    private static LoginBean instance;
    // MARK: Properties
    /** User token */
    private String token                            = DomainConst.BLANK;
    /** Id of role */
    private String role_id                          = DomainConst.BLANK;
    /** Flag need change pass */
    private String need_change_pass                 = DomainConst.BLANK;
    /** List menu */
    private ArrayList<ConfigBean> menu              = new ArrayList<>();
    /** Pathological config */
    private ArrayList<ConfigBean> pathological      = new ArrayList<>();
    /** Address config */
    private ArrayList<ConfigBean> address_config    = new ArrayList<>();
    /** Diagnosis config */
    private ArrayList<ConfigBean> diagnosis         = new ArrayList<>();
    /** Treatment type config */
    private ArrayList<ConfigBean> treatment         = new ArrayList<>();
    /** Teeth config */
    private ArrayList<ConfigBean> teeth             = new ArrayList<>();
    /** Timer config */
    private ArrayList<ConfigBean> timer             = new ArrayList<>();

    /**
     * Constructor.
     */
    public LoginBean() {
        super();
    }

    /**
     * Get singleton instance.
     * @return Singleton instance
     */
    public static LoginBean getInstance() {
        if (instance == null) {
            instance = new LoginBean();
        }
        return instance;
    }

    /**
     * Constructor
     * @param data JSONObject data
     */
    public void updateData(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        this.token              = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_TOKEN);
        this.role_id            = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ROLE_ID);
        this.need_change_pass   = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NEED_CHANGE_PASS);
        this.menu = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_MENU);
        this.menu.add(new ConfigBean(DomainConst.MENU_ID_LIST.CONFIGURATION, "Cài đặt"));
        this.menu.add(new ConfigBean(DomainConst.MENU_ID_LIST.LOGOUT, "Thoát"));
        this.pathological       = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_PATHOLOGICAL);
        this.address_config     = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_ADDRESS_CONFIG);
        this.diagnosis          = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_DIAGNOSIS);
        this.treatment          = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_TREATMENT);
        this.teeth              = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_TEETH);
        this.timer              = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_TIMER);
    }

    /**
     * Clear data when logout.
     */
    public void clearData() {
        this.token = DomainConst.BLANK;
        this.role_id = DomainConst.BLANK;
        this.menu.clear();
        this.menu.add(new ConfigBean(DomainConst.MENU_ID_LIST.LOGIN, "Đăng nhập"));
        this.menu.add(new ConfigBean(DomainConst.MENU_ID_LIST.CONFIGURATION, "Cài đặt"));
        this.pathological.clear();
        this.address_config.clear();
        this.diagnosis.clear();
        this.treatment.clear();
        this.teeth.clear();
        this.timer.clear();
    }

    /**
     * Get token value
     * @return Token value
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Get menu value
     * @return Menu value
     */
    public ArrayList<ConfigBean> getMenu() {
        return menu;
    }
}
