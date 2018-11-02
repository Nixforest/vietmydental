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
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.component.adapters.DailyReportListAdapter;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.g02.model.StatisticBean;
import vietmydental.immortal.com.gate.g03.api.UpdateDailyReportRequest;
import vietmydental.immortal.com.gate.g03.component.adapters.G03F00S03ExtListAdapter;
import vietmydental.immortal.com.gate.g03.model.DailyReportListResModel;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0094-IMT (KhoiVT20180910) [Android] extend when update value for api
public class G03F00S03ExtFragment extends BaseFragment<G00HomeActivity> {
    G03F00S03ExtListAdapter customAdaper;
    ReceiptBean receiptBean = new ReceiptBean();
    String date = DomainConst.BLANK;
    @BindView(R.id.lv_daily_report) ListView listView;
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
                        filterData();
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
                        filterData();
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
        View rootView = inflater.inflate(R.layout.fragment_g03_f00_s03_ext, container, false);
        ButterKnife.bind(this, rootView);
        filterData();
        setData();


        return rootView;
    }

    private void filterData(){
        if ( receiptBean.getData().size() > 0){
            for(int i = 0; i < receiptBean.getData().size(); i++){
                switch (receiptBean.getData().get(i).getId()) {
                    case DomainConst.ITEM_STATUS:
                        if (receiptBean.getData().get(i).getData().equals(String.valueOf(DomainConst.STATUS_CONFIRM))) {
                            btnConfirm.setVisibility(View.INVISIBLE);
                            btnNoConfirm.setVisibility(View.INVISIBLE);
                        } else {
                            btnConfirm.setVisibility(View.VISIBLE);
                            btnNoConfirm.setVisibility(View.VISIBLE);
                        }
                        receiptBean.getData().remove(i);
                        i--;
                        break;
                    case DomainConst.ITEM_AGENT_ID:
                        receiptBean.getData().remove(i);
                        i--;
                        break;
                    case DomainConst.ITEM_STATUS_TEXT:
                        receiptBean.getData().remove(i);
                        i--;
                        break;
                }
            }
        }
    }


    private void setData(){
        customAdaper = new G03F00S03ExtListAdapter(parentActivity,R.layout.list_item_g03_f00_s01,receiptBean.getData());
        listView.setAdapter(customAdaper);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (receiptBean.getData().get(i).getId().equals(DomainConst.ITEM_TOTAL)){
                    parentActivity.openG02F00S03Fragment(receiptBean);
                }
            }
        });

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
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] extend when update value for api