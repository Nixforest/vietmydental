package vietmydental.immortal.com.vietmydental.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import vietmydental.immortal.com.vietmydental.model.ConfigBean;

/**
 * Created by nguyenpt on 4/16/18.
 */

public class BaseModel {
    /** Singleton instance */
    private static BaseModel instance;
    /** Database name */
    private static final String DB_NAME                 = "Share_DB";
    /** Field: MenuConfig */
    private static final String fieldMenuConfig         = "MenuConfig";
    /** Field: UpHold */
    private static final String fieldUpHold             = "UpHold";
    /** Field: SignUpCode */
    private static final String fieldSignUpCode         = "SignUpCode";
    /** Field: RegistrationCode */
    private static final String fieldRegistrationCode   = "RegistrationCode";
    /** Field: Token */
    private static final String fieldToken              = "Token";
    /** Field: Issue */
    private static final String fieldIssue              = "Issue";
    /** Field: MaxUpload */
    private static final String fieldMaxUpload          = "MaxUpload";
    /** Field: RoleId */
    private static final String fieldRoleId             = "RoleId";
    /** Field: UserId */
    private static final String fieldUserId             = "UserId";
    /** Field: UserInfo */
    private static final String fieldUserInfo           = "UserInfo";
    /** Field: CheckMenu */
    private static final String fieldCheckMenu          = "CheckMenu";
    /** Field: NeedChangePass */
    private static final String fieldNeedChangePass     = "NeedChangePass";
    /** Singleton instance */
//    private int mode = DomainConst.MODE_RUNNING;
    /** Singleton instance */
//    private String serverURL = DomainConst.SERVER_URL;
    /** Singleton instance */
    private int mode = DomainConst.MODE_TRAINING;
    /** Singleton instance */
    private String serverURL = DomainConst.SERVER_URL_TRAINING;

    /**
     * Get singleton instance.
     * @return Singleton instance
     */
    public static BaseModel getInstance() {
        if (instance == null) {
            instance = new BaseModel();
        }
        return instance;
    }

    /**
     * Get current mode.
     * @return Current mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Set current mode.
     * @param mode Mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
        // Update value of server URL by mode
        if (mode == DomainConst.MODE_RUNNING) {
            setServerURL(DomainConst.SERVER_URL);
        } else if (mode == DomainConst.MODE_TRAINING) {
            setServerURL(DomainConst.SERVER_URL_TRAINING);
        }
    }

    /**
     * Get server URL.
     * @return Server URL
     */
    public String getServerURL() {
        return serverURL;
    }

    /**
     * Set server URL.
     * @param serverURL Server URL
     */
    public void setServerURL(String serverURL) {
        this.serverURL = serverURL;
    }

    /**
     * Get need change pass flag.
     * @param ctx Current context
     * @return Need change pass flag
     */
    public String isNeedChangePass(Context ctx) {
        return getValue(ctx, fieldNeedChangePass);
    }

    /**
     * Set need change pass flag.
     * @param ctx Current context
     * @param value Need change pass flag
     */
    public void setNeedChangePass(Context ctx, String value) {
        setValue(ctx, fieldNeedChangePass, value);
    }

    /**
     * Get Shared preference.
     * @param ctx Current context
     * @return Shared preference object
     */
    public SharedPreferences getSharedPref(Context ctx) {
        return ctx.getSharedPreferences(DB_NAME, Context.MODE_MULTI_PROCESS);
//        return ctx.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Set value.
     * @param ctx Current context
     * @param key Key
     * @param value Value
     */
    public void setValue(Context ctx, String key, String value) {
        SharedPreferences sharedPref = getSharedPref(ctx);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Clean all value.
     * @param context Current context
     */
    public void cleanAll(Context context) {
        BaseModel.getInstance().setToken(context, null);
        BaseModel.getInstance().setMenuConfig(context, null);
//        SharePref.getInstance().setUpHold(context, null); // Anh Dung close Now 23, 2015, có thể không cần xóa cái này vì không rõ cập nhật ntn mà bị mất khi có notify chưa login
    }

    /**
     * Get value.
     * @param ctx Current context
     * @param key Key
     * @return Value
     */
    public String getValue(Context ctx, String key) {
        SharedPreferences sharedPref = getSharedPref(ctx);
        return sharedPref.getString(key, null);
    }

    /**
     * Set menu config.
     * @param ctx Current context
     * @param value Value
     */
    public void setMenuConfig(Context ctx, String value) {
        setValue(ctx, fieldMenuConfig, value);
    }

    /**
     * Get menu config.
     * @param ctx Current context
     * @return Menu config
     */
    public String getMenuConfig(Context ctx) {
        return getValue(ctx, fieldMenuConfig);
    }

    /**
     * Set token.
     * @param ctx Current context
     * @param value Value
     */
    public void setToken(Context ctx, String value) {
        setValue(ctx, fieldToken, value);
    }

    /**
     * Get token.
     * @param ctx Current context
     * @return Token
     */
    public String getToken(Context ctx) {
        return getValue(ctx, fieldToken);
    }

    /**
     * Set uphold.
     * @param ctx Current context
     * @param value Value
     */
    public void setUpHold(Context ctx, String value) {
        setValue(ctx, fieldUpHold, value);
    }

    /**
     * Get check menu.
     *
     * @param ctx Current context
     * @return Check menu
     */
    public List<ConfigBean> getCheckMenu(Context ctx) {
        String checkMenu = getValue(ctx, fieldCheckMenu);
        return fromJson(checkMenu, new TypeToken<List<ConfigBean>>() {
        }.getType());
    }

    /**
     * Convert json to Object by json
     *
     * @param json    Json string
     * @param typeOfT type or object
     * @param <T>     Return type
     * @return
     */
    private <T> T fromJson(String json, Type typeOfT) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert json to Object by json
     *
     * @param json     Json string
     * @param classOfT type or object
     * @param <T>      Return type
     * @return
     */
    private <T> T fromJson(String json, Class<T> classOfT) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get json string from object
     *
     * @param object
     * @return
     */
    private String toJson(Object object) {
        String json = null;
        try {
            Gson gson = new Gson();
            json = gson.toJson(object);
            gson = null;
        } catch (Exception ex) {
            // Do nothing
        }
        return json;
    }

    /**
     * Set registration code.
     * @param ctx Current context
     * @param value Value
     */
    public void setRegistrationCode(Context ctx, String value) {
        setValue(ctx, fieldRegistrationCode, value);
    }

    /**
     * Get registration code.
     * @param ctx Current context
     * @return Registration code
     */
    public String getRegistrationCode(Context ctx) {
        String value = getValue(ctx, fieldRegistrationCode);
        if (value == null) {
            value = "";
        }
        return value;
    }
}
