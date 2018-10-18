package vietmydental.immortal.com.gate.g03.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gujun.android.taggroup.TagGroup;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.component.adapters.DailyReportBranchAdapter;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g02.G02Const;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.g02.model.StatisticBean;
import vietmydental.immortal.com.gate.g03.api.DailyReportRequest;
import vietmydental.immortal.com.gate.g03.api.UpdateDailyReportRequest;
import vietmydental.immortal.com.gate.g03.model.DailyReportListResModel;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.
public class G03F00S03Fragment  extends BaseFragment<G00HomeActivity> {

    ReceiptBean receiptBean = new ReceiptBean();
    String date = DomainConst.BLANK;
    @BindView(R.id.tv_sum) TextView tvSum;
    @BindView(R.id.tv_branch) TextView tvBranch;
    @BindView(R.id.tv_creator) TextView tvCreator;
    @BindView(R.id.btn_confirm) Button btnConfirm;
    @BindView(R.id.btn_no_confirm) Button btnNoConfirm;
    private DailyReportListResModel respData;
    /** List data */
    public ArrayList<ReceiptBean> list = new ArrayList<>();
    public StatisticBean statisticBean;
    String currentDate = DomainConst.BLANK;
    @OnClick(R.id.btn_confirm)
    public void confirm() {
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            UpdateDailyReportRequest request = new UpdateDailyReportRequest(token,receiptBean.getId(),DomainConst.STATUS_CONFIRM
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        Toast.makeText(parentActivity,"Xác nhận báo cáo thành công",Toast.LENGTH_SHORT).show();
                        //++ BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
                        parseData(resp.getArrayData());
                        setData();
                        //-- BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }
    }

    @OnClick(R.id.btn_no_confirm)
    public void unconfirm() {
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            UpdateDailyReportRequest request = new UpdateDailyReportRequest(token,receiptBean.getId(),DomainConst.STATUS_CANCEL
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        Toast.makeText(parentActivity,"Không xác nhận báo cáo thành công",Toast.LENGTH_SHORT).show();
                        //++ BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
                        parseData(resp.getArrayData());
                        setData();
                        //-- BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.


                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }
    }

    @OnClick(R.id.view_sum)
    public void goSumScreen() {
        parentActivity.openG02F00S03Fragment(receiptBean);
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.DAILY_REPORT_DATE + this.date;
    }


    public void setData(ReceiptBean receiptBean, String date){
        this.receiptBean = receiptBean;
        this.date        = date;
    }

    /**
     * Get title config to show on menu
     *
     * @return
     */
    @Override
    public BaseFragment.TitleConfigObject getTitleConfig() {
        return new BaseFragment.TitleConfigObject(false, true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g03_f00_s03, container, false);
        ButterKnife.bind(this, rootView);
        setData();

        return rootView;
    }
    //++ BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
    private void setData(){
        if ( receiptBean.getData().size() > 0){
            for(int i = 0; i < receiptBean.getData().size(); i++){
                switch (receiptBean.getData().get(i).getId()){
                    case DomainConst.ITEM_TOTAL:
                        tvSum.setText(receiptBean.getData().get(i).getData());
                        break;
                    case DomainConst.ITEM_AGENT:
                        tvBranch.setText(receiptBean.getData().get(i).getData());
                        break;
                    case DomainConst.ITEM_RECEIPTIONIST:
                        tvCreator.setText(receiptBean.getData().get(i).getData());
                        break;
                    case DomainConst.ITEM_STATUS:
                        if(receiptBean.getData().get(i).getData().equals(String.valueOf(DomainConst.STATUS_CONFIRM))){
                            btnConfirm.setVisibility(View.INVISIBLE);
                            btnNoConfirm.setVisibility(View.INVISIBLE);
                        }
                        else {
                            btnConfirm.setVisibility(View.VISIBLE);
                            btnNoConfirm.setVisibility(View.VISIBLE);
                        }
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Parse data from response.
     * @param data JSONObject data
     */
    private void parseData(JSONArray data) {
        JsonParser jsonParser = new JsonParser();
        JsonArray gsonObject = (JsonArray) jsonParser.parse(data.toString());
        respData = new DailyReportListResModel(gsonObject);
        list = respData.getList();
        receiptBean = list.get(0);
    }
    //-- BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.