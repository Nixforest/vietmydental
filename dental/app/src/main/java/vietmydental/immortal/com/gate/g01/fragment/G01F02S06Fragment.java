package vietmydental.immortal.com.gate.g01.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.TreatmentCreateRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F02S06ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F02S06Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listView)
    protected ListView listView;
    /** Data */
    private ArrayList<ConfigExtBean> data = new ArrayList<>();
    /** Id of customer */
    private String id = "";
    /** Time id */
    private String timeId = "";
    /** Date value */
    private Date date = new Date();
    /** Flag is first run */
    private boolean isFirstTime = true;
    /** Adapter */
    private G01F02S06ListAdapter mAdapter;

    /**
     * Default constructor
     */
    public G01F02S06Fragment() {
        // Required empty public constructor
    }

    // MARK: Override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f02_s06, container, false);
        ButterKnife.bind(this, rootView);
        setDataForListView();
        handleListViewClick();
        // Auto run update data method
        if (isFirstTime) {
            updateTime(data.get(0).getName());
        }
        return rootView;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return "Tạo mới đợt điều trị";
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
     * Set data
     * @param data Data to set
     */
    public void setData(ArrayList<ConfigExtBean> data) {
        this.data = data;
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
        if (listView != null) {
              listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                  @Override
                  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                      ConfigExtBean bean = data.get(i);
                      switch (bean.getId()) {
                          case DomainConst.ITEM_TIME_ID:
                              updateTime(bean.getName());
                              break;
                          case DomainConst.ITEM_START_DATE:
                              updateDate(bean.getName());
                              break;
                          case DomainConst.ITEM_NOTE:
                              updateWorkDetail(bean);
                              break;
                          default:
                              break;
                      }
                  }
              });
        }
    }

    /**
     * Initialize data
     */
    public void initData() {
        data.add(new ConfigExtBean(DomainConst.ITEM_TIME_ID, DomainConst.CONTENT00562, ""));
        data.add(new ConfigExtBean(DomainConst.ITEM_START_DATE, DomainConst.CONTENT00563,
                CommonProcess.getDateString(date, DomainConst.DATE_TIME_FORMAT_1)));
//        data.add(new ConfigExtBean(DomainConst.ITEM_TYPE, DomainConst.CONTENT00564, ""));
        data.add(new ConfigExtBean(DomainConst.ITEM_NOTE, DomainConst.CONTENT00565, ""));
    }

    /**
     * Set id customer
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void onFinishClick() {
        CommonProcess.ConfirmDialogCallback listener = new CommonProcess.ConfirmDialogCallback() {
            @Override
            public void onConfirmed() {
                requestCreate();
            }
        };
        CommonProcess.showMessage(parentActivity, DomainConst.CONTENT00162,
                DomainConst.CONTENT00578, listener);
    }

    /**
     * Request create treatment schedule to server
     */
    private void requestCreate() {
        String token = BaseModel.getInstance().getToken(parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            TreatmentCreateRequest request = new TreatmentCreateRequest(
                    token, id, timeId,
                    CommonProcess.getDateString(date, DomainConst.DATE_TIME_FORMAT_2),
                    LoginBean.getInstance().getId(),
                    CommonProcess.getConfigExtValueById(data, DomainConst.ITEM_TYPE),
                    CommonProcess.getConfigExtValueById(data, DomainConst.ITEM_NOTE)) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        parentActivity.onBackClick();
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
     * Update timer
     * @param title: Tile of dialog selection
     */
    private void updateTime(String title) {
        final ArrayList<String> listTime = new ArrayList<>();
        for (ConfigBean bean : LoginBean.getInstance().getTimer()) {
            listTime.add(bean.getName());
        }
        String[] arrTime = new String[listTime.size()];
        listTime.toArray(arrTime);
        DialogInterface.OnClickListener listenter = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ConfigBean selectedItem = LoginBean.getInstance().getTimer().get(i);
                CommonProcess.setConfigExtDataStrById(data, DomainConst.ITEM_TIME_ID, selectedItem.getName());
                timeId = selectedItem.getId();
                mAdapter.notifyDataSetChanged();
                // Auto run update data methods
                if (isFirstTime) {
                    updateDate(data.get(1).getName());
                }
            }
        };
        CommonProcess.showSelectionAlert(parentActivity, title, arrTime, listenter);
    }

    /**
     * Update date
     * @param title Title of selection dialog
     */
    private void updateDate(String title) {
        // Convert date to calendar object
        final Calendar calendar = CommonProcess.toCalendar(date);

        // Callback after select date on date picker
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                // Update value of calendar object
                calendar.set(year, monthOfYear, dayOfMonth);
                // Update date object
                date = calendar.getTime();
                // Update item value
                CommonProcess.setConfigExtDataStrById(data, DomainConst.ITEM_START_DATE,
                        CommonProcess.getDateString(date, DomainConst.DATE_TIME_FORMAT_1));
                // Reload data
                mAdapter.notifyDataSetChanged();
                // Auto run update data methods
                if (isFirstTime) {
                    updateWorkDetail(data.get(2));
                    isFirstTime = false;
                }
            }
        };
        // Create dialog
        DatePickerDialog dialog = new DatePickerDialog(parentActivity, callback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE));
        // Set min date
        dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        // Set title
        dialog.setTitle(title);
        dialog.show();
    }

    /**
     * Up working detail
     * @param bean Object data
     */
    private void updateWorkDetail(final ConfigExtBean bean) {
        final EditText editText = new EditText(parentActivity);
        AlertDialog.Builder builder = CommonProcess.createInputTextAlert(
                parentActivity, bean.getName(), bean.getDataStr(), editText);
        builder.setPositiveButton(DomainConst.CONTENT00008, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!editText.getText().toString().isEmpty()) {
                    CommonProcess.setConfigExtDataStrById(data, DomainConst.ITEM_NOTE, editText.getText().toString());
                    mAdapter.notifyDataSetChanged();
                } else {
                    updateWorkDetail(bean);
                }
            }
        }).setNegativeButton(DomainConst.CONTENT00009, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
    }
}
