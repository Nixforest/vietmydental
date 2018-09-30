package vietmydental.immortal.com.gate.g02.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g02.G02Const;
import vietmydental.immortal.com.gate.g02.api.GetStatisticRequest;
import vietmydental.immortal.com.gate.g02.model.StatisticBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
public class G02F00S02Fragment extends BaseFragment<G00HomeActivity> {
    @BindView(R.id.tv_files) TextView tvFiles;
    @BindView(R.id.tv_colect) TextView tvColect;
    @BindView(R.id.tv_dept) TextView tvDept;
    @BindView(R.id.tv_discount) TextView tvDiscount;
    @BindView(R.id.tv_sum) TextView tvSum;
    @BindView(R.id.tv_date_search) TextView tvDateSearch;
    public StatisticBean statisticBean;
    @BindView(R.id.tag_group) TagGroup mTagGroup;
    private String fromDate = "";
    private String toDate   = "";
    private ArrayList<ConfigBean> agentList   = new ArrayList<>();

    private String page   = "-1";
    /**
     * Set data
     * @param fromDate From Date
     * @param toDate To Date
     * @param agentList List Agent
     */
    public void setData(String fromDate, String toDate, ArrayList<ConfigBean> agentList) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.agentList = agentList;

    }
    @OnClick(R.id.btn_list_collected)
    public void goListCollectedScreen() {
        parentActivity.openG02F00S03Fragment(G02Const.STATUS_RECEIPTIONIST, fromDate, toDate);
    }

    @OnClick(R.id.btn_list_no_collected)
    public void goListNoCollectedScreen() {
        parentActivity.openG02F00S03Fragment(G02Const.STATUS_DOCTOR, fromDate, toDate);
    }


    public G02F00S02Fragment() {
        // Required empty public constructor
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.STATISTIC;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);
        ArrayList<String> agentId = new ArrayList<>();

        tvDateSearch.setText("Từ "+ fromDate + " đến " + toDate);
        if (agentList.size() == LoginBean.getInstance().listAgent.size()){
            mTagGroup.setTags("Tất cả");
            for(int i = 1; i < agentList.size(); i++){
                agentId.add(agentList.get(i).getId());
            }
        }
        else{
            String[] strings = new String[agentList.size()];
            for(int i = 0; i< agentList.size(); i++){
                strings[i] = agentList.get(i).getName();
            }
            mTagGroup.setTags(strings);
            for(int i = 0; i < agentList.size(); i++){
                agentId.add(agentList.get(i).getId());
            }
        }

        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());

        if (token != null) {
            parentActivity.showLoadingView(true);
            GetStatisticRequest request = new GetStatisticRequest(token, fromDate, toDate,agentId
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        statisticBean = new StatisticBean(resp.getJsonData());
                        tvFiles.setText(statisticBean.total_qty);
                        tvColect.setText(statisticBean.vfinal);
                        tvDept.setText(statisticBean.dept);
                        tvDiscount.setText(statisticBean.discount_amount);
                        tvSum.setText(statisticBean.total);
                        Log.i("staticBean", String.valueOf(resp.getJsonData()));

                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }
        // Inflate the layout for this fragment
        return rootView;
    }
}
//-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.