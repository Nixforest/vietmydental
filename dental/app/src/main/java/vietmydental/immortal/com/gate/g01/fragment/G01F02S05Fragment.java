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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.TreatmentUpdateRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F01S02ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F02S05Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listInfo)
    protected ListView listInfo;
    /** Current data */
    private ArrayList<ConfigExtBean> mData = new ArrayList<>();
    /** Current selected index */
    private int currentIndex = -1;
    /** Data */
    private G01F02S02Fragment.TreatmentInfoRespBean respData;
    /** Id of treatment */
    private String id = "";
    /** Id of pathological */
    private String pathologicalId = "";
    /** Id of diagnosis */
    private String diagnosisId = "";
    /** Adapter */
    private G01F01S02ListAdapter mAdapter;

    /**
     * Constructor
     */
    public G01F02S05Fragment() {
        // Required empty public constructor
    }

    // MARK: Override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f02_s05, container, false);
        ButterKnife.bind(this, rootView);
        setListInfo();
        handleListViewItemClick();
        return rootView;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        ConfigExtBean data = CommonProcess.getConfigExtObjById(respData.getList(), DomainConst.ITEM_HEALTHY);
        if (data != null) {
            return data.getName();
        }
        return DomainConst.BLANK;
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
        ArrayList<String> pathological = new ArrayList<>();
        for (ConfigBean bean :
                LoginBean.getInstance().getPathological()) {
            pathological.add(bean.getName());
        }
        String[] simpleArray = new String[pathological.size()];
        pathological.toArray(simpleArray);
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle("Thêm bệnh lý");
        builder.setItems(simpleArray, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ConfigBean selectedItem = LoginBean.getInstance().getPathological().get(i);
                ConfigExtBean data = new ConfigExtBean(selectedItem.getId(), selectedItem.getName());
                if (!isContain(data)) {
                    mData.add(data);
                    updateDataToServer();
                }
            }
        });
        builder.setNegativeButton("Huỷ", null);
        builder.show();
    }

    // MARK: Logic
    /**
     * Set data for screen
     * @param data Array config extent bean
     */
    public void setData(G01F02S02Fragment.TreatmentInfoRespBean data) {
        this.respData = data;
        ConfigExtBean healthy = CommonProcess.getConfigExtObjById(respData.getList(), DomainConst.ITEM_HEALTHY);
        if (healthy != null) {
            mData = healthy.getDataExt();
        }
    }

    /**
     * Set list view information data
     */
    public void setListInfo() {
        mAdapter = new G01F01S02ListAdapter(mData, parentActivity.getLayoutInflater());
        if (listInfo != null) {
            listInfo.setAdapter(mAdapter);
        }
    }

    /**
     * Handle when click on listview item
     */
    public void handleListViewItemClick() {
        if (listInfo != null) {
            listInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    currentIndex = i;
                    CommonProcess.ConfirmDialogCallback listener = new CommonProcess.ConfirmDialogCallback() {
                        @Override
                        public void onConfirmed() {
                            handleRemoveItem();
                        }
                    };
                    CommonProcess.showMessage(parentActivity,
                            DomainConst.CONTENT00162,
                            DomainConst.CONTENT00546, listener);
                }
            });
        }
    }

    /**
     * Handle remove item at index
     */
    public void handleRemoveItem() {
        mData.remove(currentIndex);
        updateDataToServer();
    }

    /**
     * Check if List data is contain data
     * @param data Data to check
     * @return True if found in List data, false otherwise
     */
    public boolean isContain(ConfigExtBean data) {
        for (ConfigExtBean bean :
                mData) {
            if (data.getId().equals(bean.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Update data to server
     */
    private void updateDataToServer() {
        String token = BaseModel.getInstance().getToken(parentActivity.getBaseContext());
        ArrayList<ConfigExtBean> data = respData.getList();
        ConfigExtBean healthy = CommonProcess.getConfigExtObjById(data, DomainConst.ITEM_HEALTHY);
        String status = CommonProcess.getConfigExtValueById(data, DomainConst.ITEM_STATUS);
        if (token != null && healthy != null) {
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
     * Set data
     * @param id Id of treatment
     * @param diagnosisId Id of diagnosis
     * @param pathologicalId Id of pathological
     */
    public void setData(String id, String diagnosisId, String pathologicalId) {
        this.id = id;
        this.diagnosisId = diagnosisId;
        this.pathologicalId = pathologicalId;
    }
}
