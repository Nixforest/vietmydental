package vietmydental.immortal.com.gate.g00.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.api.UserProfileRequest;
import vietmydental.immortal.com.gate.g00.model.UserProfileBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends BaseFragment<G00HomeActivity> {
    //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    //private UserProfileBean userProfileBean;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.tv_address) TextView tvAddress;
    @OnClick(R.id.btn_update_account)
    public void updateAccount(View view) {
        parentActivity.openUserEditProfile();
    }
    @OnClick(R.id.btn_change_pass)
    public void changePass(View view) {
        parentActivity.openUserChangePassword();
    }
    //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.USER_PROFILE;
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.bind(this, rootView);
        //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
        requestServer();
        //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
        return rootView;
    }

    /**
     * Request data from server
     */
    //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    private void requestServer() {
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            this.parentActivity.showLoadingView(true);
            UserProfileRequest request = new UserProfileRequest(token) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        parentActivity.userProfileBean = new UserProfileBean(resp.getJsonData());
                        Log.i("userprofile", String.valueOf(resp.getJsonData()));
                        setDataForView();
                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }
    }
    //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    private void setDataForView(){
        tvName.setText(parentActivity.userProfileBean.name);
        tvPhone.setText(parentActivity.userProfileBean.phone);
        tvAddress.setText(parentActivity.userProfileBean.address);
    }
    //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh

}
