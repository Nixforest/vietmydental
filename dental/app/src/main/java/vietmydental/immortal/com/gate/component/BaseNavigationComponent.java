package vietmydental.immortal.com.gate.component;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.g00.api.LogoutRequest;
import vietmydental.immortal.com.gate.g00.fragment.ConfigurationFragment;
import vietmydental.immortal.com.gate.g00.fragment.HomeFragment;
import vietmydental.immortal.com.gate.g00.fragment.TestFragment;
import vietmydental.immortal.com.gate.g00.fragment.UserChangePasswordFragment;
import vietmydental.immortal.com.gate.g00.fragment.UserEditProfileFragment;
import vietmydental.immortal.com.gate.g00.fragment.UserProfileFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00LoginActivity;
import vietmydental.immortal.com.gate.g01.fragment.G01F00S01Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F00S02Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F01S01Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F01S02Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F02S01Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F02S02Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F02S05Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F02S06Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F03S01Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F03S03Fragment;
import vietmydental.immortal.com.gate.g01.fragment.G01F03S04Fragment;
import vietmydental.immortal.com.gate.g02.fragment.G02F00S01Fragment;
import vietmydental.immortal.com.gate.g02.fragment.G02F00S02Fragment;
import vietmydental.immortal.com.gate.g02.fragment.G02F00S03Fragment;
import vietmydental.immortal.com.gate.g04.fragment.G04F00S01Fragment;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.gate.view.BaseActivity;
import vietmydental.immortal.com.vietmydental.R;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;

public class BaseNavigationComponent {
    /** Current activity */
    protected BaseActivity curActivity;
    /** Manage fragment */
    protected FragmentManager fragmentManager;
    /** Last fragment */
    private BaseFragment lastFragment;

    // UI references.
    /** Header layout */
    @BindView(R.id.rl_header_layout)
    public View layoutHeader;
    /** Sub header layout */
    @BindView(R.id.rl_subheader_layout)
    public View layoutSubHeader;
    /** First container */
    @BindView(R.id.container1)
    public RelativeLayout layoutLevel1;
    /** Second container */
    @BindView(R.id.container2)
    public RelativeLayout layoutLevel2;
    /** Back button */
    @BindView(R.id.title_btn_back)
    public View btnBack;
    /** Right button */
    @BindView(R.id.btnFinish)
    public View btnRight;
    /** Menu button */
    @BindView(R.id.title_btn_sliding_menu)
    public View btnMenu;
    @BindView(R.id.tv_sub_title_header)
    public TextView mActivityTitle;

    /**
     * Constructor
     */
    public BaseNavigationComponent(BaseActivity activity) {
        if (!(activity instanceof G00HomeActivity)) {
            return;
        }
        ButterKnife.bind(this, activity.findViewById(android.R.id.content));
        fragmentManager = activity.getFragmentManager();
        this.curActivity = activity;
    }

    /**
     * Get main activity
     * @return Home activity class
     */
    public Class<?> getMainActivity() {
        return G00HomeActivity.class;
    }

    /**
     * Show/hide title
     * @param isShow Flag show/hide
     */
    public void showTitle(boolean isShow) {
        layoutHeader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * Show/hide sub title
     *
     * @param isShow Flag show/hide
     */
    public void showSubTitle(boolean isShow) {
        layoutSubHeader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * Show fragment.
     *
     * @param fragment Fragment
     */
    public void moveToFragment(BaseFragment fragment, int layoutLevel) {
        try {
            int currentLayout;
            if (layoutLevel == DomainConst.LAYOUT_LEVEL_1) {
                layoutLevel1.removeAllViews();
                layoutLevel2.removeAllViews();

                layoutLevel1.setVisibility(View.VISIBLE);
                layoutLevel2.setVisibility(View.GONE);
                currentLayout = R.id.container1;
            } else if (layoutLevel == DomainConst.LAYOUT_LEVEL_2) {
                layoutLevel2.removeAllViews();

                layoutLevel1.setVisibility(View.GONE);
                layoutLevel2.setVisibility(View.VISIBLE);
                currentLayout = R.id.container2;
            } else {
                return;
            }

            // Close previous menu first
            Fragment currentFragment = getCurrentFragment();
//            if (layoutLevel > DomainConst.LAYOUT_LEVEL_1 && currentFragment != null && currentFragment instanceof BaseFragment) {
//                ((BaseFragment) currentFragment).hideMenu();
//            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(currentLayout, fragment).addToBackStack(fragment.getClass().toString());
            fragmentTransaction.commit();
            // Update menu
            updateLeftRightMenu(fragment);
            if (currentFragment != null && currentFragment instanceof BaseFragment) {
               fragment.setPreviousFragment((BaseFragment) currentFragment);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Check if second container is visible
     * @return True if second container is visible, False otherwise
     */
    public boolean isLevel2() {
        return layoutLevel2.getVisibility() == View.VISIBLE;
    }

    /**
     * Check if first container is visible
     * @return True if first container is visible, False otherwise
     */
    public boolean isLevel1() {
        return layoutLevel1.getVisibility() == View.VISIBLE;
    }

    /**
     * Get current fragment
     * @return Current fragment
     */
    public Fragment getCurrentFragment() {
        if (isLevel2()) {
            return fragmentManager.findFragmentById(R.id.container2);
        } else if (isLevel1()) {
            return fragmentManager.findFragmentById(R.id.container1);
        }
        return null;
    }

    /**
     * Update left right menu
     * @param fragment Current fragment need to update
     */
    public void updateLeftRightMenu(BaseFragment fragment) {
        if (fragment != null) {
            // Set title
            setTitle(fragment);

            // Update left button
            BaseFragment.TitleConfigObject titleConfig = fragment.getTitleConfig();
            if (titleConfig != null) {
                showTitle(true);
                btnBack.setVisibility(titleConfig.isShowBack ? View.VISIBLE : View.GONE);
                btnMenu.setVisibility(titleConfig.isShowMenu ? View.VISIBLE : View.GONE);
                btnRight.setVisibility(titleConfig.isShowRightBtn ? View.VISIBLE : View.GONE);
            } else {
                showTitle(false);
            }
        }
        lastFragment = fragment;
    }

    /**
     * Set title for fragment
     * @param fragment Fragment
     */
    public void setTitle(BaseFragment fragment) {
        if ((mActivityTitle != null) && (fragment != null)
                && (fragment.getFragmentUUID() != null)) {
            String fragmentId = fragment.getFragmentUUID();
            for (ConfigBean item :
                    LoginBean.getInstance().getMenu()) {
                if (fragmentId.equals(item.getId())) {
                    setTitle(item.getName());
                    return;
                }
            }
            setTitle(fragmentId);
        }
    }

    /**
     * Set title for activity.
     *
     * @param title Title to set
     */
    public void setTitle(String title) {
        if (mActivityTitle != null) {
            mActivityTitle.setText(title);
        }
    }

    /**
     * Handle click on menu item
     * @param menuId Id of menu item
     */
    public void menuItemClick(String menuId) {
        switch (menuId) {
            case DomainConst.MENU_ID_LIST.HOME:
                openHome();
                break;
            case DomainConst.MENU_ID_LIST.USER_PROFILE:
                openUserProfile();
                break;
            case DomainConst.MENU_ID_LIST.CUSTOMER_LIST:
                openG01F00S01();
                break;
            case DomainConst.MENU_ID_LIST.CONFIGURATION:
                openConfiguration();
                break;
            case DomainConst.MENU_ID_LIST.LOGOUT:
                logout();
                break;
            case DomainConst.MENU_ID_LIST.LOGIN:
                openLogin();
                break;
            //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
            case DomainConst.MENU_ID_LIST.REPORT_REVENUE:
                openReportReVenue();
                break;
            //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
            default:
                break;
        }
    }

    /**
     * Check if back button is enabled
     * @return True if back button can press, False otherwise.
     */
    public boolean isBackButtonEnabled() {
        if (lastFragment != null) {
            return lastFragment.isBackButtonEnabled();
        }
        return false;
    }

    /**
     * Open home screen
     */
    public void openHome() {
        BaseFragment fragment = new HomeFragment();
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }
    //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    /**
     * Open home screen
     */
    public void openReportReVenue() {
        BaseFragment fragment = new G02F00S01Fragment();
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }
    //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    /**
     * Open Configuration
     */
    public void openConfiguration() {
        BaseFragment fragment = new ConfigurationFragment();
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open user profile
     */
    public void openUserProfile() {
        BaseFragment fragment = new UserProfileFragment();
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }
    //++ BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    /**
     * Open user edit profile
     */
    public void openUserEditProfile() {
        BaseFragment fragment = new UserEditProfileFragment();
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open user change password
     */
    public void openUserChangePassword() {
        BaseFragment fragment = new UserChangePasswordFragment();
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }
    //-- BUG0032-IMT (KhoiVT 20180921) [Android] Các màn hình vệ tinh
    /**
     * Handle log out
     */
    public void logout() {
        curActivity.showLoadingView(true);
        String token = BaseModel.getInstance().getToken(curActivity.getBaseContext());
        if (token != null) {
            LogoutRequest api = new LogoutRequest(token) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        // Clean data
                        LoginBean.getInstance().clearData();
                        BaseModel.getInstance().setToken(curActivity.getBaseContext(), DomainConst.BLANK);
                        curActivity.showLoadingView(false);
                        // Go to login activity
                        openLogin();
                    } else {
                        curActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(curActivity, resp);
                    }
                }
            };
            api.execute();
        }
    }

    /**
     * Open login activity
     */
    public void openLogin() {
        Intent intent = new Intent(curActivity.getBaseContext(), G00LoginActivity.class);
        curActivity.startActivity(intent);
        curActivity.finish();
    }

    /**
     * Open G01F00S01 screen
     */
    public void openG01F00S01() {
        BaseFragment fragment = new G01F00S01Fragment();
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F00S02 screen
     * @param id Id of customer
     */
    public void openG01F00S02(String id) {
        BaseFragment fragment = new G01F00S02Fragment();
        ((G01F00S02Fragment)fragment).setId(id);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F01S01 screen
     * @param id Id of customer
     */
    public void openG01F01S01(String id) {
        BaseFragment fragment = new G01F01S01Fragment();
        ((G01F01S01Fragment)fragment).setId(id);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F01S02 screen
     * @param data Data of screen
     */
    public void openG01F01S02(ArrayList<ConfigExtBean> data, String id, String recordNumber) {
        BaseFragment fragment = new G01F01S02Fragment();
        ((G01F01S02Fragment)fragment).setData(data);
        ((G01F01S02Fragment)fragment).setId(id);
        ((G01F01S02Fragment)fragment).setRecordNumber(recordNumber);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F02S05 screen
     * @param id Id of treatment
     * @param data Data of treatment
     * @param diagnosisId Id of diagnosis
     * @param pathologicalId Id of pathological
     */
    public void openG01F02S05(String id, G01F02S02Fragment.TreatmentInfoRespBean data, String diagnosisId, String pathologicalId) {
        BaseFragment fragment = new G01F02S05Fragment();
        ((G01F02S05Fragment)fragment).setData(id, diagnosisId, pathologicalId);
        ((G01F02S05Fragment)fragment).setData(data);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F02S06 screen
     * @param id Id of customer
     */
    public void openG01F02S06(String id) {
        BaseFragment fragment = new G01F02S06Fragment();
        ((G01F02S06Fragment)fragment).setId(id);
        ((G01F02S06Fragment)fragment).initData();
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F02S01 screen
     * @param id Id of customer
     */
    public void openG01F02S01(String id) {
        BaseFragment fragment = new G01F02S01Fragment();
        ((G01F02S01Fragment)fragment).setId(id);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F02S02 screen
     * @param id Id of treatment
     */
    public void openG01F02S02(String id) {
        BaseFragment fragment = new G01F02S02Fragment();
        ((G01F02S02Fragment)fragment).setId(id);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F03S01 screen
     * @param data Data of screen
     */
    public void openG01F03S01(ConfigExtBean data) {
        BaseFragment fragment = new G01F03S01Fragment();
//        ((G01F03S01Fragment)fragment).setId(id);
        ((G01F03S01Fragment)fragment).setData(data);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F03S03 screen
     * @param id Id of schedule
     */
    public void openG01F03S03(String id) {
        BaseFragment fragment = new G01F03S03Fragment();
        ((G01F03S03Fragment)fragment).setId(id);
        ((G01F03S03Fragment)fragment).initData();
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F03S04 screen
     * @param id Id of treatment detail
     * @param amount Amount of money
     * @param debt Debt value
     */
    public void openG01F03S04(String id, String amount, String debt) {
        BaseFragment fragment = new G01F03S04Fragment();
        ((G01F03S04Fragment)fragment).setId(id);
        ((G01F03S04Fragment)fragment).initData(amount, debt);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G01F03S04 screen
     * @param id Id of treatment detail
     * @param amount Amount of money
     * @param debt Debt value
     */
    public void openG01F03S04(String id, String amount, String discount, String finalAmount, String description, String debt) {
        BaseFragment fragment = new G01F03S04Fragment();
        ((G01F03S04Fragment)fragment).setData(id, amount, discount, finalAmount, description, debt);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    /**
     * Open G02F00S03Fragment screen
     * @param status Status
     * @param fromDate From Date
     * @param toDate To Date
     */
    public void openG02F00S03Fragment(String status, String fromDate, String toDate, ArrayList<ConfigBean> agentList) {
        BaseFragment fragment = new G02F00S03Fragment();
        ((G02F00S03Fragment)fragment).setData(status, fromDate, toDate, agentList);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Open G02F00S02Fragment screen
     * @param fromDate From Date
     * @param toDate To Date
     * @param agentList List Agent
     */
    public void openG02F00S02Fragment(String fromDate, String toDate, ArrayList<ConfigBean> agentList) {
        BaseFragment fragment = new G02F00S02Fragment();
        ((G02F00S02Fragment)fragment).setData(fromDate,toDate,agentList);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }
    //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.

    //++ BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
    /**
     * Open G04F00S01Fragment screen
     */
    public void openG04F00S01Fragment() {
        BaseFragment fragment = new G04F00S01Fragment();
        //((G02F00S02Fragment)fragment).setData(fromDate,toDate,agentList);
        this.moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    //-- BUG0100-IMT (KhoiVT20180910) [Android] Scan QRCode.
}
