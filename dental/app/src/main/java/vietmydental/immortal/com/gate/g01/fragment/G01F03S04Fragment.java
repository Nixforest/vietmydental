package vietmydental.immortal.com.gate.g01.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.ReceiptCreateRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F02S06ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F03S04Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listView)
    protected ListView listView;
    /** Data */
    private ArrayList<ConfigExtBean> data = new ArrayList<>();
    /** Id of treatment schedule detail */
    private String id = "";
    /** List view adapter */
    private G01F02S06ListAdapter mAdapter;

    /**
     * Default constructor
     */
    public G01F03S04Fragment() {
        // Required empty public constructor
    }

    // MARK: Override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f03_s04, container, false);
        ButterKnife.bind(this, rootView);
        setDataForListView();
        handleListViewClick();
        return rootView;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.CONTENT00574;
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
    /**
     * Set data for listview
     */
    private void setDataForListView() {
        mAdapter = new G01F02S06ListAdapter(data, parentActivity.getLayoutInflater());
        if (listView != null) {
            listView.setAdapter(mAdapter);
        }
    }

    /**
     * Handle when click on listview item.
     */
    private void handleListViewClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ConfigExtBean bean = data.get(i);
                switch (bean.getId()) {
                    case DomainConst.ITEM_DISCOUNT:
                        updateDiscount();
                        break;
                    case DomainConst.ITEM_FINAL:
                        updateFinal();
                        break;
                    case DomainConst.ITEM_DESCRIPTION:
                        updateNote();
                        break;
                    default: break;
                }
            }
        });
    }

    /**
     * Update note value
     */
    private void updateNote() {
        ConfigExtBean bean = CommonProcess.getConfigExtObjById(data,
                DomainConst.ITEM_DESCRIPTION);
        if (bean != null) {
            final EditText input = new EditText(parentActivity);
            AlertDialog.Builder builder = CommonProcess.createInputTextAlert(parentActivity,
                    bean.getName(), bean.getDataStr(), input);
            // Setup the buttons
            builder.setPositiveButton(DomainConst.CONTENT00008, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (!input.getText().toString().isEmpty()) {
                        CommonProcess.setConfigExtDataStrById(data,
                                DomainConst.ITEM_DESCRIPTION, input.getText().toString());
                        mAdapter.notifyDataSetChanged();
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
    }

    /**
     * Update money item value
     * @param itemId Id of item
     */
    private void updateMoneyItem(final String itemId) {
        ConfigExtBean bean = CommonProcess.getConfigExtObjById(data, itemId);
        if (bean != null) {
            final EditText input = new EditText(parentActivity);
            AlertDialog.Builder builder = CommonProcess.createInputNumberAlert(parentActivity,
                    bean.getName(), bean.getDataStr(), input);
            // Setup the buttons
            builder.setPositiveButton(DomainConst.CONTENT00008, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (!input.getText().toString().isEmpty()) {
                        CommonProcess.setConfigExtDataStrById(data,
                                itemId, input.getText().toString());
                        mAdapter.notifyDataSetChanged();
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
    }

    /**
     * Update final value
     */
    private void updateFinal() {
        updateMoneyItem(DomainConst.ITEM_FINAL);
    }

    /**
     * Update discount value
     */
    private void updateDiscount() {
        updateMoneyItem(DomainConst.ITEM_DISCOUNT);
    }

    /**
     * Initialize data
     * @param amount Amount value
     * @param debt Debt value
     */
    public void initData(String amount, String debt) {
        data.add(new ConfigExtBean(DomainConst.ITEM_CHARACTERISTICS, DomainConst.CONTENT00570, amount));
        data.add(new ConfigExtBean(DomainConst.ITEM_CUSTOMER_DEBT, DomainConst.CONTENT00577, debt));
        data.add(new ConfigExtBean(DomainConst.ITEM_DISCOUNT, DomainConst.CONTENT00572, ""));
        data.add(new ConfigExtBean(DomainConst.ITEM_FINAL, DomainConst.CONTENT00573, ""));
        data.add(new ConfigExtBean(DomainConst.ITEM_DESCRIPTION, DomainConst.CONTENT00081, ""));
    }

    /**
     * Set data
     * @param id
     * @param amount
     * @param discount
     * @param finalAmount
     * @param description
     */
    public void setData(String id, String amount, String discount, String finalAmount, String description, String debt) {
        this.id = id;
        data.add(new ConfigExtBean(DomainConst.ITEM_CHARACTERISTICS, DomainConst.CONTENT00570, amount));
        data.add(new ConfigExtBean(DomainConst.ITEM_CUSTOMER_DEBT, DomainConst.CONTENT00577, debt));
        data.add(new ConfigExtBean(DomainConst.ITEM_DISCOUNT, DomainConst.CONTENT00572, discount));
        data.add(new ConfigExtBean(DomainConst.ITEM_FINAL, DomainConst.CONTENT00573, finalAmount));
        data.add(new ConfigExtBean(DomainConst.ITEM_DESCRIPTION, DomainConst.CONTENT00081, description));
    }

    /**
     * Set id detail
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void onFinishClick() {
        String token = BaseModel.getInstance().getToken(parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            ReceiptCreateRequest request = new ReceiptCreateRequest(
                    token, id, CommonProcess.getCurrentDate(DomainConst.DATE_TIME_FORMAT_2),
                    CommonProcess.getConfigExtValueById(data, DomainConst.ITEM_DISCOUNT),
                    CommonProcess.getConfigExtValueById(data, DomainConst.ITEM_FINAL),
                    "",
                    CommonProcess.getConfigExtValueById(data, DomainConst.ITEM_DESCRIPTION),
                    ""
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showMessage(parentActivity, DomainConst.CONTENT00162,
                                resp.getMessage(), new CommonProcess.ConfirmDialogCallback() {
                            @Override
                                    public void onConfirmed() {
                                parentActivity.onBackClick();
                            }
                                });
                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }
    }
}
