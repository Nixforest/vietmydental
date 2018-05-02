package vietmydental.immortal.com.vietmydental.component;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import vietmydental.immortal.com.vietmydental.R;
import vietmydental.immortal.com.vietmydental.model.ConfigBean;
import vietmydental.immortal.com.vietmydental.utils.DomainConst;

public abstract class BaseNavigationComponent {
    protected BaseActivity activity;
    /** Manage fragment */
    protected FragmentManager fragmentManager;
    /** List config item */
    public ArrayList<ConfigBean> configBeans;
    /** Check Home view enable or not */
    public boolean isEnableHome = false;

    // UI references.
    @BindView(R.id.rl_header_layout) View layoutHeader;
    @BindView(R.id.rl_subheader_layout) View layoutSubHeader;
    public @BindView(R.id.container1) RelativeLayout layoutLevel1;
    public @BindView(R.id.container2) RelativeLayout layoutLevel2;
    @BindView(R.id.title_btn_back) View btnBack;
    @BindView(R.id.title_btn_sliding_menu) View btnMenu;

    @BindView(R.id.tv_sub_title_header)
    TextView mActivityTitle;
    private BaseFragment lastFragment;


    /**
     * Request show/hide title bar
     *
     * @param isShow
     */
    public void requestShowTitle(boolean isShow) {
        layoutHeader.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    /**
     * Request show/hide title bar
     *
     * @param isShow
     */
    public void requestShowSubTitle(boolean isShow) {
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
            if (layoutLevel > DomainConst.LAYOUT_LEVEL_1 && currentFragment != null && currentFragment instanceof BaseFragment) {
                ((BaseFragment) currentFragment).hideMenu();
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(currentLayout, fragment);
            fragmentTransaction.commit();

            updateLeftRightMenu(fragment);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Set title for current fragment.
     *
     * @param fragment Current fragment
     */
    public void setTitle(BaseFragment fragment) {
        if (mActivityTitle != null && fragment != null && fragment.getFragmentUUID() != null) {
            String fragmentUUID = fragment.getFragmentUUID();
            if (configBeans != null && configBeans.size() > 0) {
                for (ConfigBean item : configBeans) {
                    if (item != null && fragmentUUID.equals(item.getId())) {
                        fragmentUUID = item.getName();
                        break;
                    }
                }
            }
            setTitle(fragmentUUID);
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
     * Get current fragment.
     *
     * @return Fragment.
     */
    public Fragment getCurrentFragment() {
        if (isLevel2()) {
            return fragmentManager.findFragmentById(R.id.container2);
        } else if (isLevel1()) {
            return fragmentManager.findFragmentById(R.id.container1);
        }
        return null;
    }

    public boolean isLevel2() {
        return layoutLevel2.getVisibility() == View.VISIBLE;
    }

    public boolean isLevel1() {
        return layoutLevel1.getVisibility() == View.VISIBLE;
    }


    /**
     * Show top right menu.
     *
     * @param fragment Current fragment
     */
    public void updateLeftRightMenu(BaseFragment fragment) {
        if (fragment != null) {
            // Set title
            setTitle(fragment);

            // Update button left
            BaseFragment.TitleConfigObject titleConfig = fragment.getTitleConfig();
            if (titleConfig != null) {
                requestShowTitle(true);
                btnBack.setVisibility(titleConfig.isShowBack ? View.VISIBLE : View.GONE);
                btnMenu.setVisibility(titleConfig.isShowMenu ? View.VISIBLE : View.GONE);
                String fragmentUUID = fragment.getFragmentUUID();
                requestShowSubTitle(fragmentUUID != null || titleConfig.isShowBack);
            } else {
                requestShowTitle(false);
                requestShowSubTitle(false);
            }
        }
        lastFragment = fragment;
    }

    /**
     * Check & handle back buton or not
     *
     * @return true if handled, false if give back to default action
     */
    public boolean handleBackButton() {
        if (lastFragment != null) {
            return lastFragment.onBackPressed();
        }
        return false;
    }

    public abstract Class<?> getMainActivity();

    public BaseNavigationComponent() {
        // Do nothing
    }
}
