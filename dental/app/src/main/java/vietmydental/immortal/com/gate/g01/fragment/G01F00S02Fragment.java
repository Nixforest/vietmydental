package vietmydental.immortal.com.gate.g01.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.CustomerInfoRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F00S02ListAdapter;
import vietmydental.immortal.com.gate.g01.model.CustomerBean;
import vietmydental.immortal.com.gate.g01.model.TransactionObject;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F00S02Fragment extends BaseFragment<G00HomeActivity> {
    /** Id of customer */
    private String id = "";
    /** Data */
    private CustomerInfoRespBean respData;
    /** ListView drawer list */
    @BindView(R.id.listView)
    protected ListView listView;

    private G01F00S02ListAdapter mAdapter;
    /**
     * Constructor
     */
    public G01F00S02Fragment() {
        // Required empty public constructor
//        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f00_s02, container, false);
        ButterKnife.bind(this, rootView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ConfigExtBean bean = respData.getList().get(i);
                Log.println(Log.ASSERT, "info", String.valueOf(i));

            }
        });

        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            this.parentActivity.showLoadingView(true);
            CustomerInfoRequest request = new CustomerInfoRequest(token, id) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        parseData(resp.getArrayData());
                        setDataForListView();
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
        respData = new G01F00S02Fragment.CustomerInfoRespBean(gsonObject);
    }

    /**
     * Set data for list view
     */
    private void setDataForListView() {
        mAdapter = new G01F00S02ListAdapter(parentActivity);
        int section = 0;
        for (ConfigExtBean item :
                respData.getList()) {
            mAdapter.addSectionHeaderItem(item, section);
            for (ConfigExtBean bean :
                    item.getDataExt()) {
                mAdapter.addItem(bean, section);
            }
            section++;
        }

//        int y = 1;
//        mAdapter.addSectionHeaderItem(new TransactionObject(("Header Item #" + y), 0));
//        y = 2;
//
//        for (int i = 1; i < 30; i++) {
//            mAdapter.addItem(new TransactionObject(("Row Item #" + i), i*5));
//            if (i % 4 == 0) {
//                mAdapter.addSectionHeaderItem(new TransactionObject(("Header Item #" + y), 0));
//                y++;
//            }
//        }

        listView.setAdapter(mAdapter);
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return "Thông tin bệnh nhân";
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

    /**
     * Customer information response
     */
    public class CustomerInfoRespBean extends BaseResponse {
        /** List information */
        private ArrayList<ConfigExtBean> list = new ArrayList<>();

        public CustomerInfoRespBean(JsonArray gsonObject) {
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
    }
}
