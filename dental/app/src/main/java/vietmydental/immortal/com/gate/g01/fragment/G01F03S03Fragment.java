package vietmydental.immortal.com.gate.g01.fragment;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.TreatmentScheduleDetailCreateRequest;
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
public class G01F03S03Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listView)
    protected ListView listView;
    /** Data */
    private ArrayList<ConfigExtBean> data = new ArrayList<>();
    /** Id of schedule */
    private String id = "";
    /** Time id */
    private String timeId = "";
    /** Date value */
    private Date date = new Date();
    /** Teeth id */
    private String teethId = "";
    /** Diagnosis id */
    private String diagnosisId = "";
    /** Treatment id */
    private String treatmentId = "";
    /** Flag is first run */
    private boolean isFirstTime = true;
    /** Adapter */
    private G01F02S06ListAdapter mAdapter;

    /**
     * Default constructor
     */
    public G01F03S03Fragment() {
        // Required empty public constructor
    }

    // MARK: Override methods
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f03_s03, container, false);
        ButterKnife.bind(this, rootView);
        handleListViewClick();
        setDataForListView();
        return rootView;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.CONTENT00557;
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
        String token = BaseModel.getInstance().getToken(parentActivity.getBaseContext());
        ConfigExtBean teethInfo = CommonProcess.getConfigExtObjById(data, DomainConst.ITEM_TEETH_INFO);
        if (token != null && (teethInfo != null)) {
            parentActivity.showLoadingView(true);
            TreatmentScheduleDetailCreateRequest request = new TreatmentScheduleDetailCreateRequest(
                    token, id, timeId,
                    CommonProcess.getDateString(date, DomainConst.DATE_TIME_FORMAT_2),
                    teethId,
                    diagnosisId, treatmentId, teethInfo.getDataExt()) {
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

    // MARK: Logic
    /**
     * Initialize data
     */
    public void initData() {
        data.add(new ConfigExtBean(DomainConst.ITEM_TIME_ID, DomainConst.CONTENT00562, ""));
        data.add(new ConfigExtBean(DomainConst.ITEM_START_DATE, DomainConst.CONTENT00563, ""));
        data.add(new ConfigExtBean(DomainConst.ITEM_TEETH_INFO, DomainConst.CONTENT00566, ""));
        data.add(new ConfigExtBean(DomainConst.ITEM_DIAGNOSIS, DomainConst.CONTENT00567, ""));
        data.add(new ConfigExtBean(DomainConst.ITEM_TREATMENT, DomainConst.CONTENT00568, ""));
    }

    /**
     * Set id customer
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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
                    case DomainConst.ITEM_TIME_ID:
                        updateTime(bean.getName());
                        break;
                    case DomainConst.ITEM_START_DATE:
                        updateDate(bean.getName());
                        break;
                    case DomainConst.ITEM_TEETH_INFO:
                        updateTeeth(bean.getName());
                        break;
                    case DomainConst.ITEM_DIAGNOSIS:
                        updateDiagnosis(bean.getName());
                        break;
                    case DomainConst.ITEM_TREATMENT:
                        updateTreatment(bean.getName());
                        break;
                    default:
                        break;
                }
            }
        });
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
//                    updateWorkDetail(data.get(2));
//                    updateTime(data.get(3).getName());
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
        final boolean[] selecteds = new boolean[teeth.size()];
        teeth.toArray(simpleArray);
        final ConfigExtBean teethInfo = CommonProcess.getConfigExtObjById(data, DomainConst.ITEM_TEETH_INFO);
        if (teethInfo != null) {
            int idx = 0;
            for (ConfigExtBean bean :
                    LoginBean.getInstance().getTooth()) {
                selecteds[idx++] = teethInfo.contains(bean);
            }
        }
        DialogInterface.OnMultiChoiceClickListener listener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                selecteds[i] = b;
            }
        };
        DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (teethInfo != null) {
                    int idx = 0;
                    // Clear current teeth info
                    teethInfo.getDataExt().clear();
                    for (ConfigExtBean bean :
                            LoginBean.getInstance().getTooth()) {
                        // Update teeth info
                        if (selecteds[idx++]) {
                            teethInfo.getDataExt().add(bean);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
        CommonProcess.showMultiSelectionAlert(parentActivity, title, simpleArray, selecteds, listener, okListener, null);
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
        new SimpleSearchDialogCompat(parentActivity, title,
                DomainConst.CONTENT00287, null, LoginBean.getInstance().getDiagnosisFull(),
                new SearchResultListener<ConfigExtBean>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           ConfigExtBean item, int position) {
                        // Check if current selected item is Create new item
                        if (item.getId().equals(DomainConst.ITEM_CREATE_NEW)) {
                            CommonProcess.showMessage(parentActivity, DomainConst.CONTENT00162,
                                    DomainConst.CONTENT00362, null);
                        } else {
                            String[] arrValue = item.getName().split("-");
                            if (arrValue.length == 3) {
                                CommonProcess.setConfigExtDataStrById(data, DomainConst.ITEM_DIAGNOSIS, arrValue[1]);
                            } else {
                                CommonProcess.setConfigExtDataStrById(data, DomainConst.ITEM_DIAGNOSIS, item.getName());
                            }
                            diagnosisId = item.getId();
                            dialog.dismiss();
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                }).show();
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
                DomainConst.CONTENT00287, null, listTreatment,
                new SearchResultListener<ConfigExtBean>() {
                    @Override
                    public void onSelected(BaseSearchDialogCompat dialog,
                                           ConfigExtBean item, int position) {
                        // Check if current selected item is Create new item
                        if (item.getId().equals(DomainConst.ITEM_CREATE_NEW)) {
                            CommonProcess.showMessage(parentActivity, DomainConst.CONTENT00162,
                                    DomainConst.CONTENT00362, null);
                        } else {
                            CommonProcess.setConfigExtDataStrById(data, DomainConst.ITEM_TREATMENT, item.getName());

                            treatmentId = item.getId();
                            dialog.dismiss();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }).show();
    }
}
