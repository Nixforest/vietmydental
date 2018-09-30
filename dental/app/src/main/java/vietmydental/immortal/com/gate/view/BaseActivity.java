package vietmydental.immortal.com.gate.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.component.BaseNavigationComponent;
import vietmydental.immortal.com.gate.component.adapters.MenuListAdapter;
import vietmydental.immortal.com.gate.g00.fragment.HomeFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00LoginActivity;
import vietmydental.immortal.com.gate.g01.fragment.G01F00S01Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F02S02Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F02S05Fragment;
import vietmydental.immortal.com.gate.model.ConfigBean;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

@RuntimePermissions
public class BaseActivity extends AppCompatActivity {
    /** Loading view */
    @BindView(R.id.loading_view)
    protected View loadingView;
    /** ListView drawer list */
    @BindView(R.id.navList)
    protected ListView menuList;
    /** DrawerLayout layout of menu */
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    /** Navigation */
    protected BaseNavigationComponent navigator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                navigator.menuItemClick(LoginBean.getInstance().getMenu().get(i).getId());
                closeMenu();
            }
        });
        showLoadingView(false);
        navigator = new BaseNavigationComponent(this);
    }

    // MARK: Logic
    /**
     * Close menu
     */
    public void closeMenu() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawers();
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

    /**
     * Handle show/hide menu
     * @param isShow Flag show/hide
     */
    public void showMenu(boolean isShow) {
        if (mDrawerLayout != null) {
            if (isShow) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            } else {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        }
    }

    /**
     * Handle show/hide loading view
     * @param isShow Flag show/hide
     */
    public void showLoadingView(boolean isShow) {
        hideKeyboard();
        if (loadingView != null) {
            if (isShow) {
                loadingView.setVisibility(View.VISIBLE);
            } else {
                loadingView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Update data for menu
     */
    public void updateMenu() {
        // TODO: Get menu data
        ArrayList<ConfigBean> data = LoginBean.getInstance().getMenu();
        if (menuList != null) {
            menuList.setAdapter(new MenuListAdapter(data, getLayoutInflater()));
        }
    }

    /**
     * Request call
     * @param phoneNumber Phone number
     */
    public void requestCall(String phoneNumber) {
        BaseActivityPermissionsDispatcher.callActionWithPermissionCheck(this, phoneNumber);
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callAction(String phoneNumber) {
        String uri = "tel:" + phoneNumber;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
        Toast.makeText(this, getString(R.string.CONTENT00258) + " " + phoneNumber, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.CALL_PHONE)
    void showNeverAskForCallPhone() {
        Toast.makeText(this, R.string.permission_call_phone_neverask, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    // MARK: Static methods
    /**
     * Start App Detail setting to open permission
     * @param context Current context
     */
    public static void startInstalledAppDetailsActivity(Activity context) {
        if (context == null) {
            return;
        }
        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(intent);
    }

    /**
     * Show update new version dialog
     * @param context Current context
     */
    private static void showUpdateNewVersionDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.CONTENT00072))
                .setMessage(context.getString(R.string.CONTENT00142))
                .setPositiveButton(context.getString(R.string.CONTENT00141), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + ""));
                        context.startActivity(i);
                    }
                })
                .setNegativeButton(context.getString(R.string.CONTENT00136), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Check version code
     * @param context Current context
     * @param versionCode Version code
     */
    public static void checkVersion(final Context context, String versionCode) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int serverVersion = Integer.parseInt(versionCode);
            if (serverVersion > pInfo.versionCode) {
                // Need update
                showUpdateNewVersionDialog(context);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adjust font scale
     * @param baseContext Base context
     * @param config Configuration
     */
    public static void adjustFontScale(Context baseContext, Configuration config) {
        if (config.fontScale > 1.0) {
            config.fontScale = (float) 1.0;
            DisplayMetrics metrics = baseContext.getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) baseContext.getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = config.fontScale * metrics.density;
            baseContext.getResources().updateConfiguration(config, metrics);
        }
    }

    // MARK: Event handler
    /**
     * Handle click on menu button
     */
    @OnClick(R.id.title_btn_sliding_menu)
    public void onMenuClick(){
        if (mDrawerLayout != null) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }
    /**
     * Handle click on menu button
     */
    @OnClick(R.id.btnFinish)
    public void onFinishClick(){
        ((BaseFragment) navigator.getCurrentFragment()).onFinishClick();
    }

    /**
     * Handle click on back button
     */
    @OnClick(R.id.title_btn_back)
    public void onBackClick() {
        // Handle click on back button
        if (navigator.isBackButtonEnabled()) {
            onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Fragment curFragment = navigator.getCurrentFragment();
        if (curFragment != null && (curFragment instanceof BaseFragment)) {
            if (!((BaseFragment) curFragment).isBackButtonEnabled()) {
                return;
            }
        }
        // Hide layout 2 and visible layout 1 if current view is layout 2
        if (navigator.isLevel2()) {
            // Trigger resume
            curFragment = navigator.getCurrentFragment();
            navigator.layoutLevel1.setVisibility(View.VISIBLE);
            navigator.layoutLevel2.setVisibility(View.GONE);

            // Back by default
            if (curFragment instanceof BaseFragment) {
                navigator.updateLeftRightMenu((BaseFragment) curFragment);
            }
            if (curFragment != null) {
                curFragment.onResume();
            }
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                BaseFragment previousFragment = ((BaseFragment)curFragment).getPreviousFragment();
                navigator.updateLeftRightMenu(previousFragment);
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     * Open G01F00S02 screen
     * @param id Id of customer
     */
    public void openG01F00S02(String id) {
        this.navigator.openG01F00S02(id);
    }

    /**
     * Open G01F01S01 screen
     * @param id Id of customer
     */
    public void openG01F01S01(String id) {
        this.navigator.openG01F01S01(id);
    }

    /**
     * Open G01F01S02 screen
     * @param data Data of screen
     */
    public void openG01F01S02(ArrayList<ConfigExtBean> data, String id, String recordNumber) {
        navigator.openG01F01S02(data, id, recordNumber);
    }

    /**
     * Open G01F02S06 screen
     * @param id Id of customer
     */
    public void openG01F02S06(String id) {
        navigator.openG01F02S06(id);
    }

    /**
     * Open G01F02S01 screen
     * @param id Id of customer
     */
    public void openG01F02S01(String id) {
        navigator.openG01F02S01(id);
    }

    /**
     * Open G01F02S02 screen
     * @param id Id of treatment
     */
    public void openG01F02S02(String id) {
        navigator.openG01F02S02(id);
    }

    /**
     * Open G01F02S05 screen
     * @param id Id of treatment
     * @param data Data of treatment
     * @param diagnosisId Id of diagnosis
     * @param pathologicalId Id of pathological
     */
    public void openG01F02S05(String id, G01F02S02Fragment.TreatmentInfoRespBean data, String diagnosisId, String pathologicalId) {
        navigator.openG01F02S05(id, data, diagnosisId, pathologicalId);
    }
    /**
     * Open G01F03S01 screen
     * @param data Data of screen
     */
    public void openG01F03S01(ConfigExtBean data) {
        navigator.openG01F03S01(data);
    }

    /**
     * Open G01F03S03 screen
     * @param id Id of schedule
     */
    public void openG01F03S03(String id) {
        navigator.openG01F03S03(id);
    }

    /**
     * Open G01F03S04 screen
     * @param id Id of treatment detail
     * @param amount Amount of money
     * @param debt Debt value
     */
    public void openG01F03S04(String id, String amount, String debt) {
        navigator.openG01F03S04(id, amount, debt);
    }

    /**
     * Open G01F03S04 screen
     * @param id Id of treatment detail
     * @param amount Amount of money
     * @param debt Debt value
     */
    public void openG01F03S04(String id, String amount, String discount, String finalAmount,
                              String description, String debt) {
        navigator.openG01F03S04(id, amount, discount, finalAmount, description, debt);
    }

    //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    /**
     * Open openG02F00S03Fragment screen
     * @param status status of receipt
     * @param fromDate from date of receipt
     * @param status to date of receipt
     */
    public void openG02F00S03Fragment(String status, String fromDate, String toDate) {
        navigator.openG02F00S03Fragment(status,fromDate,toDate);
    }

    /**
     * Open openG02F00S02Fragment screen
     * @param fromDate From Date
     * @param toDate To Date
     * @param agentList List Agent
     */
    public void openG02F00S02Fragment(String fromDate, String toDate, ArrayList<ConfigBean> agentList) {
        navigator.openG02F00S02Fragment(fromDate,toDate, agentList);
    }
    //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.

}
