package vietmydental.immortal.com.gate.g00.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.g00.api.UpdateConfigurationRequest;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.NotificationBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.gate.view.BaseActivity;
import vietmydental.immortal.com.vietmydental.R;

public class G00HomeActivity extends BaseActivity {
    /** Flag need open Home */
    private boolean isNotOpenHome = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        adjustFontScale(getBaseContext(), getResources().getConfiguration());
        setContentView(R.layout.activity_g00_home);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        // For menu
        updateMenu();
        openHome();
        String token = BaseModel.getInstance().getToken(getBaseContext());
        if (token != null) {
            showLoadingView(true);
            UpdateConfigurationRequest api = new UpdateConfigurationRequest(token) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        handleUpdateConfig(resp.getData());
                        showLoadingView(false);
                    } else {
                        showLoadingView(false);
//                        CommonProcess.showErrorMessage(G00HomeActivity.this, resp.getMessage());
                        CommonProcess.showErrorMessage(G00HomeActivity.this, resp);
                    }
                }
            };
            api.execute();
        }
    }

    /**
     * Open home screen
     */
    private void openHome() {
        if (!isNotOpenHome) {
            navigator.openHome();
        }
    }

    /**
     * Handle after get update config
     * @param data JSONObject data
     */
    private void handleUpdateConfig(JSONObject data) {
        LoginBean.getInstance().updateData(data);
        // Update title
//        navigator.openHome();



        // Check extras data -> false will be redirect to home
        isNotOpenHome = checkExtrasData();
        openHome();
        // Update menu
        updateMenu();
    }

    /**
     * Check extras data.
     *
     * @return False
     */
    private boolean checkExtrasData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String data = extras.getString(DomainConst.KEY_DATA, DomainConst.BLANK);
            try {
                JSONObject jsonObject = new JSONObject(data);
                NotificationBean bean = new NotificationBean(jsonObject);
                switch (bean.getCategory()) {
                    case NotificationBean.NOTIFY_CATEGORY_NEW_TREATMENT_SCHEDULE:
//                        navigator.openG01F02S02(bean.getObjectId());
                        navigator.openG01F00S02(bean.getObjectId());
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * For pick image.
     */
    public void loadImageFromGallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        // Start the Intent

        if (galleryIntent.resolveActivity(getPackageManager()) != null) {
            galleryIntent = Intent.createChooser(galleryIntent, "Select your application");
        }
        startActivityForResult(galleryIntent, DomainConst.RESULT_LOAD_IMG);
    }
}
