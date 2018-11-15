package vietmydental.immortal.com.gate.g00.api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseRequest;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.BuildConfig;
//++ BUG0151-IMT (KhoiVT 20181114) get domain by GetDomainName api
public class GetDomainRequest extends BaseRequest {
    /** bundle_id */
    private String bundleId = DomainConst.BLANK;

    /**
     * Constructor
     */
    public GetDomainRequest() {
        super(DomainConst.PATH_DOMAIN);
        this.bundleId = DomainConst.BUNDLE_ID;
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
            String bundle_id = DomainConst.BUNDLE_APP_ID;
            if (BaseModel.getInstance().getMode() == DomainConst.MODE_TRAINING){
                this.bundleId = bundle_id + ".training";
            }
            else{
                this.bundleId = bundle_id;
            }
            object.put(DomainConst.KEY_BUNDLE_ID, bundleId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ret.add(new BasicNameValuePair(DomainConst.KEY_Q, object.toString()));
        return ret;
    }
}
//-- BUG0151-IMT (KhoiVT 20181114) get domain by GetDomainName api