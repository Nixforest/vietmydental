package vietmydental.immortal.com.gate.g00.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
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
    private ArrayList<ConfigExtBean> diagnosis      = new ArrayList<>();
    /** Treatment type config */
    private ArrayList<ConfigExtBean> treatment      = new ArrayList<>();
    /** Teeth config */
    private ArrayList<ConfigExtBean> teeth          = new ArrayList<>();
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
        this.id         = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ID);
        this.name       = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NAME);
        this.token              = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_TOKEN);
        this.role_id            = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_ROLE_ID);
        this.need_change_pass   = CommonProcess.getJsonString(gsonObject, DomainConst.KEY_NEED_CHANGE_PASS);
        this.menu = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_MENU);
        this.menu.add(new ConfigBean(DomainConst.MENU_ID_LIST.CONFIGURATION, "Cài đặt"));
        this.menu.add(new ConfigBean(DomainConst.MENU_ID_LIST.LOGOUT, "Thoát"));
        this.pathological       = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_PATHOLOGICAL);
        this.address_config     = CommonProcess.getListConfig(gsonObject, DomainConst.KEY_ADDRESS_CONFIG);
        this.diagnosis          = CommonProcess.getListConfigExt(gsonObject, DomainConst.KEY_DIAGNOSIS);
        this.treatment          = CommonProcess.getListConfigExt(gsonObject, DomainConst.KEY_TREATMENT);
        this.teeth              = CommonProcess.getListConfigExt(gsonObject, DomainConst.KEY_TEETH);
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
        this.getPathological().clear();
        this.address_config.clear();
        this.getDiagnosis().clear();
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

    /**
     * Get pathological value
     * @return Pathological value
     */
    public ArrayList<ConfigBean> getPathological() {
        return pathological;
    }
    public ArrayList<ConfigBean> getTimer() {
        return timer;
    }

    public ArrayList<ConfigExtBean> getDiagnosis() {
        return diagnosis;
    }

    /**
     * Get list full of diagnosis, only children items in 1 array
     * @return Children list items
     */
    public ArrayList<ConfigExtBean> getDiagnosisFull() {
        ArrayList<ConfigExtBean> retVal = new ArrayList<>();
        for (ConfigExtBean item :
                diagnosis) {
            for (ConfigExtBean child :
                    item.getDataExt()) {
                retVal.add(child);
            }
        }
        return retVal;
    }

    public ArrayList<ConfigExtBean> getTooth() {
        return teeth;
    }

    public ArrayList<ConfigExtBean> getTreatment() {
        return treatment;
    }

    /**
     * Get diagnosis by id
     * @param id Id of item
     * @return Object
     */
    public ConfigExtBean getDiagnosis(String id) {
        for (ConfigExtBean item :
                diagnosis) {
            if (id.equals(item.getId())) {
                return item;
            } else {
                if (!item.getDataExt().isEmpty()) {
                    for (ConfigExtBean child :
                            item.getDataExt()) {
                        if (id.equals(child.getId())) {
                            return child;
                        }
                    }
                }
            }
        }
        return new ConfigExtBean();
    }

    /**
     * Get treatment by id
     * @param id Id value
     * @return Object
     */
    public ConfigExtBean getTreatment(String id) {
        for (ConfigExtBean item :
                treatment) {
            for (ConfigExtBean child :
                    item.getDataExt()) {
                if (child.getId().equals(id)) {
                    return child;
                }
            }
        }
        return null;
    }
}
