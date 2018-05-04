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
import vietmydental.immortal.com.gate.g01.api.TreatmentInfoRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F02S02ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F02S02Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listView)
    protected ListView listView;

    /** Id of treatment */
    private String id = "";
    /** Data */
    private TreatmentInfoRespBean respData;
    /** Visible data */
    private List<List<ConfigExtBean>> mData = new ArrayList<>();
    /** Adapter */
    private G01F02S02ListAdapter mAdapter;

    /**
     * Default constructor
     */
    public G01F02S02Fragment() {
        // Required empty public constructor
    }

    // MARK: Override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f02_s02, container, false);
        ButterKnife.bind(this, rootView);

        handleListViewClick();
        requestServer();

        return rootView;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return "Thông tin đợt điều trị";
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

    // MARK: Logic
    /**
     * Set id customer
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Request server
     */
    private void requestServer() {
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            this.parentActivity.showLoadingView(true);
            TreatmentInfoRequest request = new TreatmentInfoRequest(token, id) {
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
    }

    /**
     * Parse data from response.
     * @param data JSONObject data
     */
    private void parseData(JSONArray data) {
        JsonParser jsonParser = new JsonParser();
        JsonArray gsonObject = (JsonArray) jsonParser.parse(data.toString());
        respData = new TreatmentInfoRespBean(gsonObject);
    }

    /**
     * Set data for list view
     */
    private void setDataForListView() {
        mAdapter = new G01F02S02ListAdapter(parentActivity);
        mData.clear();
        int section = 0;
        ConfigExtBean firstSectionItem = new ConfigExtBean("0", "Thông tin");
        addSectionHeaderItem(firstSectionItem, section);
        for (ConfigExtBean item :
                respData.getList()) {
            if (item.getId().equals(DomainConst.ITEM_DETAILS)) {
                section++;
                addSectionHeaderItem(item, section);
                for (ConfigExtBean bean :
                        item.getDataExt()) {
                    addItem(bean, section);
                }
            } else {
                switch (item.getId()) {
                    case DomainConst.ITEM_HEALTHY:
                        addItem(item, section);
                        break;
                    case DomainConst.ITEM_END_DATE:
//                        addItem(item, section);
                        break;
                    case DomainConst.ITEM_CAN_UPDATE:
                    case DomainConst.ITEM_STATUS:
                    case DomainConst.ITEM_DIAGNOSIS_ID:
                    case DomainConst.ITEM_PATHOLOGICAL_ID:
                        break;
                    case DomainConst.ITEM_INSURANCE:
                        if (!item.getDataStr().isEmpty() && !item.getDataStr().equals("0")) {
                            addItem(item, section);
                        }
                        break;
                    default:
                        addItem(item, section);
                        break;
                }

            }
        }

        listView.setAdapter(mAdapter);
    }

    /**
     * Handle when click on listview item.
     */
    private void handleListViewClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ConfigExtBean bean = getItem(i);
                switch (getSectionId(i)) {
                    case "0":
                        if (!isSectionHeader(i)) {
                            switch (bean.getId()) {
                                case DomainConst.ITEM_DIAGNOSIS:
                                    if (canUpdate()) {
                                        updateDiagnosis();
                                    }
                                    break;
                                case DomainConst.ITEM_PATHOLOGICAL:
                                    if (canUpdate()) {
                                        updatePathological();
                                    }
                                    break;
                                case DomainConst.ITEM_HEALTHY:
                                    openHealthyInfo();
                                    break;
                                default: break;
                            }
                        }
                        break;
                    case DomainConst.ITEM_DETAILS:
                        if (isSectionHeader(i)) {
                            createNewTreatmentScheduleDetail();
                        } else {
                            openTreatmentScheduleDetail(bean);
                        }
                        break;
                    default: break;
                }
            }
        });
    }

    /**
     * Open treatment schedule detail screen
     * @param bean Data treatment schedule detail
     */
    private void openTreatmentScheduleDetail(ConfigExtBean bean) {
        parentActivity.openG01F03S01(bean.getId(), bean);
    }

    /**
     * Open create new treatment schedule detail screen.
     */
    private void createNewTreatmentScheduleDetail() {
        parentActivity.openG01F03S03(id);
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
     * Open healthy information screen
     */
    private void openHealthyInfo() {
    }

    /**
     * Check if model can update
     * @return True if value of can update item is "1", False otherwise
     */
    private boolean canUpdate() {
        return CommonProcess.getConfigExtValueById(respData.getList(), DomainConst.ITEM_CAN_UPDATE).equals("1");
    }

    /**
     * Update diagnosis
     */
    private void updateDiagnosis() {
    }

    /**
     * Update pathological
     */
    private void updatePathological() {
    }

    /**
     * Treatment information response bean
     */
    public class TreatmentInfoRespBean extends BaseResponse {
        /** List information */
        private ArrayList<ConfigExtBean> list = new ArrayList<>();

        public TreatmentInfoRespBean(JsonArray gsonObject) {
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
