package vietmydental.immortal.com.gate.component;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.Unbinder;
import vietmydental.immortal.com.vietmydental.R;

public abstract class BaseFragment<T extends AppCompatActivity> extends Fragment {
    /** Parent activity */
    protected T parentActivity;
    /** Used for unbind butter knife */
    protected Unbinder fragmentUnbinder;
    /** Flag for check view destroyed or not */
    private boolean isDestroyed;
    /** Edit text search */
    @Nullable
    protected @BindView(R.id.edt_list_search)
    AutoCompleteTextView textSearch;
    /** Previous fragment */
    private BaseFragment previousFragment;
    /** Title */
    private String title;

    public BaseFragment getPreviousFragment() {
        return previousFragment;
    }

    public void setPreviousFragment(BaseFragment previousFragment) {
        this.previousFragment = previousFragment;
    }

    /**
     * Title config object
     */
    public static class TitleConfigObject {
        /**
         * Flag show menu
         */
        public final boolean isShowMenu;
        /**
         * Flag show back button
         */
        public final boolean isShowBack;
        /**
         * Flag show right button
         */
        public final boolean isShowRightBtn;

        /**
         * Config to show item on title bar
         *
         * @param isShowMenu menu action
         * @param isShowBack back action
         */
        public TitleConfigObject(boolean isShowMenu, boolean isShowBack) {
            this.isShowMenu = isShowMenu;
            this.isShowBack = isShowBack;
            this.isShowRightBtn = false;
        }

        /**
         * Config to show item on title bar
         *
         * @param isShowMenu menu action
         * @param isShowBack back action
         */
        public TitleConfigObject(boolean isShowMenu, boolean isShowBack, boolean isShowRightBtn) {
            this.isShowMenu = isShowMenu;
            this.isShowBack = isShowBack;
            this.isShowRightBtn = isShowRightBtn;
        }
    }

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    /**
     * Handle when tap back button.
     *
     * @return False
     */
    public boolean isBackButtonEnabled() {
        return getTitleConfig().isShowBack;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    public abstract String getFragmentUUID();

    /**
     * Get title config to show on menu
     *
     * @return
     */
    public abstract TitleConfigObject getTitleConfig();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unbind data
        if (fragmentUnbinder != null) {
            fragmentUnbinder.unbind();
            fragmentUnbinder = null;
        }
        isDestroyed = true;
    }

    /**
     * Check view is destroyed already
     * @return Flag isDestroyed value
     */
    public boolean isDestroyedView() {
        return isDestroyed;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (T) getActivity();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // https://stackoverflow.com/questions/7575921/illegalstateexception-can-not-perform-this-action-after-onsaveinstancestate-wit
        super.onSaveInstanceState(outState);
    }

    public void onFinishClick() {

    }
}
