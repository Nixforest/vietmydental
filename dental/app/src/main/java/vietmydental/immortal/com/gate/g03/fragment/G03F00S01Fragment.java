package vietmydental.immortal.com.gate.g03.fragment;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.component.adapters.DailyReportBranchAdapter;
import vietmydental.immortal.com.gate.component.adapters.DailyReportListAdapter;
import vietmydental.immortal.com.gate.component.adapters.ReceiptAdapter;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.fragment.G01F00S02Fragment;
import vietmydental.immortal.com.gate.g02.G02Const;
import vietmydental.immortal.com.gate.g02.api.GetListReceiptsRequest;
import vietmydental.immortal.com.gate.g02.api.GetStatisticRequest;
import vietmydental.immortal.com.gate.g02.model.GetListReceiptsResModel;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.g02.model.StatisticBean;
import vietmydental.immortal.com.gate.g03.api.DailyReportListRequest;
import vietmydental.immortal.com.gate.g03.api.DailyReportRequest;
import vietmydental.immortal.com.gate.g03.model.DailyReportListResModel;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.
public class G03F00S01Fragment extends BaseFragment<G00HomeActivity> {
    @BindView(R.id.lv_report) ListView lvReport;
    ProgressBar progressBar;
    DailyReportListAdapter customAdaper;
    /** Data */
    private DailyReportListResModel respData;
    /** List data */
    public ArrayList<ReceiptBean> list = new ArrayList<>();

    /** List data */
    //++ BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
    public ArrayList<ReceiptBean> listforbranch = new ArrayList<>();
    //-- BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
    public Integer year;
    public Integer month;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
    Boolean isLoading = false;
    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.DAILY_REPORT;
    }

    /**
     * Get title config to show on menu
     *
     * @return
     */
    @Override
    public BaseFragment.TitleConfigObject getTitleConfig() {
        return new BaseFragment.TitleConfigObject(true, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g03_f00_s01, container, false);
        ButterKnife.bind(this, rootView);
        setListViewFooter();
        progressBar.setVisibility(View.GONE);
        Date todayDate = Calendar.getInstance().getTime();
        String vmonth = (String) DateFormat.format("MM",   todayDate);
        String vyear  = (String) DateFormat.format("yyyy", todayDate);
        month = Integer.parseInt(vmonth);
        year = Integer.parseInt(vyear);
        String sDate = formatter.format(todayDate);
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            DailyReportListRequest request = new DailyReportListRequest(token,sDate
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        parseData(resp.getArrayData());
                        customAdaper = new DailyReportListAdapter(parentActivity,R.layout.list_item_g03_f00_s01,list);
                        lvReport.setAdapter(customAdaper);
                        lvReport.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                                if(scrollState == SCROLL_STATE_IDLE && lvReport.getLastVisiblePosition() ==
//                                        list.size() - 1){
//                                    progressBar.setVisibility(View.VISIBLE);
//                                    addMoreItems();
//                                }
                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                if(lvReport.getLastVisiblePosition() == totalItemCount -1 && isLoading == false){
                                    isLoading = true;
                                    addMoreItems();
                                }
                            }
                        });
                        lvReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                                if ( list.get(position).getData().size() > 0){
                                    for(int i = 0; i < list.get(position).getData().size(); i++){
                                        switch (list.get(position).getData().get(i).getId()){
                                            case DomainConst.ITEM_STATUS_TEXT:
                                                //++ BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
                                                if(list.get(position).getData().get(i).getData().equals("Chưa tạo") || list.get(position).getData().get(i).getData().equals("Mới tạo")){
                                                    Toast.makeText(parentActivity, "Báo cáo chưa được tạo bởi Lễ tân",Toast.LENGTH_SHORT).show();

                                                }
                                                else{
                                                    //parentActivity.openG03F00S02Fragment(list.get(position).getName());
                                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                                    Date pdate = new Date();
                                                    try {
                                                        pdate = format.parse(list.get(position).getName());
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                                    String sDate = formatter.format(pdate);
                                                    String token = BaseModel.getInstance().getToken(parentActivity.getBaseContext());
                                                    if (token != null) {
                                                        parentActivity.showLoadingView(true);
                                                        DailyReportRequest request = new DailyReportRequest(token,sDate
                                                        ) {
                                                            @Override
                                                            protected void onPostExecute(Object o) {
                                                                BaseResponse resp = getResponse();
                                                                if ((resp != null) && resp.isSuccess()) {
                                                                    parentActivity.showLoadingView(false);
                                                                    parseDataForBranch(resp.getArrayData());
                                                                    if(listforbranch.size() == 1){
                                                                        parentActivity.openG03F00S03Fragment(listforbranch.get(0), list.get(position).getName());
                                                                    }
                                                                    else if (list.size() > 1){
                                                                        parentActivity.openG03F00S02Fragment(list.get(position).getName());

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
                                                }
                                                //-- BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
                                                break;
//
                                            default:
                                                break;
                                        }
                                    }
                                }
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
        return rootView;
    }

    private void addMoreItems(){
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, 1, 0, 0);
        c.add(Calendar.MONTH, -1);
        String vmonth = (String) DateFormat.format("MM",   c);
        String vyear  = (String) DateFormat.format("yyyy", c);
        month = Integer.parseInt(vmonth);
        year = Integer.parseInt(vyear);
        Date lastMonth = c.getTime();
        String sDate = formatter.format(lastMonth.getTime());
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            DailyReportListRequest request = new DailyReportListRequest(token,sDate
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        JsonParser jsonParser = new JsonParser();
                        JsonArray gsonObject = (JsonArray) jsonParser.parse(resp.getArrayData().toString());
                        respData = new DailyReportListResModel(gsonObject);
                        list.addAll(respData.getList());
                        customAdaper.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        isLoading = false;
                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }

    }

    private void setListViewFooter(){
        View view = LayoutInflater.from(parentActivity).inflate(R.layout.footer_listview_progressbar, null);
        progressBar = view.findViewById(R.id.progressBar);
        lvReport.addFooterView(progressBar);
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

    //++ BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
    /**
     * Parse data from response.
     * @param data JSONObject data
     */
    private void parseDataForBranch(JSONArray data) {
        JsonParser jsonParser = new JsonParser();
        JsonArray gsonObject = (JsonArray) jsonParser.parse(data.toString());
        respData = new DailyReportListResModel(gsonObject);
        listforbranch = respData.getList();
    }
    //-- BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.