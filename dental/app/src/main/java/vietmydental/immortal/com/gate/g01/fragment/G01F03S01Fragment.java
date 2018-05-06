package vietmydental.immortal.com.gate.g01.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.TreatmentCreateRequest;
import vietmydental.immortal.com.gate.g01.api.TreatmentInfoRequest;
import vietmydental.immortal.com.gate.g01.api.TreatmentScheduleDetailUpdateRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F02S02ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F03S01Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listView)
    protected ListView listView;

    /** Id of treatment schedule detail */
    private String id = "";
    /** Teeth id */
    private String teethId = "";
    /** Diagnosis id */
    private String diagnosisId = "";
    /** Treatment */
    private String treatmentId = "";
    /** Data */
    private ConfigExtBean detailData = new ConfigExtBean();
    /** Visible data */
    private List<ArrayList<ConfigExtBean>> mData = new ArrayList<>();
    /** Adapter */
    private G01F02S02ListAdapter mAdapter;
    /** Action receipt */
    @BindView(R.id.btnAdd)
    protected FloatingActionButton actionButton;

    /**
     * Default constructor
     */
    public G01F03S01Fragment() {
        // Required empty public constructor
    }

    // MARK: Override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f03_s01, container, false);
        ButterKnife.bind(this, rootView);

        handleListViewClick();
        setDataForListView();
        updateActionButton();
        return rootView;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return detailData.getId();
    }

    /**
     * Get title config to show on menu
     *
     * @return
     */
    @Override
    public TitleConfigObject getTitleConfig() {
        return new BaseFragment.TitleConfigObject(false, true, true);
    }

    @Override
    public void onFinishClick() {
        CommonProcess.ConfirmDialogCallback listener = new CommonProcess.ConfirmDialogCallback() {
            @Override
            public void onConfirmed() {
                handleFinish();
            }
        };
        CommonProcess.showMessage(parentActivity,
                DomainConst.CONTENT00162,
                DomainConst.CONTENT00561, listener);
    }

    // MARK: Logic
    /**
     * Update action button
     */
    private void updateActionButton() {
        if (CommonProcess.getConfigExtValueById(detailData.getDataExt(), DomainConst.ITEM_STATUS).equals(DomainConst.TREATMENT_SCHEDULE_DETAIL_COMPLETED)) {
            actionButton.setVisibility(View.VISIBLE);
        } else {
            actionButton.setVisibility(View.GONE);
        }
    }
    /**
     * Handle finish schedule detail
     */
    private void handleFinish() {
        CommonProcess.setConfigExtDataStrById(detailData.getDataExt(), DomainConst.ITEM_STATUS, DomainConst.TREATMENT_SCHEDULE_DETAIL_COMPLETED);
        handleUpdate(DomainConst.TREATMENT_SCHEDULE_DETAIL_COMPLETED);
    }

    /**
     * Handle finish schedule detail
     */
    private void handleUpdate(final String status) {
        updateActionButton();
        String token = BaseModel.getInstance().getToken(parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            TreatmentScheduleDetailUpdateRequest request = new TreatmentScheduleDetailUpdateRequest(
                    token,
                    CommonProcess.getConfigExtValueById(detailData.getDataExt(), DomainConst.ITEM_ID),
                    teethId, diagnosisId, treatmentId, status
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        if (status.equals(DomainConst.TREATMENT_SCHEDULE_DETAIL_COMPLETED)) {
                            parentActivity.onBackClick();
                        }
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
     * Set id customer
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Initialize data
     */
    public void setData(ConfigExtBean data) {
        this.detailData = data;
        this.id = data.getId();
        this.teethId = CommonProcess.getConfigExtValueById(detailData.getDataExt(), DomainConst.ITEM_TEETH_ID);
        this.diagnosisId = CommonProcess.getConfigExtValueById(detailData.getDataExt(), DomainConst.ITEM_DIAGNOSIS_ID);
        this.treatmentId = CommonProcess.getConfigExtValueById(detailData.getDataExt(), DomainConst.ITEM_TREATMENT_TYPE_ID);
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
                detailData.getDataExt()) {
            if (item.getId().equals(DomainConst.ITEM_DETAILS)) {
                section++;
                addSectionHeaderItem(item, section);
                for (ConfigExtBean bean :
                        item.getDataExt()) {
                    if (!bean.getId().isEmpty()) {
                        addItem(bean, section);
                    }
                }
            } else if (!item.getId().equals(DomainConst.ITEM_RECEIPT)) {
                switch (item.getId()) {
                    case DomainConst.ITEM_HEALTHY:
                        addItem(item, section);
                        break;
                    case DomainConst.ITEM_END_DATE:
//                        addItem(item, section);
                        break;
                    case DomainConst.ITEM_NOTE:
                        if (!item.getDataStr().isEmpty()) {
                            addItem(item, section);
                        }
                        break;
                    case DomainConst.ITEM_CAN_UPDATE:
                    case DomainConst.ITEM_STATUS:
                    case DomainConst.ITEM_DIAGNOSIS_ID:
                    case DomainConst.ITEM_PATHOLOGICAL_ID:
                    case DomainConst.ITEM_ID:
                    case DomainConst.ITEM_TIME_ID:
                    case DomainConst.ITEM_TIME:
                    case DomainConst.ITEM_TEETH_ID:
                    case DomainConst.ITEM_TREATMENT_TYPE_ID:
                    case DomainConst.ITEM_START_DATE:
                    case DomainConst.ITEM_TYPE:
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
                                case DomainConst.ITEM_TEETH:
                                    if (canUpdate()) {
                                        updateTeeth(bean.getName());
                                    }
                                    break;
                                case DomainConst.ITEM_TREATMENT:
                                    updateTreatment(bean.getName());
                                    break;
                                default: break;
                            }
                        }
                        break;
                    case DomainConst.ITEM_DETAILS:
                        if (isSectionHeader(i)) {
//                            createNewTreatmentScheduleDetail();
                        } else {
//                            openTreatmentScheduleDetail(bean.getId());
                        }
                        break;
                    default: break;
                }
            }
        });
    }

    /**
     * Update treatment
     * @param title Title of alert
     */
    private void updateTreatment(String title) {
        ArrayList<ConfigExtBean> listTreatment = new ArrayList<>();

        final ArrayList<String> treatment = new ArrayList<>();
        for (ConfigExtBean bean :
                LoginBean.getInstance().getTreatment()) {
//            treatment.add(bean.getName());
            for (ConfigExtBean child :
                    bean.getDataExt()) {
                String name = child.getName() + " - " + child.getDataStr();
                treatment.add(name);

                listTreatment.add(new ConfigExtBean(child.getId(), name));
            }
        }
        String[] simpleArray = new String[ treatment.size() ];
        treatment.toArray(simpleArray);
        new SimpleSearchDialogCompat(parentActivity, title,
                "Tìm kiếm", null, listTreatment,
                new SearchResultListener<ConfigExtBean>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           ConfigExtBean item, int position) {
                        CommonProcess.setConfigExtDataStrById(mData.get(0), DomainConst.ITEM_TREATMENT, item.getName());
                        treatmentId = item.getId();
                        dialog.dismiss();
                        updateDataToServer();
                    }
                }).show();
    }

    /**
     * Update teeth
     * @param title Title of alert
     */
    private void updateTeeth(String title) {
        final ArrayList<String> teeth = new ArrayList<>();
        for (ConfigExtBean bean :
                LoginBean.getInstance().getTooth()) {
            teeth.add(bean.getName());
        }
        String[] simpleArray = new String[ teeth.size() ];
        teeth.toArray(simpleArray);
        new SimpleSearchDialogCompat(parentActivity, title,
                "Tìm kiếm", null, LoginBean.getInstance().getTooth(),
                new SearchResultListener<ConfigExtBean>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           ConfigExtBean item, int position) {
                        CommonProcess.setConfigExtDataStrById(mData.get(0), DomainConst.ITEM_TEETH, item.getName());
                        teethId = item.getId();
                        dialog.dismiss();
                        updateDataToServer();
                    }
                }).show();
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
     * Update data to server
     */
    private void updateDataToServer() {
        handleUpdate(CommonProcess.getConfigExtValueById(this.detailData.getDataExt(), DomainConst.ITEM_STATUS));
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
     * Check if model can update
     * @return True if value of can update item is "1", False otherwise
     */
    private boolean canUpdate() {
        return CommonProcess.getConfigExtValueById(detailData.getDataExt(), DomainConst.ITEM_CAN_UPDATE).equals("1");
    }

    // MARK: Event handler
    /**
     * Handle click on add button
     */
    @OnClick(R.id.btnAdd)
    public void btnAddClick() {
        ConfigExtBean bean = LoginBean.getInstance().getTreatment(treatmentId);
        ConfigExtBean receipt = CommonProcess.getConfigExtObjById(detailData.getDataExt(), DomainConst.ITEM_RECEIPT);
        if (bean != null && receipt != null) {
            if (receipt == null) {
                parentActivity.openG01F03S04(CommonProcess.getConfigExtValueById(detailData.getDataExt(), DomainConst.ITEM_ID), bean.getDataStr());
            } else {
                parentActivity.openG01F03S04(CommonProcess.getConfigExtValueById(detailData.getDataExt(), DomainConst.ITEM_ID), bean.getDataStr(),
                        CommonProcess.getConfigExtValueById(receipt.getDataExt(), DomainConst.ITEM_DISCOUNT),
                        CommonProcess.getConfigExtValueById(receipt.getDataExt(), DomainConst.ITEM_FINAL),
                        CommonProcess.getConfigExtValueById(receipt.getDataExt(), DomainConst.ITEM_DESCRIPTION));
            }
        }
    }
}
