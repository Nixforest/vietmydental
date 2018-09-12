package vietmydental.immortal.com.gate.g01.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
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
import vietmydental.immortal.com.gate.g01.api.MedicalRecordInfoRequest;
import vietmydental.immortal.com.gate.g01.api.MedicalRecordUpdateRequest;
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
    /** Adapter */
    private G01F01S01ListAdapter mAdapter;

    /**
     * Constructor
     */
    public G01F01S01Fragment() {
        // Required empty public constructor
    }

    // MARK: Override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f01_s01, container, false);
        ButterKnife.bind(this, rootView);

        requestData();
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

    // MARK: Logic
    /**
     * Set id
     * @param id Id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Request data from server
     */
    private void requestData() {
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
                        setListInfo();
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
     * Set list view information data
     */
    public void setListInfo() {
        mAdapter = new G01F01S01ListAdapter(respData.getVisibleList(), parentActivity.getLayoutInflater());
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
                    try {
                        ArrayList<ConfigExtBean> listData = respData.getVisibleList();
                        ConfigExtBean data = listData.get(i);
                        switch (data.getId()) {
                            case DomainConst.ITEM_MEDICAL_HISTORY:
                                ConfigExtBean recordNumberObj = CommonProcess.getConfigExtObjById(listData,
                                        DomainConst.ITEM_RECORD_NUMBER);
                                if (recordNumberObj != null) {
                                    parentActivity.openG01F01S02(data.getDataExt(),
                                            id, recordNumberObj.getName());
                                }
                                break;
                            case DomainConst.ITEM_RECORD_NUMBER:
                                updateRecordNumber();
                                break;
                            default: break;
                        }
                    } catch (Exception e) {
                        // Do nothing
                    }
//                    currentIndex = i;
//                    CommonProcess.ConfirmDialogCallback listener = new CommonProcess.ConfirmDialogCallback() {
//                        @Override
//                        public void onConfirmed() {
//                            handleRemoveItem();
//                        }
//                    };
//                    CommonProcess.showMessage(parentActivity,
//                            DomainConst.CONTENT00162,
//                            DomainConst.CONTENT00546, listener);
                }
            });
        }
    }

    /**
     * Update record number
     */
    private void updateRecordNumber() {
        ConfigExtBean recordNumberObj = CommonProcess.getConfigExtObjById(respData.getVisibleList(),
                DomainConst.ITEM_RECORD_NUMBER);
        String value = "";
        if (recordNumberObj != null) {
            value = recordNumberObj.getName();
        }
        final EditText input = new EditText(parentActivity);
        AlertDialog.Builder builder = CommonProcess.createInputTextAlert(parentActivity,
                DomainConst.CONTENT00549, value, input);
        // Setup the buttons
        builder.setPositiveButton(DomainConst.CONTENT00008, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!input.getText().toString().isEmpty()) {
                    CommonProcess.setConfigExtNameById(respData.getVisibleList(),
                            DomainConst.ITEM_RECORD_NUMBER, input.getText().toString());
                    updateDataToServer();
                }
            }
        });
        builder.setNegativeButton(DomainConst.CONTENT00009, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * Update data to server
     */
    private void updateDataToServer() {
        String token = BaseModel.getInstance().getToken(parentActivity.getBaseContext());
        ConfigExtBean recordNumberObj = CommonProcess.getConfigExtObjById(respData.getVisibleList(),
                DomainConst.ITEM_RECORD_NUMBER);
        ConfigExtBean medicalHistoryObj = CommonProcess.getConfigExtObjById(respData.getVisibleList(),
                DomainConst.ITEM_MEDICAL_HISTORY);
        if (token != null && recordNumberObj != null && medicalHistoryObj != null) {
            MedicalRecordUpdateRequest request = new MedicalRecordUpdateRequest(
                    token, id,
                    recordNumberObj.getName(),
                    medicalHistoryObj.getDataExt()
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
        respData = new MedicalHistoryRespBean(gsonObject);
    }

    /**
     * Response class
     */
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
