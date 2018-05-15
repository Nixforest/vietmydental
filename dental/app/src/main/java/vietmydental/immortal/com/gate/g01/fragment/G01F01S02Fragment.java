package vietmydental.immortal.com.gate.g01.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
import butterknife.OnClick;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.SimpleSearchFilter;
import ir.mirrajabi.searchdialog.adapters.SearchDialogAdapter;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.FilterResultListener;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.MedicalRecordUpdateRequest;
import vietmydental.immortal.com.gate.g01.api.PathologicalCreateRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F01S02ListAdapter;
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
    /** Current selected index */
    private int currentIndex = -1;
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
//        handleAddItem();
        handleAddItemWithSearchView(DomainConst.CONTENT00548);
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

    /**
     * Set id value
     * @param id Id value
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Set record number
     * @param recordNumber Record number value
     */
    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    /**
     * Handle add item
     */
    private void handleAddItem() {
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

    /**
     * Handle add item with search dialog
     * @param title Title of dialog
     */
    private void handleAddItemWithSearchView(String title) {
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
                } else if (!isContain(data)) {
                    mData.add(data);
                    baseSearchDialogCompat.dismiss();
                    updateDataToServer();
                } else {
                    baseSearchDialogCompat.dismiss();
                }
            }
        });
        dialog.show();
    }

    /**
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
                                        mData.add(respBean.getBean());
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
}
