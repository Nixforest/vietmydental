package vietmydental.immortal.com.gate.g01.fragment;


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
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.TreatmentCreateRequest;
import vietmydental.immortal.com.gate.g01.api.TreatmentScheduleDetailCreateRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F02S06ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
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
    private String date = CommonProcess.getCurrentDate(DomainConst.DATE_TIME_FORMAT_2);
    /** Teeth id */
    private String teethId = "";
    /** Diagnosis id */
    private String diagnosisId = "";
    /** Treatment id */
    private String treatmentId = "";

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
        return "Chi tiết điều trị mới";
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
        if (token != null) {
            parentActivity.showLoadingView(true);
            TreatmentScheduleDetailCreateRequest request = new TreatmentScheduleDetailCreateRequest(
                    token, id, timeId, date, teethId,
                    diagnosisId, treatmentId) {
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
        data.add(new ConfigExtBean(DomainConst.ITEM_TEETH, DomainConst.CONTENT00566, ""));
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
        if (listView != null) {
            listView.setAdapter(new G01F02S06ListAdapter(data, parentActivity.getLayoutInflater()));
        }
    }

    /**
     * Handle when click on listview item.
     */
    private void handleListViewClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
}
