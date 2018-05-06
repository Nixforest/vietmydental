package vietmydental.immortal.com.gate.g01.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.MedicalRecordUpdateRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F01S02ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F01S02Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listInfo)
    protected ListView listInfo;
    /** Current data */
    private ArrayList<ConfigExtBean> mData = new ArrayList<>();
    /** Id of customer */
    private String id = "";
    /** Record number */
    private String recordNumber = "";
    /** Adapter */
    private G01F01S02ListAdapter mAdapter;
    /**
     * Constructor
     */
    public G01F01S02Fragment() {
        // Required empty public constructor
    }

    // MARK: Override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f01_s02, container, false);
        ButterKnife.bind(this, rootView);
        setListInfo();
        return rootView;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return "Tiền sử bệnh";
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
        String[] simpleArray = new String[ pathological.size() ];
        pathological.toArray(simpleArray);
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle("Thêm tiền sử bệnh");
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
     * @param mData Array config extent bean
     */
    public void setData(ArrayList<ConfigExtBean> mData) {
        this.mData = mData;
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

                }
            });
        }
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
        List<String> arrData = new ArrayList<>();
        for (ConfigExtBean bean :
                mData) {
            arrData.add(bean.getId());
        }
        String medicalHistory = "[" + TextUtils.join(",", arrData) + "]";
        String token = BaseModel.getInstance().getToken(parentActivity.getBaseContext());
        if (token != null) {
            MedicalRecordUpdateRequest request = new MedicalRecordUpdateRequest(
                token, id, recordNumber, mData
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

    public void setId(String id) {
        this.id = id;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }
}
