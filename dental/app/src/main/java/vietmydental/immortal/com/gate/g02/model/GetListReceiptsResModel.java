package vietmydental.immortal.com.gate.g02.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
public class GetListReceiptsResModel  extends ConfigBean {
    public String total_record = DomainConst.BLANK;
    public String total_page = DomainConst.BLANK;
    public ArrayList<ReceiptBean> list = new ArrayList<>();
    public String data = DomainConst.BLANK;


    /**
     * Constructor.
     */
    public GetListReceiptsResModel() {
        super();
    }
    /**
     * Constructor.
     */
    public GetListReceiptsResModel(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        try {
            this.data = data.getString("data");
            if (!this.data.isEmpty()){
                JsonObject jsonObject = (JsonObject) jsonParser.parse(this.data);
                this.total_record = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_TOTAL_RECORD);
                this.total_page = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_TOTAL_PAGE);
                this.list = CommonProcess.getListReceipt(jsonObject, DomainConst.KEY_LIST);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
//-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.