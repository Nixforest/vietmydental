package vietmydental.immortal.com.gate.g01.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.SimpleSearchFilter;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.PathologicalCreateRequest;
import vietmydental.immortal.com.gate.g01.api.TreatmentInfoRequest;
import vietmydental.immortal.com.gate.g01.api.TreatmentUpdateRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F02S02ListAdapter;
import vietmydental.immortal.com.gate.g01.model.PathologicalCreateRespBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
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
    /** Id of pathological */
    private String pathologicalId = "";
    /** Id of diagnosis */
    private String diagnosisId = "";
    /** Data */
    private TreatmentInfoRespBean respData;
    /** Visible data */
    private List<ArrayList<ConfigExtBean>> mData = new ArrayList<>();
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
     * Update data to server
     */
    private void updateDataToServer() {
        String token = BaseModel.getInstance().getToken(parentActivity.getBaseContext());
        ArrayList<ConfigExtBean> data = respData.getList();
        ConfigExtBean healthy = CommonProcess.getConfigExtObjById(data, DomainConst.ITEM_HEALTHY);
        String status = CommonProcess.getConfigExtValueById(data, DomainConst.ITEM_STATUS);
        if (token != null && healthy != null && status != null) {
            TreatmentUpdateRequest request = new TreatmentUpdateRequest(
                    token, id, diagnosisId, pathologicalId, healthy.getDataExt(), status
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        mAdapter.notifyDataSetChanged();
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
        ArrayList<ConfigExtBean> listData = respData.getList();
        ConfigExtBean diagnosis = CommonProcess.getConfigExtObjById(listData, DomainConst.ITEM_DIAGNOSIS_ID);
        if (diagnosis != null) {
            diagnosisId = diagnosis.getDataStr();
        }
        ConfigExtBean pathological = CommonProcess.getConfigExtObjById(listData, DomainConst.ITEM_PATHOLOGICAL_ID);
        if (pathological != null) {
            pathologicalId = pathological.getDataStr();
        }
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
                    case DomainConst.ITEM_DIAGNOSIS:
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
                                        updateDiagnosis(bean.getName());
                                    }
                                    break;
                                case DomainConst.ITEM_PATHOLOGICAL:
                                    if (canUpdate()) {
//                                        updatePathological(bean.getName());
                                        updatePathologicalWithSearchView(bean.getName());
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
        parentActivity.openG01F03S01(bean);
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
        parentActivity.openG01F02S05(id, respData, diagnosisId, pathologicalId);
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
     * @param title Title of alert
     */
    private void updateDiagnosis(String title) {
        final ArrayList<String> diagnosis = new ArrayList<>();
        for (ConfigExtBean bean :
                LoginBean.getInstance().getDiagnosis()) {
            diagnosis.add(bean.getName());
            if (!bean.getDataExt().isEmpty()) {
                for (ConfigExtBean child :
                        bean.getDataExt()) {
                    diagnosis.add(child.getName());
                }
            }
        }
        String[] simpleArray = new String[ diagnosis.size() ];
        diagnosis.toArray(simpleArray);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                ConfigBean selectedItem = LoginBean.getInstance().getPathological().get(i);
//                CommonProcess.setConfigExtDataStrById(mData.get(0), DomainConst.ITEM_PATHOLOGICAL, selectedItem.getName());
//                pathologicalId = selectedItem.getId();
//                mAdapter.notifyDataSetChanged();
            }
        };
//        CommonProcess.showSelectionAlert(parentActivity, title, simpleArray, listener);
        new SimpleSearchDialogCompat(parentActivity, title,
                "Tìm kiếm", null, LoginBean.getInstance().getDiagnosis(),
                new SearchResultListener<ConfigExtBean>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           ConfigExtBean item, int position) {
                        String[] arrValue = item.getName().split("-");
                        if (arrValue.length == 3) {
                            CommonProcess.setConfigExtDataStrById(mData.get(0), DomainConst.ITEM_DIAGNOSIS, arrValue[1]);
                        } else {
                            CommonProcess.setConfigExtDataStrById(mData.get(0), DomainConst.ITEM_DIAGNOSIS, item.getName());
                        }
                        diagnosisId = item.getId();
                        dialog.dismiss();
                        updateDataToServer();
                    }
                }).show();
    }

    /**
     * Update pathological
     * @param title Title of alert
     */
    private void updatePathological(String title) {
        final ArrayList<String> pathological = new ArrayList<>();
        for (ConfigBean bean :
                LoginBean.getInstance().getPathological()) {
            pathological.add(bean.getName());
        }
        String[] simpleArray = new String[ pathological.size() ];
        pathological.toArray(simpleArray);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ConfigBean selectedItem = LoginBean.getInstance().getPathological().get(i);
                CommonProcess.setConfigExtDataStrById(mData.get(0), DomainConst.ITEM_PATHOLOGICAL, selectedItem.getName());
                pathologicalId = selectedItem.getId();
                updateDataToServer();
            }
        };
        CommonProcess.showSelectionAlert(parentActivity, title, simpleArray, listener);
    }

    /**
     * Update pathological with search dialog
     * @param title Title of alert
     */
    private void updatePathologicalWithSearchView(String title) {
        final SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(parentActivity, title, DomainConst.CONTENT00287, null,
                LoginBean.getInstance().getPathological(), new SearchResultListener<ConfigBean>() {
            @Override
            public void onSelected(final BaseSearchDialogCompat baseSearchDialogCompat, ConfigBean item, int i) {
                // Get selected item
                ConfigExtBean data = new ConfigExtBean(item.getId(), item.getName());
                // Check if current selected item is Create new item
                if (item.getId().equals(DomainConst.ITEM_CREATE_NEW)) {
                    // Get text from searchbox
                    String newPathological = ((SimpleSearchDialogCompat)baseSearchDialogCompat).getSearchBox().getText().toString();
                    // Normalization
                    newPathological = CommonProcess.upperCaseAllFirst(newPathological);
                    final String finalNewPathological = newPathological;
                    CommonProcess.showMessage(parentActivity, DomainConst.CONTENT00162,
                            "Bạn có chắc chắn muốn tạo mới Bệnh lý " + newPathological + " không?",
                            new CommonProcess.ConfirmDialogCallback() {
                                @Override
                                public void onConfirmed() {
                                    requestCreateNewPathological(finalNewPathological, baseSearchDialogCompat);
                                }
                            });
                } else {
                    CommonProcess.setConfigExtDataStrById(mData.get(0), DomainConst.ITEM_PATHOLOGICAL, data.getName());
                    pathologicalId = data.getId();
                    mAdapter.notifyDataSetChanged();
                    baseSearchDialogCompat.dismiss();
                }
            }
        });
        dialog.show();
    }/**
     * Request create new pathological
     * @param value New value
     * @param baseSearchDialogCompat Current dialog
     */
    private void requestCreateNewPathological(String value, final BaseSearchDialogCompat baseSearchDialogCompat) {
        String token = BaseModel.getInstance().getToken(parentActivity);
        if (token != null) {
            PathologicalCreateRequest request = new PathologicalCreateRequest(
                    token, value, value) {
                @Override
                protected void onPostExecute(Object o) {
                    final BaseResponse resp = getResponse();
                    // Request success
                    if ((resp != null) && resp.isSuccess()) {
                        // Get response data
                        final PathologicalCreateRespBean respBean = parseData(resp.getData());
                        // Save to login data
                        LoginBean.getInstance().getPathological().add(respBean.getBean());

                        CommonProcess.showMessage(parentActivity, DomainConst.CONTENT00162,
                                resp.getMessage(), new CommonProcess.ConfirmDialogCallback() {
                                    @Override
                                    public void onConfirmed() {
                                        // Add to current data
                                        CommonProcess.setConfigExtDataStrById(mData.get(0), DomainConst.ITEM_PATHOLOGICAL, respBean.getBean().getName());
                                        pathologicalId = respBean.getBean().getId();
                                        // Reload load list view data
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                        baseSearchDialogCompat.dismiss();
                        mAdapter.notifyDataSetChanged();
                    } else {
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }
    }

    private PathologicalCreateRespBean parseData(JSONObject data) {
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObj = (JsonObject) jsonParser.parse(data.toString());
        PathologicalCreateRespBean retVal = new PathologicalCreateRespBean(gsonObj);
        return retVal;
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
