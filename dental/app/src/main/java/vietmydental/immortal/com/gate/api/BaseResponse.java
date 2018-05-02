package vietmydental.immortal.com.gate.api;

import org.json.JSONException;
import org.json.JSONObject;

import vietmydental.immortal.com.gate.utils.DomainConst;

/**
 * Response base class
 */
public class BaseResponse {
    /** Code */
    private int                 code                = 0;
    /** Response body */
    private String              respBody            = DomainConst.BLANK;
    /** Data */
    private JSONObject          jsonData            = null;
    /** Status */
    private int                 status              = 0;

    /** Constant */
    public static final int  API_RESPONSE_CODE_BAD_REQUEST  = 400;
    public static final int  API_RESPONSE_CODE_SUCCESS      = 200;
    public static final int  API_RESPONSE_CODE_UNAUTHORIZED = 401;
    public static final int API_RESPONSE_STATUS_FAILED      = 0;
    public static final int API_RESPONSE_STATUS_SUCCESS     = 1;

    /**
     * Initialize data for response.
     */
    private void initData() {
        if ((jsonData == null) && (respBody != null)) {
            try {
                jsonData = new JSONObject(respBody);
            } catch (JSONException e) {
                jsonData = new JSONObject();
                try {
                    jsonData.put(DomainConst.KEY_MESSAGE, "");
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
//                Log.e("Response", "Error when get url " + BaseRequest.getServerURL() + getSubURL() + " and response: " + respBody);
            }
        }
    }

    /**
     * Set response body
     * @param respBody Value to set
     */
    public void setRespBody(String respBody) {
        this.respBody = respBody;
    }

    /**
     * Set code value
     * @param code Value to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Get code value
     * @return Code value
     */
    public int getCode() {
        initData();
        if (jsonData != null && (jsonData.has(DomainConst.KEY_CODE))) {
            try {
                code = jsonData.getInt(DomainConst.KEY_CODE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return code;
    }

    /**
     * Get status
     * @return
     */
    public int getStatus() {
        initData();
        if (jsonData != null) {
            try {
                status = jsonData.getInt(DomainConst.KEY_STATUS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    /**
     * Get message
     * @return Message value
     */
    public String getMessage() {
        initData();
        if ((jsonData != null) && jsonData.has(DomainConst.KEY_MESSAGE)) {
            try {
                return jsonData.getString(DomainConst.KEY_MESSAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * Determine response's status is success
     * @return True if status == 1, False otherwise
     */
    public boolean isSuccess() {
        initData();
        if (jsonData != null) {
            try {
                status = jsonData.getInt(DomainConst.KEY_STATUS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return status == 1;
    }

    /**
     * Get json data
     * @return Json data value
     */
    public JSONObject getJsonData() {
        initData();
        return jsonData;
    }

    /**
     * Get data object
     * @return Data object
     */
    public JSONObject getData() {
        JSONObject currentData = getJsonData();
        JSONObject data = null;
        if (currentData != null) {
            try {
                data = currentData.getJSONObject(DomainConst.KEY_DATA);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return data;
    }
}
