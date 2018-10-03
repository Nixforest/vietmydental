package vietmydental.immortal.com.gate.g02.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.component.adapters.ReceiptAdapter;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g02.G02Const;
import vietmydental.immortal.com.gate.g02.api.GetListReceiptsRequest;
import vietmydental.immortal.com.gate.g02.model.GetListReceiptsResModel;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
public class G02F00S03Fragment extends BaseFragment<G00HomeActivity> {
    @BindView(R.id.tv_sum_statistic) TextView tvSum;
    @BindView(R.id.tv_debt_statistic) TextView tvDebt;
    @BindView(R.id.tv_doctor_statistic) TextView tvDoctor;
    @BindView(R.id.tv_cashier_statistic) TextView tvCashier;
    @BindView(R.id.tv_discount_statistic) TextView tvDiscount;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.lv_statistic) ListView lvStatistic;
    public GetListReceiptsResModel getListReceiptsResModel;
    public ArrayList<ReceiptBean> listReceipt = new ArrayList<>();
    public ArrayList<ConfigBean> agentList = new ArrayList<>();


    private String fromDate = DomainConst.BLANK;
    private String toDate = DomainConst.BLANK;
    private String status = DomainConst.BLANK;
    private String page   = "-1";
    /**
     * Set data
     * @param status
     */
    public void setData(String status, String fromDate, String toDate, ArrayList<ConfigBean> agentList) {
        this.status   = status;
        this.fromDate = fromDate;
        this.toDate   = toDate;
        this.agentList = agentList;

    }
    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        if (status.equals(G02Const.STATUS_RECEIPTIONIST)){
            return DomainConst.MENU_ID_LIST.COLLECTED_REVENUE;
        }
        else {
            return DomainConst.MENU_ID_LIST.NO_COLLECTED_REVENUE;
        }
        //return DomainConst.MENU_ID_LIST.DETAIL_REVENUE;
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
        View rootView = inflater.inflate(R.layout.fragment_g02_f00_s03, container, false);
        ButterKnife.bind(this, rootView);
//        Date todayDate = Calendar.getInstance().getTime();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//        String sDate = formatter.format(todayDate);
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        ArrayList<String> agentId = new ArrayList<>();
        for(int i = 0; i < agentList.size(); i++){
            agentId.add(agentList.get(i).getId());
        }
        if (token != null) {
            parentActivity.showLoadingView(true);
            GetListReceiptsRequest request = new GetListReceiptsRequest(token, fromDate, toDate,agentId,page,status
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        getListReceiptsResModel = new GetListReceiptsResModel(resp.getJsonData());
                        listReceipt = getListReceiptsResModel.list;
                        ReceiptAdapter customAdaper = new ReceiptAdapter(parentActivity,R.layout.list_item_g02_f00_s03,listReceipt);
                        lvStatistic.setAdapter(customAdaper);
                        lvStatistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                if ( listReceipt.get(i).getData().size() > 0){
                                    for(int p = 0; p < listReceipt.get(i).getData().size(); p++ ){
                                        switch (listReceipt.get(i).getData().get(p).getId()){
                                            case DomainConst.ITEM_NAME:  tvName
                                                    .setText(listReceipt.get(i).getData().get(p).getData());
                                                break;
                                            case DomainConst.ITEM_DOCTOR:  tvDoctor
                                                    .setText(listReceipt.get(i).getData().get(p).getData());
                                                break;
                                            case DomainConst.ITEM_RECEIPTIONIST:  tvCashier
                                                    .setText(listReceipt.get(i).getData().get(p).getData());
                                                break;
                                            case DomainConst.ITEM_DISCOUNT:  tvDiscount
                                                    .setText(listReceipt.get(i).getData().get(p).getData());
                                                break;
                                            case DomainConst.ITEM_TOTAL:  tvSum
                                                    .setText(listReceipt.get(i).getData().get(p).getData());
                                                break;
                                            case DomainConst.ITEM_DEBT:  tvDebt
                                                    .setText(listReceipt.get(i).getData().get(p).getData());
                                                break;

                                            default:
                                                break;
                                        }
                                    }
                                }
                            }
                        });
//                        lvStatistic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                                if ( listReceipt.get(position).getData().size() > 0){
//                                    for(int i = 0; i < listReceipt.get(position).getData().size(); i++){
//                                        switch (listReceipt.get(i).getData().get(i).getId()){
//                                            case DomainConst.ITEM_NAME:  tvName
//                                                    .setText(listReceipt.get(i).getData().get(i).getData());
//                                                break;
//                                            case DomainConst.ITEM_DOCTOR:  tvDoctor.setText(listReceipt.get(i).getData().get(i).getData());
//                                                break;
//                                            case DomainConst.ITEM_RECEIPTIONIST:  tvCashier.setText(listReceipt.get(i).getData().get(i).getData());
//                                                break;
//                                            case DomainConst.ITEM_DISCOUNT:  tvDiscount.setText(listReceipt.get(i).getData().get(i).getData());
//                                                break;
//                                            case DomainConst.ITEM_TOTAL:  tvSum.setText(listReceipt.get(i).getData().get(i).getData());
//                                                break;
////                                            case DomainConst.ITEM_FINAL:  tv.setText(receipt.getData().get(i).getData());
////                                                break;
//                                            default:
//                                                break;
//                                        }
//                                    }
//                                }
//                            }
//                        });

                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }
        //requestServer();
        return rootView;
    }
}
//-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.

