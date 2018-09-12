package vietmydental.immortal.com.gate.g01.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.g01.G01Const;
import vietmydental.immortal.com.gate.utils.DomainConst;

public class ReceiptCreateRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** Detail id */
    private final String detailId;
    /** Date */
    private final String date;
    /** Discount */
    private final String discount;
    /** Final */
    private final String finalAmount;
    /** Customer confirm */
    private final String isCustomerConfirm;
    /** Note */
    private final String note;
    /** Receiptionist id */
    private final String receiptionistId;

    public ReceiptCreateRequest(String token, String detailId, String date, String discount, String finalAmount, String isCustomerConfirm, String note, String receiptionistId) {
        super(G01Const.PATH_RECEIPT_CREATE);
        this.token = token;
        this.detailId = detailId;
        this.date = date;
        this.discount = discount;
        this.finalAmount = finalAmount;
        this.isCustomerConfirm = isCustomerConfirm;
        this.note = note;
        this.receiptionistId = receiptionistId;
    }

    /**
     * Get body for request.
     *
     * @return Json string of body
     */
    @Override
    protected List<NameValuePair> buildParameter() {
        ArrayList<NameValuePair> ret = new ArrayList<>();
        JSONObject object = this.generateDefaultJSONObject();
        try {
            object.put(DomainConst.KEY_TOKEN, token);
            object.put(DomainConst.KEY_DETAIL_ID, detailId);
            object.put(DomainConst.KEY_DATE, date);
            object.put(DomainConst.KEY_DISCOUNT, discount);
            object.put(DomainConst.KEY_FINAL, finalAmount);
            object.put(DomainConst.KEY_CUSTOMER_CONFIRM, isCustomerConfirm);
            object.put(DomainConst.KEY_NOTE, note);
            object.put(DomainConst.KEY_RECEIPTIONIST_ID, receiptionistId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
