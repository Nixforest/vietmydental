//++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
package vietmydental.immortal.com.gate.g00.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.api.StreetListRequest;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.api.ChangeProfileRequest;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.model.StreetBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

public class UserEditProfileFragment extends BaseFragment<G00HomeActivity> {
    public String _cityId = DomainConst.BLANK;
    public String _districtId = DomainConst.BLANK;
    public String _streetId = DomainConst.BLANK;
    public String _ward_id = DomainConst.BLANK;
    public String name = DomainConst.BLANK;
    public String email = DomainConst.BLANK;
    public String houseNumber = DomainConst.BLANK;
    public ArrayList<ConfigBean> districtList = new ArrayList<>();
    public ArrayList<ConfigBean> wardList = new ArrayList<>();
    public ArrayList<ConfigBean> streetList = new ArrayList<>();
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_email) TextView tvEmail;
    @BindView(R.id.tv_house_number) TextView tvHouseNumber;
    @BindView(R.id.tv_city) TextView tvCity;
    @BindView(R.id.tv_district) TextView tvDistrict;
    @BindView(R.id.tv_street) TextView tvStreet;
    @BindView(R.id.tv_ward) TextView tvWard;

    @OnClick(R.id.btn_save)
    public void save(View view) {
        if (!name.equals(tvName.getText().toString()) || !email.equals(tvEmail.getText().toString()) || !houseNumber.equals(tvHouseNumber.getText().toString()) || !_cityId.equals(parentActivity.userProfileBean.province_id) || !_districtId.equals(parentActivity.userProfileBean.district_id) || !_ward_id.equals(parentActivity.userProfileBean.ward_id) || !_streetId.equals(parentActivity.userProfileBean.street_id)) {
            String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
            if (token != null) {
                this.parentActivity.showLoadingView(true);
                ChangeProfileRequest request = new ChangeProfileRequest(tvName.getText().toString(), _cityId, _districtId, _ward_id, _streetId, tvHouseNumber.getText()
                        .toString(), tvEmail.getText().toString(), token) {
                    @Override
                    protected void onPostExecute(Object o) {
                        BaseResponse resp = getResponse();
                        if ((resp != null) && resp.isSuccess()) {
                            parentActivity.showLoadingView(false);
                            Toast.makeText(parentActivity, DomainConst.CONTENT00580, Toast.LENGTH_SHORT).show();
                            parentActivity.openUserProfile();

                        } else {
                            parentActivity.showLoadingView(false);
                            CommonProcess.showErrorMessage(parentActivity, resp);
                        }
                    }
                };
                request.execute();
            }
        } else {
            Toast.makeText(parentActivity, DomainConst.CONTENT00580, Toast.LENGTH_SHORT).show();
            parentActivity.openUserProfile();
        }


    }

    @OnClick(R.id.btn_cancel)
    public void cancel(View view) {
        if (!name.equals(tvName.getText().toString()) || !email.equals(tvEmail.getText().toString()) || !houseNumber.equals(tvHouseNumber.getText().toString()) || !_cityId.equals(parentActivity.userProfileBean.province_id) || !_districtId.equals(parentActivity.userProfileBean.district_id) || !_ward_id.equals(parentActivity.userProfileBean.ward_id) || !_streetId.equals(parentActivity.userProfileBean.street_id)) {
            AlertDialog.Builder adb = new AlertDialog.Builder(parentActivity);
            adb.setTitle("Thông báo");
            adb.setMessage("Bạn thật sự muốn thoát");
            adb.setIcon(android.R.drawable.ic_dialog_alert);
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    parentActivity.onBackPressed();
                }
            });
            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final AlertDialog dialog = adb.create();
            dialog.show();
        } else {
            parentActivity.onBackPressed();
        }
    }

    @OnClick(R.id.view_name)
    public void updateName(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(parentActivity);
        View mview = parentActivity.getLayoutInflater().inflate(R.layout.update_dialog, null);
        final TextView tvNameUpdate = mview.findViewById(R.id.tv_name_update);
        tvNameUpdate.setText(getResources().getString(R.string.CONTENT00577));
        final EditText edtValue = mview.findViewById(R.id.edt_update);
        edtValue.setText(tvName.getText().toString());
        final Button btnCancel = mview.findViewById(R.id.btn_cancel);
        final Button btnOk = mview.findViewById(R.id.btn_Ok);

        mBuilder.setView(mview);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtValue.getText().toString().equals("")) {
                    tvName.setText(edtValue.getText());
                }
                if (!tvName.getText().toString().equals(parentActivity.userProfileBean.name)) {
                    tvName.setTextColor(getResources().getColor(R.color.brand_color_1));
                } else {
                    tvName.setTextColor(getResources().getColor(R.color.main_color_3));
                }
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.view_email)
    public void updateEmail(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(parentActivity);
        View mview = parentActivity.getLayoutInflater().inflate(R.layout.update_dialog, null);
        final TextView tvNameUpdate = mview.findViewById(R.id.tv_name_update);
        tvNameUpdate.setText(getResources().getString(R.string.prompt_email));
        final EditText edtValue = mview.findViewById(R.id.edt_update);
        edtValue.setText(tvEmail.getText().toString());
        final Button btnCancel = mview.findViewById(R.id.btn_cancel);
        final Button btnOk = mview.findViewById(R.id.btn_Ok);

        mBuilder.setView(mview);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtValue.getText().toString().equals("")) {
                    tvEmail.setText(edtValue.getText());
                }
                if (!tvEmail.getText().toString().equals(parentActivity.userProfileBean.email)) {
                    tvEmail.setTextColor(getResources().getColor(R.color.brand_color_1));
                } else {
                    tvEmail.setTextColor(getResources().getColor(R.color.main_color_3));
                }
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.view_house_numbers)
    public void updateHouseNumbers(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(parentActivity);
        View mview = parentActivity.getLayoutInflater().inflate(R.layout.update_dialog, null);
        final TextView tvNameUpdate = mview.findViewById(R.id.tv_name_update);
        tvNameUpdate.setText(getResources().getString(R.string.CONTENT00057));
        final EditText edtValue = mview.findViewById(R.id.edt_update);
        edtValue.setText(tvHouseNumber.getText().toString());
        final Button btnCancel = mview.findViewById(R.id.btn_cancel);
        final Button btnOk = mview.findViewById(R.id.btn_Ok);

        mBuilder.setView(mview);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtValue.getText().toString().equals("")) {
                    tvHouseNumber.setText(edtValue.getText());
                }
                if (!tvHouseNumber.getText().toString().equals(parentActivity.userProfileBean.house_numbers)) {
                    tvHouseNumber.setTextColor(getResources().getColor(R.color.brand_color_1));
                } else {
                    tvHouseNumber.setTextColor(getResources().getColor(R.color.main_color_3));
                }
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.view_city)
    public void updateProvince(View view) {
        final SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(parentActivity, getResources().getString(R.string.CONTENT00575), DomainConst.CONTENT00287, null,
                LoginBean.getInstance().getAddress(), new SearchResultListener<ConfigBean>() {
            @Override
            public void onSelected(final BaseSearchDialogCompat baseSearchDialogCompat, ConfigBean item, int i) {
                // Get selected item
                if (!_cityId.equals(item.getId())) {
                    tvCity.setText(item.getName());
                    _cityId = item.getId();
                    setDistrictList();
                    checkExistDistrict();
                    checkExistWard();
                    requestStreet();
                }
                if (!_cityId.equals(parentActivity.userProfileBean.province_id)) {
                    tvCity.setTextColor(getResources().getColor(R.color.brand_color_1));
                } else {
                    tvCity.setTextColor(getResources().getColor(R.color.main_color_3));
                }
                baseSearchDialogCompat.dismiss();
            }
        });
        dialog.show();
    }

    public void requestStreet() {
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            this.parentActivity.showLoadingView(true);
            StreetListRequest request = new StreetListRequest(_cityId, "-1", token) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        StreetBean streetBean = new StreetBean(resp.getJsonData());
                        streetList = streetBean.list;
                        checkExistStreet();
                        //Log.i("userprofile", String.valueOf(resp.getJsonData()));

                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }

    }

    public void setDistrictList() {
        for (int e = 0; e <= LoginBean.getInstance().getAddress().size() - 1; e++) {
            if (_cityId.equals(LoginBean.getInstance().getAddress().get(e).getId())) {
                districtList = LoginBean.getInstance().getAddress().get(e).getData();
                break;
            }
        }
    }

    public void setWardList() {
        for (int e = 0; e <= districtList.size() - 1; e++) {
            if (_districtId.equals(districtList.get(e).getId())) {
                wardList = districtList.get(e).getData();
            }
        }
    }

    public void checkExistDistrict() {
        if (districtList.size() > 0) {
            boolean check = false;
            for (int e = 0; e <= districtList.size() - 1; e++) {
                if (_districtId.equals(districtList.get(e).getId())) {
                    check = true;
                    tvDistrict.setText(districtList.get(e).getName());
                    break;
                }
            }
            if (!check) {
                _districtId = districtList.get(0).getId();
                tvDistrict.setText(districtList.get(0).getName());
            }
            setWardList();
        } else {
            tvDistrict.setText("");
        }
        if (!_districtId.equals(parentActivity.userProfileBean.district_id)) {
            tvDistrict.setTextColor(getResources().getColor(R.color.brand_color_1));
        } else {
            tvDistrict.setTextColor(getResources().getColor(R.color.main_color_3));
        }
    }

    public void checkExistWard() {
        if (wardList.size() > 0) {
            boolean check = false;
            for (int e = 0; e <= wardList.size() - 1; e++) {
                if (_ward_id.equals(wardList.get(e).getId())) {
                    check = true;
                    tvWard.setText(wardList.get(e).getName());
                    break;
                }
            }
            if (!check) {
                _ward_id = wardList.get(0).getId();
                tvWard.setText(wardList.get(0).getName());
            }
        } else {
            tvWard.setText("");
        }
        if (!_ward_id.equals(parentActivity.userProfileBean.ward_id)) {
            tvWard.setTextColor(getResources().getColor(R.color.brand_color_1));
        } else {
            tvWard.setTextColor(getResources().getColor(R.color.main_color_3));
        }
    }

    public void checkExistStreet() {
        if (streetList.size() > 0) {
            boolean check = false;
            for (int e = 0; e <= streetList.size() - 1; e++) {
                if (_streetId.equals(streetList.get(e).getId())) {
                    check = true;
                    tvStreet.setText(streetList.get(e).getName());
                    break;
                }
            }
            if (!check) {
                _streetId = streetList.get(0).getId();
                tvStreet.setText(streetList.get(0).getName());
            }
        } else {
            tvStreet.setText("");
        }
        if (!_streetId.equals(parentActivity.userProfileBean.street_id)) {
            tvStreet.setTextColor(getResources().getColor(R.color.brand_color_1));
        } else {
            tvStreet.setTextColor(getResources().getColor(R.color.main_color_3));
        }
    }

    @OnClick(R.id.view_district)
    public void updatecity(View view) {
        final SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(parentActivity, getResources().getString(R.string.CONTENT00579), DomainConst.CONTENT00287, null,
                districtList, new SearchResultListener<ConfigBean>() {
            @Override
            public void onSelected(final BaseSearchDialogCompat baseSearchDialogCompat, ConfigBean item, int i) {
                // Get selected item
                if (!_districtId.equals(item.getId())) {
                    tvDistrict.setText(item.getName());
                    _districtId = item.getId();
                    setWardList();
                    checkExistWard();
                }
                if (!_districtId.equals(parentActivity.userProfileBean.district_id)) {
                    tvDistrict.setTextColor(getResources().getColor(R.color.brand_color_1));
                } else {
                    tvDistrict.setTextColor(getResources().getColor(R.color.main_color_3));
                }
                baseSearchDialogCompat.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.view_ward)
    public void updateWard(View view) {
        final SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(parentActivity, getResources().getString(R.string.CONTENT00576), DomainConst.CONTENT00287, null,
                wardList, new SearchResultListener<ConfigBean>() {
            @Override
            public void onSelected(final BaseSearchDialogCompat baseSearchDialogCompat, ConfigBean item, int i) {
                // Get selected item
                if (!_ward_id.equals(item.getId())) {
                    tvWard.setText(item.getName());
                    _ward_id = item.getId();
                }
                if (!_ward_id.equals(parentActivity.userProfileBean.ward_id)) {
                    tvWard.setTextColor(getResources().getColor(R.color.brand_color_1));
                } else {
                    tvWard.setTextColor(getResources().getColor(R.color.main_color_3));
                }
                baseSearchDialogCompat.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.view_street)
    public void updateStreet(View view) {
        final SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(parentActivity, getResources().getString(R.string.CONTENT00576), DomainConst.CONTENT00287, null,
                streetList, new SearchResultListener<ConfigBean>() {
            @Override
            public void onSelected(final BaseSearchDialogCompat baseSearchDialogCompat, ConfigBean item, int i) {
                // Get selected item
                tvStreet.setText(item.getName());
                _streetId = item.getId();
                if (!_streetId.equals(parentActivity.userProfileBean.street_id)) {
                    tvStreet.setTextColor(getResources().getColor(R.color.brand_color_1));
                } else {
                    tvStreet.setTextColor(getResources().getColor(R.color.main_color_3));
                }
                baseSearchDialogCompat.dismiss();
            }
        });
        dialog.show();

    }

    public UserEditProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.USER_PROFILE_EDIT;
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_edit_profile, container, false);
        ButterKnife.bind(this, rootView);
        setDataForView();
        String s = LoginBean.getInstance().getAddress().toString();
        Log.i("test", s);
        //requestServer();
        return rootView;
    }

    public void setDataForView() {
        tvName.setText(parentActivity.userProfileBean.name);
        if (parentActivity.userProfileBean.email.isEmpty()) {
            tvEmail.setText(getResources().getString(R.string.prompt_email));
        } else {
            tvEmail.setText(parentActivity.userProfileBean.email);
        }
        if (parentActivity.userProfileBean.house_numbers.isEmpty()) {
            tvHouseNumber.setText(getResources().getString(R.string.CONTENT00057));
        } else {
            tvHouseNumber.setText(parentActivity.userProfileBean.house_numbers);
        }
        name = parentActivity.userProfileBean.name;
        email = parentActivity.userProfileBean.email;
        houseNumber = parentActivity.userProfileBean.house_numbers;
        _cityId = parentActivity.userProfileBean.province_id;
        _districtId = parentActivity.userProfileBean.district_id;
        _ward_id = parentActivity.userProfileBean.ward_id;
        _streetId = parentActivity.userProfileBean.street_id;
        updateAddress();
    }

    public void updateAddress() {
        if (!_cityId.equals("")) {
            for (int i = 0; i < LoginBean.getInstance().getAddress().size(); i++) {
                if (LoginBean.getInstance().getAddress().get(i).getId().equals(_cityId)) {
                    tvCity.setText(LoginBean.getInstance().getAddress().get(i).getName());
                }
            }
            setDistrictList();
            checkExistDistrict();
            checkExistWard();
        }
        requestStreet();
    }

}
//-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
