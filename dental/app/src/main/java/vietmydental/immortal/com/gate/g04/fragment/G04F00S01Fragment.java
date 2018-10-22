package vietmydental.immortal.com.gate.g04.fragment;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
public class G04F00S01Fragment extends BaseFragment<G00HomeActivity> {
    @BindView(R.id.lv_qrcode) ListView lvQRCode;
    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.QRCODE;
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
        View rootView = inflater.inflate(R.layout.fragment_g04_f00_s01, container, false);
        ButterKnife.bind(this, rootView);
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(parentActivity, android.R.layout.simple_list_item_1, BaseModel.getInstance().listQRCode);
        lvQRCode.setAdapter(itemsAdapter);
        lvQRCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                parentActivity.openG01F01S01(BaseModel.getInstance().listQRCode.get(i));
            }
        });
        return rootView;
    }

}
//-- BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.