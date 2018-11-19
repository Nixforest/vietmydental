package vietmydental.immortal.com.vietmydental.component;

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
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;
import vietmydental.immortal.com.vietmydental.R;
import vietmydental.immortal.com.vietmydental.component.adapters.MenuListAdapter;

@RuntimePermissions
public class BaseActivity extends AppCompatActivity {
//    protected BaseActivityExtend extend = new BaseActivityExtend(this);
    /** Loading view */
    protected View loadingView;
    /** Component handle navigator */
    public NavigationComponent navigationComponent;
    /** DrawerLayout layout of menu */
    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    /** ListView drawer list */
    private ListView mDrawerList;
    boolean isPause = false;

    public static void adjustFontScale(Context baseContext, Configuration configuration) {
        Log.w("Tag", "Font scale: " + configuration.fontScale);
        if (configuration.fontScale > 1.0) {
            configuration.fontScale = (float) 1.0;
            DisplayMetrics metrics = baseContext.getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) baseContext.getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            baseContext.getResources().updateConfiguration(configuration, metrics);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
//        ButterKnife.bind(extend, this);

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (navigationComponent.configBeans != null && position >= 0 &&
                        position < navigationComponent.configBeans.size()) {
                    String menuId = navigationComponent.configBeans.get(position).getId();
                    String menuName = navigationComponent.configBeans.get(position).getName();

                    navigationComponent.actionForMenuId(menuId, menuName);
                    closeMenu();
                }
            }
        });

        // Load loading view
        loadingView = findViewById(R.id.loading_view);
        showLoadingView(false);

//        application = (GasApplication) getApplication();
        navigationComponent = new NavigationComponent(this);
    }

    public void closeMenu() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawers();
        }
    }
    @Nullable
    @OnClick(R.id.title_btn_sliding_menu)
    public void onMenuClick() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @OnClick(R.id.title_btn_back)
    void onBackClick() {
        if (!navigationComponent.handleBackButton()) {
            onBackPressed();
        }
    }

    public void setActivateMenuDrawer(boolean isActivate) {
        if (isActivate) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    /**
     * Show loading view.
     *
     * @param isShow True is show, false is hide
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
     * Start App Detail setting to open permission
     *
     * @param context
     */
    public static void startInstalledAppDetailsActivity(Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    /**
     * Handle when tap back button.
     */
    @Override
    public void onBackPressed() {
        Fragment currentFragment = navigationComponent.getCurrentFragment();

        if (currentFragment != null && currentFragment instanceof BaseFragment) {
            if (((BaseFragment) currentFragment).onBackPressed()) {
                return;
            }
        }

        // Hide layout 2 and visible layout 1 if current view is layout 2
        if (navigationComponent.isLevel2()) {
            // Trigger resume
            currentFragment = navigationComponent.getCurrentFragment();
            navigationComponent.layoutLevel1.setVisibility(View.VISIBLE);
            navigationComponent.layoutLevel2.setVisibility(View.GONE);

            currentFragment = navigationComponent.getCurrentFragment();

            // Back by default
            if (currentFragment instanceof BaseFragment) {
                navigationComponent.updateLeftRightMenu((BaseFragment) currentFragment);
            }
            if (currentFragment != null) {
                currentFragment.onResume();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Handle show error for exception
     *
     * @param exception
     */
    public static void handleException(Exception exception) {
//        Mint.logException(exception);
    }

    void setupMenuProfileInfo() {

    }

    public void updateMenuView() {
        setupMenuProfileInfo();
        if (navigationComponent.configBeans != null) {
            mDrawerList.setAdapter(new MenuListAdapter(navigationComponent.configBeans, getLayoutInflater()));
        }
    }

    /**
     * Check version code that responses from server side.
     * If it's a new version. Show dialog to update
     *
     * @param versionCode
     */
    public static void checkVersionCode(final Context context, String versionCode) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int serverVersionCode = Integer.parseInt(versionCode);
            if (serverVersionCode > pInfo.versionCode) {
                // Need update
                showUpdateNewVersion(context);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show update new version dialog
     * @param context Current context
     */
    private static void showUpdateNewVersion(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.CONTENT00072))
                .setMessage(context.getString(R.string.CONTENT00142))
                .setPositiveButton(context.getString(R.string.CONTENT00141), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + "com.immortal.vietmydental"));
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

    public void requestCall(String phoneNumber) {
        BaseActivityPermissionsDispatcher.callActionWithPermissionCheck(this, phoneNumber);
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callAction(String phoneNumber) {
        String uri = "tel:" + phoneNumber;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
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

    public View notifyViewNotImplementYet(String fragmentUUID, String name) {
        String text = "Feature '" + fragmentUUID + "' doesn't implement yet. Please contact with administrator!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        BaseActivity.handleException(new Exception(text));
        return null;
    }

    public void callActionWithCheck(String phoneNumber) {
        BaseActivityPermissionsDispatcher.callActionWithPermissionCheck(this, phoneNumber);
    }

    @Override
    protected void onResume() {
        isPause = false;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isPause = true;
        super.onPause();
    }

    public boolean isPause() {
        return isPause;
    }

    public void changeSystemMenuColor() {
        Window window = getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.main_title));
        }

    }
}
