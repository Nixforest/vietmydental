//++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
package vietmydental.immortal.com.gate.g00.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.api.ChangePasswordRequest;
import vietmydental.immortal.com.gate.g00.api.ChangeProfileRequest;
import vietmydental.immortal.com.gate.g00.api.UserProfileRequest;
import vietmydental.immortal.com.gate.g00.model.UserProfileBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
import vietmydental.immortal.com.vietmydental.utils.CommonUtils;

public class UserChangePasswordFragment extends BaseFragment<G00HomeActivity>{
    //Edit Text Old Password
    @BindView(R.id.user_change_pass_old)            EditText edtOld;
    //Edit Text New Password
    @BindView(R.id.user_change_pass_new)            EditText edtNew;
    //Edit Text New Password Confirm
    @BindView(R.id.user_change_pass_new_confirm)    EditText edtNewConfirm;

    @OnClick(R.id.btn_save)
    public void changePass(View view) {
        parentActivity.hideKeyboard();
        // Set data
        String oldPass = edtOld.getText().toString();
        String newPass = edtNew.getText().toString();
        String newPassConfirm = edtNewConfirm.getText().toString();

        if (oldPass.isEmpty() || newPass.isEmpty()) {
            // Show empty dialog
            CommonUtils.showErrorMessage(parentActivity, getString(R.string.CONTENT00025));
            return;
        } else if (!newPass.equals(newPassConfirm)) {
            CommonUtils.showErrorMessage(parentActivity, getString(R.string.CONTENT00026));
            return;
        }
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        if (token != null) {
            parentActivity.showLoadingView(true);
            ChangePasswordRequest request = new ChangePasswordRequest(oldPass,newPass,token) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        Toast.makeText(parentActivity, DomainConst.CONTENT00579,Toast.LENGTH_SHORT).show();
                        parentActivity.openUserProfile();

                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }

    }

    @OnClick(R.id.btn_cancel)
    public void cancel(View view) {
        parentActivity.openUserProfile();
    }

    public UserChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.USER_PROFILE_CHANGE_PASS;
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
        View rootView = inflater.inflate(R.layout.fragment_user_change_password, container, false);
        ButterKnife.bind(this, rootView);
        //requestServer();
        return rootView;
    }
}
//-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh