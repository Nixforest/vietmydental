package vietmydental.immortal.com.gate.g03.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
//++ BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.
public class DailyReportListResModel extends BaseResponse {
    /** List information */
    private ArrayList<ReceiptBean> list = new ArrayList<>();

    public DailyReportListResModel(JsonArray gsonObject) {
        super();
        try {
            JsonArray array = gsonObject.getAsJsonArray();
            for (JsonElement obj :
                    array) {
                list.add(new ReceiptBean(obj.getAsJsonObject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get list customer.
     * @return List customer
     */
    public ArrayList<ReceiptBean> getList() {
        return list;
    }
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.