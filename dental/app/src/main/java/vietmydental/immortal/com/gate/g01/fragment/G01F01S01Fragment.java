package vietmydental.immortal.com.gate.g01.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.CustomerInfoRequest;
import vietmydental.immortal.com.gate.g01.api.MedicalRecordInfoRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F01S01ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F01S01Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listInfo)
    protected ListView listInfo;
    /** Id of customer */
    private String id = "";
    /** Data */
    private MedicalHistoryRespBean respData;

    public G01F01S01Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f01_s01, container, false);
        ButterKnife.bind(this, rootView);

        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            this.parentActivity.showLoadingView(true);
            MedicalRecordInfoRequest request = new MedicalRecordInfoRequest(token, id) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        parseData(resp.getArrayData());
                        if (listInfo != null) {
                            listInfo.setAdapter(new G01F01S01ListAdapter(respData.getVisibleList(), parentActivity.getLayoutInflater()));
                        }
                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }

        return rootView;
    }

    // MARK: Logic

    /**
     * Parse data from response.
     * @param data JSONObject data
     */
    private void parseData(JSONArray data) {
        JsonParser jsonParser = new JsonParser();
        JsonArray gsonObject = (JsonArray) jsonParser.parse(data.toString());
        respData = new MedicalHistoryRespBean(gsonObject);
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return "Hồ sơ bệnh án";
    }

    /**
     * Get title config to show on menu
     *
     * @return
     */
    @Override
    public TitleConfigObject getTitleConfig() {
        return new BaseFragment.TitleConfigObject(false, true);
    }

    public void setId(String id) {
        this.id = id;
    }

    public class MedicalHistoryRespBean {
        /** List information */
        private ArrayList<ConfigExtBean> list = new ArrayList<>();

        public MedicalHistoryRespBean(JsonArray gsonObject) {
            super();
            try {
                JsonArray array = gsonObject.getAsJsonArray();
                for (JsonElement obj :
                        array) {
                    list.add(new ConfigExtBean(obj.getAsJsonObject()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /**
         * Get list customer.
         * @return List customer
         */
        public ArrayList<ConfigExtBean> getList() {
            return list;
        }

        /**
         * Get visible list
         * @return List item have name not empty
         */
        public ArrayList<ConfigExtBean> getVisibleList() {
            ArrayList<ConfigExtBean> retVal = new ArrayList<>();

            for (ConfigExtBean bean :
                    list) {
                if (!bean.getName().isEmpty()) {
                    retVal.add(bean);
                } else {
                    if (bean.getId().equals(DomainConst.ITEM_RECORD_NUMBER)) {
                        ConfigExtBean element = new ConfigExtBean(bean.getId(), "Bổ sung số bệnh án");
                        retVal.add(element);
                    }
                }
            }
            
            return retVal;
        }
    }
}
