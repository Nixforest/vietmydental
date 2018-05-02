package vietmydental.immortal.com.gate.g01.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.CustomerListRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F00S01ListAdapter;
import vietmydental.immortal.com.gate.g01.model.CustomerBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ListBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F00S01Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listCustomer)
    protected ListView listCustomer;
    /** Current page */
    private int page = 0;
    /** Data */
    private CustomerListRespBean respData;

    /**
     * Constructor.
     */
    public G01F00S01Fragment() {
        // Required empty public constructor
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return "Danh sách Bệnh nhân";
    }

    /**
     * Get title config to show on menu
     *
     * @return
     */
    @Override
    public TitleConfigObject getTitleConfig() {
        return new BaseFragment.TitleConfigObject(true, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ButterKnife.bind(this.parentActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f00_s01, container, false);
        ButterKnife.bind(this, rootView);

        listCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = respData.getList().get(i).getId();
                parentActivity.openG01F00S02(id);
            }
        });

        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        String date = "2018/05/02";
        if (token != null) {
            this.parentActivity.showLoadingView(true);
            CustomerListRequest request = new CustomerListRequest(
                    token, String.valueOf(page), "0", date, date
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        parseData(resp.getData());
                        if (listCustomer != null) {
                            listCustomer.setAdapter(new G01F00S01ListAdapter(respData.getList(), parentActivity.getLayoutInflater()));
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

    // MARK: Event handler
    /**
     * Handle click on menu button
     */
//    @OnClick(R.id.btnTest)
//    public void onMenuClick(){
//        this.parentActivity.openG01F00S02();
//    }

    // MARK: Logic
    private void parseData(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(data.toString());
        respData = new CustomerListRespBean(gsonObject);
    }

    /**
     * Customer list response bean
     */
    public class CustomerListRespBean extends ListBean {
        /** List customer */
        private ArrayList<CustomerBean> list = new ArrayList<>();

        /**
         * Constructor
         *
         * @param record Record number
         * @param page   Page number
         */
        public CustomerListRespBean(int record, int page) {
            super(record, page);
        }

        /**
         * Constructor
         *
         * @param gsonObject JsonObject data
         */
        public CustomerListRespBean(JsonObject gsonObject) {
            super(gsonObject);
            try {
                JsonArray array = gsonObject.getAsJsonArray(DomainConst.KEY_LIST);
                Gson gson = new Gson();
                list = gson.fromJson(array.toString(), new TypeToken<ArrayList<CustomerBean>>(){}.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Get list customer.
         * @return List customer
         */
        public ArrayList<CustomerBean> getList() {
            return list;
        }
    }
}
