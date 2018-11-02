package vietmydental.immortal.com.gate.g00.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.net.URI;
import java.net.URISyntaxException;
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
import vietmydental.immortal.com.gate.g02.G02Const;
import vietmydental.immortal.com.gate.g02.api.GetStatisticRequest;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.g02.model.StatisticBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g03.model.DailyReportListResModel;
import vietmydental.immortal.com.gate.g04.api.GetCustomerByQRCodeRequest;
import vietmydental.immortal.com.gate.g04.model.CustomerBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
//++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
public class HomeFragment extends BaseFragment<G00HomeActivity> {
    @BindView(R.id.tv_files) TextView tvFiles;
    @BindView(R.id.tv_colect) TextView tvColect;
    @BindView(R.id.tv_dept) TextView tvDept;
    @BindView(R.id.tv_discount) TextView tvDiscount;
    @BindView(R.id.tv_sum) TextView tvSum;
    @BindView(R.id.tv_date_search) TextView tvDateSearch;
    @BindView(R.id.tag_group) TagGroup mTagGroup;
    public StatisticBean statisticBean;
    String currentDate = DomainConst.BLANK;


    //++ BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
    @BindView(R.id.view_statistic) LinearLayout viewStatistic;
    @BindView(R.id.view_Scan) LinearLayout viewScan;
    @BindView(R.id.edt_qrcode) EditText edtQRCODE;
    /** Data */
//    private DailyReportListResModel respData;
//    /** List data */
//    public ArrayList<ReceiptBean> list = new ArrayList<>();

    @OnClick(R.id.img_scan)
    public void scan() {
        IntentIntegrator.forFragment(HomeFragment.this).initiateScan();
        //intentIntegrator.initiateScan();
    }
    @OnClick(R.id.imgNext)
    public void next() {
        if (edtQRCODE.getText().toString().equals(DomainConst.BLANK)){
            Toast.makeText(parentActivity, getResources().getString(R.string.CONTENT00599),Toast.LENGTH_SHORT).show();
        }
        else{
            String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
            if (token != null) {
                parentActivity.showLoadingView(true);
                GetCustomerByQRCodeRequest request = new GetCustomerByQRCodeRequest(token, edtQRCODE.getText().toString()
                ) {
                    @Override
                    protected void onPostExecute(Object o) {
                        BaseResponse resp = getResponse();
                        if ((resp != null) && resp.isSuccess()) {
                            parentActivity.showLoadingView(false);
//                            JsonParser jsonParser = new JsonParser();
//                            JsonArray gsonObject = (JsonArray) jsonParser.parse(resp.getArrayData().toString());
//                            respData = new DailyReportListResModel(gsonObject);
//                            list = respData.getList();
//                            if (list.size() > 0){
//                                Toast.makeText(parentActivity, "Có thông tin", Toast.LENGTH_SHORT).show();
//                                parentActivity.openG01F01S01(edtQRCODE.getText().toString());
//                            }
//                            else{
//                                Toast.makeText(parentActivity, "Không có thông tin", Toast.LENGTH_SHORT).show();
//                            }
                            CustomerBean customerBean = new CustomerBean(resp.getJsonData());
                            if(!customerBean.dataId.equals(DomainConst.BLANK)){
                                Toast.makeText(parentActivity, "Có thông tin", Toast.LENGTH_SHORT).show();
                                parentActivity.openG01F01S01(customerBean.dataId);
                            }
                            else {
                                Toast.makeText(parentActivity, "Không có thông tin", Toast.LENGTH_SHORT).show();
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

    }
    @OnClick(R.id.btn_history)
    public void goQRCodeList() {
        parentActivity.openG04F00S01Fragment();
    }
    //-- BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.

    @OnClick(R.id.btn_list_collected)
    public void goListCollectedScreen() {
        parentActivity.openG02F00S03Fragment(G02Const.STATUS_RECEIPTIONIST,currentDate,currentDate,LoginBean.getInstance().listAgent);
    }

    @OnClick(R.id.btn_list_no_collected)
    public void goListNoCollectedScreen() {
        parentActivity.openG02F00S03Fragment(G02Const.STATUS_DOCTOR,currentDate,currentDate,LoginBean.getInstance().listAgent);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.HOME;
    }

    /**
     * Get title config to show on menu
     *
     * @return
     */
    @Override
    public TitleConfigObject getTitleConfig() {
        return new BaseFragment.TitleConfigObject(true, false);
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
//        mTagGroup.setTags(new String[]{"Tất cả"});
//        Date todayDate = Calendar.getInstance().getTime();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        String sDate = formatter.format(todayDate);
//        currentDate = sDate;
//        tvDateSearch.setText("Từ "+ sDate + " đến " + sDate);
//        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
//        ArrayList<String> agentId = new ArrayList<>();
//        Log.e("size",String.valueOf(LoginBean.getInstance().agentList.size()));
//        for(int i = 0; i < LoginBean.getInstance().agentList.size(); i++){
//            agentId.add(LoginBean.getInstance().agentList.get(i).getId());
//        }
//        if (token != null) {
//            parentActivity.showLoadingView(true);
//            GetStatisticRequest request = new GetStatisticRequest(token, sDate, sDate,agentId
//            ) {
//                @Override
//                protected void onPostExecute(Object o) {
//                    BaseResponse resp = getResponse();
//                    if ((resp != null) && resp.isSuccess()) {
//                        parentActivity.showLoadingView(false);
//                        statisticBean = new StatisticBean(resp.getJsonData());
//                        tvFiles.setText(statisticBean.total_qty);
//                        tvColect.setText(statisticBean.vfinal);
//                        tvDept.setText(statisticBean.dept);
//                        tvDiscount.setText(statisticBean.discount_amount);
//                        tvSum.setText(statisticBean.total);
//                        Log.i("staticBean", String.valueOf(resp.getJsonData()));
//
//                    } else {
//                        parentActivity.showLoadingView(false);
//                        CommonProcess.showErrorMessage(parentActivity, resp);
//                    }
//                }
//            };
//            request.execute();
//        }
        //++ BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
        if (LoginBean.getInstance().role_id.equals("8")){
            viewScan.setVisibility(View.VISIBLE);
            viewStatistic.setVisibility(View.GONE);
        }
        else{
            String role = LoginBean.getInstance().role_id;
            viewScan.setVisibility(View.GONE);
            viewStatistic.setVisibility(View.VISIBLE);
            mTagGroup.setTags(new String[]{"Tất cả"});
            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String sDate = formatter.format(todayDate);
            currentDate = sDate;
            tvDateSearch.setText("Từ "+ sDate + " đến " + sDate);
            String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
            ArrayList<String> agentId = new ArrayList<>();
            Log.e("size",String.valueOf(LoginBean.getInstance().agentList.size()));
            for(int i = 0; i < LoginBean.getInstance().agentList.size(); i++){
                agentId.add(LoginBean.getInstance().agentList.get(i).getId());
            }
            if (token != null) {
                parentActivity.showLoadingView(true);
                GetStatisticRequest request = new GetStatisticRequest(token, sDate, sDate,agentId
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
        }
        //-- BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }
    //++ BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
    // Get the results:
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(parentActivity, "QR code không hợp lệ", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(parentActivity, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                String contents = result.getContents();
                URI uri = null;
                try {
                    uri = new URI(result.getContents());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                String[] segments = new String[0];
                if (uri != null) {
                    segments = uri.getPath().split("/");
                    String id = segments[segments.length-1];
                    //edtQRCODE.setText(result.getContents());
                    edtQRCODE.setText(id);
                    BaseModel.getInstance().listQRCode.add(id);
                    //Automatic request
                    next();
                }
                else {
                    Toast.makeText(parentActivity, "QR code không hợp lệ", Toast.LENGTH_LONG).show();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //-- BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
}
//-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.