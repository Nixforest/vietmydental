package vietmydental.immortal.com.gate.g03.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.component.adapters.DailyReportBranchAdapter;
import vietmydental.immortal.com.gate.component.adapters.DailyReportListAdapter;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.g03.api.DailyReportListRequest;
import vietmydental.immortal.com.gate.g03.api.DailyReportRequest;
import vietmydental.immortal.com.gate.g03.model.DailyReportListResModel;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.
public class G03F00S02Fragment extends BaseFragment<G00HomeActivity> {
    String date = DomainConst.BLANK;
    /** List data */
    public ArrayList<ReceiptBean> list = new ArrayList<>();
    /** Data */
    private DailyReportListResModel respData;
    DailyReportBranchAdapter customAdaper;
    @BindView(R.id.lv_report_branch) ListView lvReport;

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.DAILY_REPORT_DATE + this.date;
    }

    public void setData(String date){
        this.date = date;
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
        View rootView = inflater.inflate(R.layout.fragment_g03_f00_s02, container, false);
        ButterKnife.bind(this, rootView);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date pdate = new Date();
        try {
            pdate = format.parse(this.date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = formatter.format(pdate);
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            DailyReportRequest request = new DailyReportRequest(token,sDate
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        parseData(resp.getArrayData());
                        if(list.size() == 1){
                            parentActivity.openG03F00S03Fragment(list.get(0), date);
                        }
                        else if (list.size() > 1){
                            customAdaper = new DailyReportBranchAdapter(parentActivity,R.layout.list_item_g03_f00_s01,list);
                            lvReport.setAdapter(customAdaper);
                            lvReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                    if ( list.get(position).getData().size() > 0){
                                        for(int i = 0; i < list.get(position).getData().size(); i++){
                                            switch (list.get(position).getData().get(i).getId()){
                                                case DomainConst.ITEM_STATUS_TEXT:
                                                    if(list.get(position).getData().get(i).getData().equals("Chưa duyệt")){
                                                        Toast.makeText(parentActivity, "Báo cáo chưa được tạo bởi Lễ tân",Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        parentActivity.openG03F00S03Fragment(list.get(position), date);
                                                    }
                                                    break;
//
                                                default:
                                                    break;
                                            }
                                        }
                                    }
                                }
                            });
                        }
                        else{

                        }


                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }

        return rootView;
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
    }
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.