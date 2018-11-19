package vietmydental.immortal.com.gate.g00.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.g00.api.GetDomainRequest;
import vietmydental.immortal.com.gate.g00.api.LoginRequest;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g04.model.CustomerBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.NotificationBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.LoginActivity;
import vietmydental.immortal.com.vietmydental.R;
import vietmydental.immortal.com.vietmydental.utils.CommonUtils;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class G00LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    /**
     * Number of click on Logo
     */
    private int clickNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g00_login);
        //++ BUG0151-IMT (KhoiVT 20181114) get domain by GetDomainName api
        if (BaseModel.getInstance().getServerURL().equals(DomainConst.BLANK)){
            getDomain();
        }
        //-- BUG0151-IMT (KhoiVT 20181114) get domain by GetDomainName api
        checkExtrasData(getIntent().getExtras());
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        //++
        Button mPatientButton = (Button) findViewById(R.id.for_patient_button);
        mPatientButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), G00PatientLoginActivity.class);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    intent.putExtras(getIntent().getExtras());
                }
                startActivity(intent);
                finish();
            }
        });
        //--

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        if (BaseModel.getInstance().getMode() == DomainConst.MODE_TRAINING) {
            mEmailView.setText("khoadt");
            mPasswordView.setText("Nkvm@123");
        }
        if (BaseModel.getInstance().getToken(getBaseContext()) != null) {
            if (!BaseModel.getInstance().getToken(getBaseContext()).isEmpty()) {
                gotoHomeActivity();
            }
        }
        int mode = BaseModel.getInstance().getMode(getBaseContext());
        if (mode == DomainConst.MODE_TRAINING) {
            mEmailView.setTextColor(Color.RED);
        } else {
            mEmailView.setTextColor(Color.BLACK);
        }
        clickNumber = 0;
        View logo = findViewById(R.id.login_logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Increase clickNumber value
                clickNumber++;
                // Meet maximum value
                if (DomainConst.MAX_CLICK_NUMBER == clickNumber) {
                    // Reset clickNumber value
                    clickNumber = 0;
                    if (BaseModel.getInstance().getMode(getBaseContext()) == DomainConst.MODE_TRAINING) {
                        BaseModel.getInstance().setMode(getBaseContext(), DomainConst.MODE_RUNNING);
                        CommonProcess.showMessage(G00LoginActivity.this,
                                G00LoginActivity.this.getString(R.string.CONTENT00162),
                                "Training mode is OFF", null);
                        mEmailView.setTextColor(Color.BLACK);
                    } else {
                        BaseModel.getInstance().setMode(getBaseContext(), DomainConst.MODE_TRAINING);
                        CommonProcess.showMessage(G00LoginActivity.this,
                                G00LoginActivity.this.getString(R.string.CONTENT00162),
                                "Training mode is ON", null);
                        mEmailView.setTextColor(Color.RED);
                    }
//                    CommonProcess.showErrorMessage(G00LoginActivity.this, "Mode = " + String.valueOf(BaseModel.getInstance().getMode(getBaseContext())));
                    //++ BUG0151-IMT (KhoiVT 20181114) get domain by GetDomainName api
                    BaseModel.getInstance().setDefaultServerUrl(G00LoginActivity.this);
                    //-- BUG0151-IMT (KhoiVT 20181114) get domain by GetDomainName api
                }
            }
        });
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    //++ BUG0151-IMT (KhoiVT 20181114) get domain by GetDomainName api
    void getDomain() {
        BaseModel.getInstance().setServerUrl(DomainConst.MAIN_URL);
        //showLoadingView(true);
        GetDomainRequest api = new GetDomainRequest() {
            @Override
            protected void onPostExecute(Object o) {
                BaseResponse resp = getResponse();
                if (resp != null && resp.isSuccess()) {
                    JSONObject data = resp.getData();
                    CustomerBean customerBean = new CustomerBean(resp.getJsonData());
                    if(!customerBean.dataId.equals(DomainConst.BLANK)){
                        BaseModel.getInstance().setServerUrl(customerBean.dataId);
                    }
                    else{
                        //++ BUG0151-IMT (KhoiVT 20181114) fix bug
                        BaseModel.getInstance().setDefaultServerUrl(G00LoginActivity.this);
                        //++ BUG0151-IMT (KhoiVT 20181114) fix bug
                    }
                } else {
                    //++ BUG0151-IMT (KhoiVT 20181114) fix bug
                    BaseModel.getInstance().setDefaultServerUrl(G00LoginActivity.this);
                    //++ BUG0151-IMT (KhoiVT 20181114) fix bug
                    CommonUtils.showErrorMessage(G00LoginActivity.this, resp.toString());
                }
                //showLoadingView(false);
            }
        };
        api.execute();
    }
    //-- BUG0151-IMT (KhoiVT 20181114) get domain by GetDomainName api

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
//        if (mAuthTask != null) {
//            return;
//        }
        hideKeyboard();

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.CONTENT00023));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            mAuthTask = new UserLoginTask(email, password);
//            mAuthTask.execute((Void) null);
            LoginRequest api = new LoginRequest(email, password, FirebaseInstanceId.getInstance().getToken(), "") {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        handleLoginSuccess(resp.getData());
                        showProgress(false);
                    } else {
                        CommonProcess.showErrorMessage(G00LoginActivity.this, resp.getMessage());
                        //++ BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
                        showProgress(false);
                        //-- BUG0097-IMT (KhoiVT20181010) [Android] Fix bug.
                    }
                }
            };
            api.execute();
        }
    }

    /**
     * Check extras data.
     * @param extras Extras data
     * @return False
     */
    private boolean checkExtrasData(Bundle extras) {
        if (extras != null) {
            String data = extras.getString(DomainConst.KEY_DATA, DomainConst.BLANK);
            try {
                JSONObject jsonObject = new JSONObject(data);
                NotificationBean bean = new NotificationBean(jsonObject);
                switch (bean.getCategory()) {
                    // Use for future
                    default: break;
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
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
        BaseModel.getInstance().setValue(getBaseContext(), DomainConst.PREF_TYPE.LAST_LOGIN_USERNAME, mEmailView.getText().toString());
        gotoHomeActivity();
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

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
//        return password.length() > 4;
        return true;
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(G00LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    /**
     * Hide keyboard.
     */
    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (getCurrentFocus() != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

