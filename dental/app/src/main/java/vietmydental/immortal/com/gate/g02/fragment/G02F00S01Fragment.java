package vietmydental.immortal.com.gate.g02.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
public class G02F00S01Fragment extends BaseFragment<G00HomeActivity> {
    //Edit Text Old Password
    @BindView(R.id.tv_branch) TextView tvBranch;
    @BindView(R.id.tv_fromdate) TextView tvFromDate;
    @BindView(R.id.tv_todate) TextView tvToDate;
    String fromDate = DomainConst.BLANK;
    String toDate   = DomainConst.BLANK;

    String[] listItems = new String [LoginBean.getInstance().agentList.size()];
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    //++
    ArrayList<Integer> sUserItems = new ArrayList<>();
    //--
    ArrayList<ConfigBean> listSelected = new ArrayList<>();
    private DatePickerDialog.OnDateSetListener mDateSetListenerFromDate;
    private DatePickerDialog.OnDateSetListener mDateSetListenerToDate;


    @OnClick(R.id.view_from_date)
    public void setFromDate(View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mDateSetListenerFromDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                //Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date =  year+ "/" + month + "/" + day;
                tvFromDate.setText(date);
                fromDate = date;
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                parentActivity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListenerFromDate,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }

    @OnClick(R.id.view_to_date)
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
                tvToDate.setText(date);
                toDate = date;
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

    @OnClick(R.id.btnToday)
    public void searchToday(View view) {
        if(listSelected.size() == 0){
            Toast.makeText(parentActivity,"Bạn chưa chọn chi nhánh!",Toast.LENGTH_SHORT).show();
        }
        else{
            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String sDate = formatter.format(todayDate);
            parentActivity.openG02F00S02Fragment(sDate,sDate, listSelected);
            //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            BaseModel.getInstance().listSelected = listSelected;
            //-- BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            mUserItems.clear();
        }
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    @OnClick(R.id.btnYesterday)
    public void searchYesterday(View view) {
        if(listSelected.size() == 0){
            Toast.makeText(parentActivity,"Bạn chưa chọn chi nhánh!",Toast.LENGTH_SHORT).show();
        }
        else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String sDate = formatter.format(yesterday());
            parentActivity.openG02F00S02Fragment(sDate,sDate, listSelected);
            //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            BaseModel.getInstance().listSelected = listSelected;
            //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            mUserItems.clear();
        }
    }

    @OnClick(R.id.btnLastMonth)
    public void searchLastMonth(View view) {
        if(listSelected.size() == 0){
            Toast.makeText(parentActivity,"Bạn chưa chọn chi nhánh!",Toast.LENGTH_SHORT).show();
        }
        else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Calendar aCalendar = Calendar.getInstance();
// add -1 month to current month
            aCalendar.add(Calendar.MONTH, -1);
// set DATE to 1, so first date of previous month
            aCalendar.set(Calendar.DATE, 1);

            Date firstDateOfPreviousMonth = aCalendar.getTime();

// set actual maximum date of previous month
            aCalendar.set(Calendar.DATE,     aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//read it
            Date lastDateOfPreviousMonth = aCalendar.getTime();
            String fromDate = formatter.format(firstDateOfPreviousMonth.getTime());
            String toDate = formatter.format(lastDateOfPreviousMonth.getTime());
            parentActivity.openG02F00S02Fragment(fromDate,toDate, listSelected);
            //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            BaseModel.getInstance().listSelected = listSelected;
            //-- BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            mUserItems.clear();
        }
    }

    @OnClick(R.id.btncurrenMonth)
    public void searchCurrenMonth(View view) {
        if(listSelected.size() == 0){
            Toast.makeText(parentActivity,"Bạn chưa chọn chi nhánh!",Toast.LENGTH_SHORT).show();
        }
        else{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            Calendar c = Calendar.getInstance();   // this takes current date
            c.set(Calendar.DAY_OF_MONTH, 1);
            Calendar c2 = Calendar.getInstance();
            c2.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
            String fromDate = formatter.format(c.getTime());
            String toDate = formatter.format(c2.getTime());
            parentActivity.openG02F00S02Fragment(fromDate,toDate, listSelected);
            //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            BaseModel.getInstance().listSelected = listSelected;
            //-- BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            mUserItems.clear();
        }
    }

    @OnClick(R.id.btnSearch)
    public void search(View view) {
        if(listSelected.size() == 0){
            Toast.makeText(parentActivity,"Bạn chưa chọn chi nhánh!",Toast.LENGTH_SHORT).show();
        }
        else if(fromDate.equals(DomainConst.BLANK) && toDate.equals(DomainConst.BLANK)){
            Date todayDate = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String sDate = formatter.format(todayDate);
            parentActivity.openG02F00S02Fragment(sDate,sDate, listSelected);
            mUserItems.clear();
            //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            BaseModel.getInstance().fromdate = fromDate;
            BaseModel.getInstance().todate = toDate;
            BaseModel.getInstance().listSelected = listSelected;
            //-- BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
        }
        else if(fromDate.equals(DomainConst.BLANK)){
            parentActivity.openG02F00S02Fragment(toDate,toDate, listSelected);
            mUserItems.clear();
            //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            BaseModel.getInstance().fromdate = toDate;
            BaseModel.getInstance().todate = toDate;
            BaseModel.getInstance().listSelected = listSelected;
            //-- BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
        }
        else if(toDate.equals(DomainConst.BLANK)){
            parentActivity.openG02F00S02Fragment(fromDate,fromDate, listSelected);
            mUserItems.clear();
            //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            BaseModel.getInstance().fromdate = fromDate;
            BaseModel.getInstance().todate = fromDate;
            BaseModel.getInstance().listSelected = listSelected;
            //-- BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
        }
        else{
            parentActivity.openG02F00S02Fragment(fromDate,toDate, listSelected);
            mUserItems.clear();
            //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
            BaseModel.getInstance().fromdate = fromDate;
            BaseModel.getInstance().todate = toDate;
            BaseModel.getInstance().listSelected = listSelected;
            //-- BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
        }

    }
    @OnClick(R.id.view_choose_branch)
    public void chooseBranch(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(parentActivity);
        mBuilder.setTitle("Chọn đại lý");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                if(isChecked){
//                    if( position == 0){
//                        for (int i = 1; i < checkedItems.length; i++) {
//                            checkedItems[i] = false;
//                            ((AlertDialog) dialogInterface).getListView().setItemChecked(i, false);
//                        }
//                        mUserItems.clear();
//                        mUserItems.add(position);
//                    }
//                    else{
//                        checkedItems[0] = false;
//                        ((AlertDialog) dialogInterface).getListView().setItemChecked(0, false);
//                        mUserItems.remove((Integer.valueOf(0)));
//                        mUserItems.add(position);
//                    }
                    mUserItems.add(position);
                }else{
                    mUserItems.remove((Integer.valueOf(position)));
                }
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
//                String item = "";
                    listSelected = new ArrayList<>();
                    for (int i = 0; i < mUserItems.size(); i++) {
                        listSelected.add(LoginBean.getInstance().agentList.get(mUserItems.get(i)));
                    }
//                    if (mUserItems.size() == 1 && mUserItems.get(0) == 0 ){
//                        listSelected = LoginBean.getInstance().agentList;
//                        tvBranch.setText("Tất cả chi nhánh");
//                    }
                    if(listSelected.size()== LoginBean.getInstance().agentList.size()){
                        tvBranch.setText("Tất cả chi nhánh");
                    }
                    else{
                        String branch = "Đã chọn "+ String.valueOf(listSelected.size()) + " chi nhánh";
                        tvBranch.setText(branch);
                    }
            }
        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    mUserItems.clear();

                }
                listSelected = new ArrayList<>();
                String branch = "Đã chọn "+ String.valueOf(listSelected.size()) + " chi nhánh";
                tvBranch.setText(branch);
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

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
        View rootView = inflater.inflate(R.layout.fragment_report_revenue_seach, container, false);
        ButterKnife.bind(this, rootView);

        for(int i = 0; i < LoginBean.getInstance().agentList.size(); i++){
            listItems[i] = LoginBean.getInstance().agentList.get(i).getName();
        }
        checkedItems = new boolean[listItems.length];
        //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
        if (BaseModel.getInstance().listSelected.size() > 0){
            listSelected = BaseModel.getInstance().listSelected;
            if( BaseModel.getInstance().listSelected.size() ==  LoginBean.getInstance().agentList.size()){
                tvBranch.setText(getResources().getString(R.string.CONTENT00580));
            }
            else{
                String branch = "Đã chọn "+ String.valueOf(BaseModel.getInstance().listSelected.size()) + " chi nhánh";
                tvBranch.setText(branch);
            }
            for (int i = 0; i < checkedItems.length; i++) {
                checkedItems[i] = false;
            }
            for(int i = 0 ; i < BaseModel.getInstance().listSelected.size() ; i++){
                for(int e = 0; e < LoginBean.getInstance().agentList.size(); e++){
                    if(LoginBean.getInstance().agentList.get(e).getId().equals(BaseModel.getInstance().listSelected.get(i).getId())){
                        checkedItems[e] = true;
                        mUserItems.add(e);
                    }
                }
            }
        }
        else{
            listSelected = LoginBean.getInstance().agentList;
            for (int i = 0; i < checkedItems.length; i++) {
                checkedItems[i] = true;
                mUserItems.add(i);

            }
        }
        fromDate = BaseModel.getInstance().fromdate;
        toDate = BaseModel.getInstance().todate;
        if (!fromDate.equals(DomainConst.BLANK)){
            tvFromDate.setText(fromDate);
        }
        if (!toDate.equals(DomainConst.BLANK)){
            tvToDate.setText(toDate);
        }
        //-- BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
        //requestServer();
        return rootView;
    }
}
//-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.