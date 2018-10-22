package vietmydental.immortal.com.gate.g04.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.g04.G04Const;
import vietmydental.immortal.com.gate.utils.DomainConst;
//++ BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
public class GetCustomerByQRCodeRequest extends BaseRequest {
    /** Token */
    private final String token;
    /** QR Code */
    private final String qr;

    /**
     * Constructor
     * @param token Token
     * @param qr QR Code
     */
    public GetCustomerByQRCodeRequest(String token, String qr) {
        super(G04Const.PATH_CUSTOMER_BY_QRCODE);
        this.token      = token;
        this.qr         = qr;
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
            object.put(DomainConst.KEY_QR, qr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
//-- BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.