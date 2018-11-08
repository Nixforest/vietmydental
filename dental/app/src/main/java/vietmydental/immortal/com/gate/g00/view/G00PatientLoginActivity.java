package vietmydental.immortal.com.gate.g00.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.g00.api.CustomerLoginRequest;
import vietmydental.immortal.com.gate.g00.api.CustomerLoginConfirmRequest;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

public class G00PatientLoginActivity extends AppCompatActivity {
    //++
    @BindView(R.id.login_phone) EditText edtPhone;
    @BindView(R.id.btn_phone_next) Button btnPhoneNext;
    @BindView(R.id.login_otp) EditText edtOtp;
    @BindView(R.id.btn_otp_next) Button btnOtpNext;
    @BindView(R.id.tv_phone) TextView tvPhone;
    @BindView(R.id.btn_back) Button btnBack;
    @BindView(R.id.btn_resend) Button btnResend;
    @BindView(R.id.view_phone) LinearLayout viewPhone;
    @BindView(R.id.view_otp) LinearLayout viewOtp;
    @BindView(R.id.login_progress) View mProgressView;
    @BindView(R.id.login_form) View mLoginFormView;

    @OnClick(R.id.btn_phone_next)
    public void goOTPConfirmScreen() {
        //parentActivity.openG02F00S03Fragment(G02Const.STATUS_RECEIPTIONIST, fromDate, toDate,agentList);
        if (edtPhone.getText().length() < 10) {
            Toast.makeText(this, "Số điện thoại không đúng", Toast.LENGTH_SHORT).show();
        } else {
            showProgress(true);
            CustomerLoginRequest request = new CustomerLoginRequest(edtPhone.getText().toString()) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        tvPhone.setText(edtPhone.getText().toString());
                        viewPhone.setVisibility(View.GONE);
                        viewOtp.setVisibility(View.VISIBLE);
                        showProgress(false);
                    } else {
                        CommonProcess.showErrorMessage(G00PatientLoginActivity.this, resp);
                        showProgress(false);
                    }
                }
            };
            request.execute();
        }
    }

    @OnClick(R.id.btn_otp_next)
    public void confirm() {
        if (edtOtp.getText().length() == 0 ) {
            Toast.makeText(this, "Mã OTP không được rỗng", Toast.LENGTH_SHORT).show();
            edtOtp.requestFocus();
        } else {
            showProgress(true);
            CustomerLoginConfirmRequest request = new CustomerLoginConfirmRequest(edtPhone.getText().toString(),edtOtp.getText().toString(), FirebaseInstanceId.getInstance().getToken()) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        handleLoginSuccess(resp.getData());
                        showProgress(false);
                    } else {
                        CommonProcess.showErrorMessage(G00PatientLoginActivity.this, resp);
                        showProgress(false);
                    }
                }
            };
            request.execute();
        }
        //gotoHomeActivity();
    }

    /**
     * Handle after login success
     * @param data JSONObject data
     */
    private void handleLoginSuccess(JSONObject data) {
        LoginBean.getInstance().updateData(data);
        // Set token
        BaseModel.getInstance().setToken(getBaseContext(), LoginBean.getInstance().getToken());
        // Save username
        BaseModel.getInstance().setValue(getBaseContext(), DomainConst.PREF_TYPE.LAST_LOGIN_USERNAME, edtPhone.getText().toString());
        gotoHomeActivity();
    }

    @OnClick(R.id.btn_back)
    public void back() {
        //parentActivity.openG02F00S03Fragment(G02Const.STATUS_RECEIPTIONIST, fromDate, toDate,agentList);
        viewOtp.setVisibility(View.GONE);
        viewPhone.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.btn_resend)
    public void resend() {
        showProgress(true);
        CustomerLoginRequest request = new CustomerLoginRequest(edtPhone.getText().toString()) {
            @Override
            protected void onPostExecute(Object o) {
                BaseResponse resp = getResponse();
                if ((resp != null) && resp.isSuccess()) {
                    Toast.makeText(G00PatientLoginActivity.this, "Gửi OTP lại thành công", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                } else {
                    CommonProcess.showErrorMessage(G00PatientLoginActivity.this, resp);
                    showProgress(false);
                }
            }
        };
        request.execute();
    }

    /**
     * Go to home activity.
     */
    private void gotoHomeActivity() {
        Intent intent = new Intent(getBaseContext(), G00HomeActivity.class);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            intent.putExtras(getIntent().getExtras());
        }
        startActivity(intent);
        finish();
    }

    //--
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g00_patient_login);
        ButterKnife.bind(this);
        TelephonyManager tMgr = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String mPhoneNumber = tMgr.getLine1Number();
        edtPhone.setText(mPhoneNumber);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
