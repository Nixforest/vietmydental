package vietmydental.immortal.com.gate.g04.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
public class CustomerBean extends ConfigBean {
    //public ArrayList<ReceiptBean> list = new ArrayList<>();
    public String dataId = DomainConst.BLANK;

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
        try {
            this.dataId = data.getString("data");
//            if (!this.data.isEmpty()){
//                JsonObject jsonObject = (JsonObject) jsonParser.parse(this.data);
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
//-- BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.