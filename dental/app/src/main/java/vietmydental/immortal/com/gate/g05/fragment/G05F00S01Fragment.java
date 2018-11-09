package vietmydental.immortal.com.gate.g05.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g02.api.GetStatisticRequest;
import vietmydental.immortal.com.gate.g02.model.StatisticBean;
import vietmydental.immortal.com.gate.g05.api.MakeScheduleRequest;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0109-IMT (KhoiVT20181105) [Android] Login and make schedule for customer
public class G05F00S01Fragment extends BaseFragment<G00HomeActivity> {

    @BindView(R.id.tv_name) AutoCompleteTextView tvName;
    @BindView(R.id.tv_phone) AutoCompleteTextView tvPhone;
    @BindView(R.id.tv_date) AutoCompleteTextView tvDate;
    @BindView(R.id.tv_content) AutoCompleteTextView tvContent;
    @BindView(R.id.btn_pick_date) Button btnPickDate;
    @BindView(R.id.btn_set_timer) Button btnSetTimer;
    private DatePickerDialog.OnDateSetListener mDateSetListenerToDate;
    String sdate   = DomainConst.BLANK;

    @OnClick(R.id.btn_pick_date)
    public void setToDate(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mDateSetListenerToDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date =  year+ "/" + month + "/" + day;
                tvDate.setText(date);
                sdate = date;
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                parentActivity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListenerToDate,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.btn_set_timer)
    public void makeSchedule(View view) {
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            MakeScheduleRequest request = new MakeScheduleRequest(token, tvName.getText().toString(), tvPhone.getText().toString(), tvDate.getText().toString(), tvContent.getText().toString()) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        Toast.makeText(parentActivity, "đặt lịch hẹn khám thành công", Toast.LENGTH_SHORT).show();
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
        return DomainConst.MENU_ID_LIST.TIMER;
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
        View rootView = inflater.inflate(R.layout.fragment_g05_f00_s01, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

}
//-- BUG0109-IMT (KhoiVT20181105) [Android] Login and make schedule for customer