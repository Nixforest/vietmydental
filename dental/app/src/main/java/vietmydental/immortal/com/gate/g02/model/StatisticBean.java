package vietmydental.immortal.com.gate.g02.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vietmydental.immortal.com.gate.g01.fragment.G01F01S01Fragment;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
public class StatisticBean extends ConfigBean {
    public String discount_amount = DomainConst.BLANK;
    public String total = DomainConst.BLANK;
    public String vfinal = DomainConst.BLANK;
    public String dept = DomainConst.BLANK;
    public String total_qty = DomainConst.BLANK;

//    public Boolean canSelectAgent;
//    public ArrayList<MedicalReceiptBean> listReceipts;
    public String data = DomainConst.BLANK;


    /**
     * Constructor.
     */
    public StatisticBean() {
        super();
    }
    /**
     * Constructor.
     */
    public StatisticBean(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        try {
            this.data = data.getString("data");
            if (!this.data.isEmpty()){
                JsonObject jsonObject = (JsonObject) jsonParser.parse(this.data);
                this.id = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_ID);
                this.name = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_NAME);
                this.discount_amount = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_DISCOUNT_AMOUNT);
                this.total = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_TOTAL);
                this.vfinal = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_FINAL);
                this.dept = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_DEBT);
                total_qty = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_TOTAL_QTY);
                //this.canSelectAgent = CommonProcess.getJsonString(jsonObject, DomainConst.KEY_DEPT);
                //listReceipts = CommonProcess.getListMedicalReceipt(jsonObject, DomainConst.KEY_LIST_RECEIPT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
//-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.