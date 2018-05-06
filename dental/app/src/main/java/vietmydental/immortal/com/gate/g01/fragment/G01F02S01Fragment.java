package vietmydental.immortal.com.gate.g01.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.TreatmentListRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F02S01ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.model.ListBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F02S01Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listInfo)
    protected ListView listInfo;
    /** Id of customer */
    private String id = "";
    /** Current page */
    private int page = 0;
    /** Data */
    private TreatmentListRespBean respData;

    public G01F02S01Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f02_s01, container, false);
        ButterKnife.bind(this, rootView);

        listInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ConfigExtBean bean = respData.getList().get(i);
                parentActivity.openG01F02S02(bean.getId());
            }
        });
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            this.parentActivity.showLoadingView(true);
            TreatmentListRequest request = new TreatmentListRequest(token, String.valueOf(page), id) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        parseData(resp.getData());
                        if (listInfo != null) {
                            listInfo.setAdapter(new G01F02S01ListAdapter(respData.getList(), parentActivity.getLayoutInflater()));
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

    /**
     * Set id customer
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return "Danh sách Đợt điều trị";
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

    // MARK: Event handler
    /**
     * Handle click on add button
     */
    @OnClick(R.id.btnAdd)
    public void btnAddClick() {
//        CommonProcess.showErrorMessage(parentActivity, "123");
        parentActivity.openG01F02S06(id);
    }

    // MARK: Logic

    /**
     * Parse data
     * @param data Data object
     */
    private void parseData(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        respData = new TreatmentListRespBean(gsonObject);
    }

    /**
     * Treatment list response bean
     */
    public class TreatmentListRespBean extends ListBean {
        /** List data */
        private ArrayList<ConfigExtBean> list = new ArrayList<>();

        /**
         * Constructor
         *
         * @param record Record number
         * @param page   Page number
         */
        public TreatmentListRespBean(int record, int page) {
            super(record, page);
        }

        /**
         * Constructor
         *
         * @param gsonObject JsonObject data
         */
        public TreatmentListRespBean(JsonObject gsonObject) {
            super(gsonObject);
            try {
                JsonArray array = gsonObject.getAsJsonArray(DomainConst.KEY_LIST);
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
    }
}
