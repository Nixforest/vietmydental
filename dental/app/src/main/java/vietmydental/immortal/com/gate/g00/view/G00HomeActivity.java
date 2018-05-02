package vietmydental.immortal.com.gate.g00.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONObject;

import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.g00.api.UpdateConfigurationRequest;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.gate.view.BaseActivity;
import vietmydental.immortal.com.vietmydental.R;

public class G00HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        adjustFontScale(getBaseContext(), getResources().getConfiguration());
        setContentView(R.layout.activity_g00_home);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        // For menu
        updateMenu();

        navigator.openHome();
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
     * Handle after get update config
     * @param data JSONObject data
     */
    private void handleUpdateConfig(JSONObject data) {
        LoginBean.getInstance().updateData(data);
        // Update title
        navigator.openHome();
        // Update menu
        updateMenu();
    }

    /**
     * Check extras data.
     *
     * @param extras Extras data.
     * @return False
     */
    private boolean checkExtrasData(Bundle extras) {
        if (extras != null) {
            String type = extras.getString(DomainConst.KEY_TYPE, DomainConst.BLANK);
//            String roleId = BaseModel.getInstance().getRoleId(G00HomeActivity.this);

            Toast.makeText(this, " " + extras.getString(DomainConst.MENU_ID_LIST.KEY_MESSAGE), Toast.LENGTH_SHORT).show();

            return true;
        } else {
            return false;
        }
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
