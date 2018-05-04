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
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.CustomerInfoRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F00S02ListAdapter;
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
    /** Visible data */
    private List<List<ConfigExtBean>> mData = new ArrayList<>();
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
                ConfigExtBean bean = getItem(i);
                switch (getSectionId(i)) {
                    case DomainConst.GROUP_MEDICAL_RECORD:          // Medical record group
                        if (isSectionHeader(i)) {
                            parentActivity.openG01F01S01(id);
                        } else {
                            switch (bean.getId()) {
                                case DomainConst.ITEM_MEDICAL_HISTORY:      // View medical history
                                    parentActivity.openG01F01S02(bean.getDataExt());
                                    break;
                                case DomainConst.ITEM_UPDATE_DATA:          // Update data
                                    parentActivity.openG01F01S01(id);
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    case DomainConst.GROUP_TREATMENT:               // Treatment group
                        if (isSectionHeader(i)) {
                            parentActivity.openG01F02S01(id);
                        } else {
                            switch (bean.getId()) {
                                case DomainConst.ITEM_UPDATE_DATA:          // Update data
                                    parentActivity.openG01F02S06(id);
                                    break;
                                default:
                                    parentActivity.openG01F02S02(bean.getId());
                                    break;
                            }
                        }
                        break;
                    default: break;
                }
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
        mData.clear();
        int section = 0;
        for (ConfigExtBean item :
                respData.getList()) {
            addSectionHeaderItem(item, section);
            for (ConfigExtBean bean :
                    item.getDataExt()) {
                addItem(bean, section);
            }
            section++;
        }

        listView.setAdapter(mAdapter);
    }

    /**
     * Add new section
     * @param item Section header data
     */
    private void addSectionHeaderItem(ConfigExtBean item, int section) {
        mData.add(new ArrayList<ConfigExtBean>());
        mData.get(section).add(item);
        mAdapter.addSectionHeaderItem(item, section);
    }

    /**
     * Add new item
     * @param item Item data
     */
    public void addItem(final ConfigExtBean item, int section) {
        mData.get(section).add(item);
        mAdapter.addItem(item, section);
    }

    /**
     * Get item data
     * @param position Position
     * @return ConfigExtBean
     */
    public ConfigExtBean getItem(int position) {
        int index = -1;
        for (List<ConfigExtBean> iterator :
                mData) {
            for (ConfigExtBean bean :
                    iterator) {
                index++;
                if (index == position) {
                    return bean;
                }
            }
        }
        return new ConfigExtBean();
    }

    /**
     * Get section id string from position
     * @param position Position
     * @return Value of section id
     */
    public String getSectionId(int position) {
        int index = -1;
        for (List<ConfigExtBean> iterator :
                mData) {
            for (ConfigExtBean bean :
                    iterator) {
                index++;
                if (index == position) {
                    return iterator.get(0).getId();
                }
            }
        }
        return "";
    }

    /**
     * Check if position is section header
     * @param position Position
     * @return True if position is first element of child array
     */
    public boolean isSectionHeader(int position) {
        int index = -1;
        for (List<ConfigExtBean> iterator :
                mData) {
            int childIdx = -1;
            for (ConfigExtBean bean :
                    iterator) {
                index++;
                childIdx++;
                if (index == position) {
                    if (childIdx == 0) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
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
