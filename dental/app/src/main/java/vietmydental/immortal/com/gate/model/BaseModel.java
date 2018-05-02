package vietmydental.immortal.com.gate.model;

import android.content.Context;
import android.content.SharedPreferences;

import vietmydental.immortal.com.gate.utils.DomainConst;

public class BaseModel {
    /** Singleton instance */
    private static BaseModel        instance;
    /** Database name */
    private static final String     DB_NAME             = "Share_DB";

    /** Current application mode */
    private int                     mode                = DomainConst.MODE_TRAINING;

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
    }
    /**
     * Get server URL.
     * @return Server URL
     */
    public String getServerURL() {
        if (this.mode == DomainConst.MODE_TRAINING) {
            return DomainConst.SERVER_URL_TRAINING;
        } else {
            return DomainConst.SERVER_URL;
        }
    }

    /**
     * Get Shared preference.
     * @param ctx Current context
     * @return Shared preference object
     */
    public SharedPreferences getSharedPref(Context ctx) {
        return ctx.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
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
     * Get current user token
     * @return User token
     */
    public String getToken(Context ctx) {
        return getValue(ctx, DomainConst.KEY_TOKEN);
    }

    /**
     * Set token.
     * @param ctx Current context
     * @param value Value
     */
    public void setToken(Context ctx, String value) {
        setValue(ctx, DomainConst.KEY_TOKEN, value);
    }
}
