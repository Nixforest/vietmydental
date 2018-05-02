package vietmydental.immortal.com.vietmydental.component;

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
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import vietmydental.immortal.com.vietmydental.R;

public abstract class BaseFragment<T extends AppCompatActivity> extends Fragment {
    public static class TitleConfigObject {
        public final boolean isShowMenu;
        public final boolean isShowBack;

        /**
         * Config to show item on title bar
         *
         * @param isShowMenu menu action
         * @param isShowBack back action
         */
        public TitleConfigObject(boolean isShowMenu, boolean isShowBack) {
            this.isShowMenu = isShowMenu;
            this.isShowBack = isShowBack;
        }
    }

    @IntDef({View.VISIBLE, View.INVISIBLE, View.GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    /** Parent activity */
    protected T parentActivity;
    /**
     * Used for unbind butter knife
     */
    protected Unbinder fragmentUnbinder;
    /** Flag for check view destroyed or not */
    private boolean isDestroyed;
    @Nullable protected @BindView(R.id.edt_list_search) AutoCompleteTextView textSearch;

    /**
     * Handle when tap back button.
     *
     * @return False
     */
    public boolean onBackPressed() {
        return false;
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

    public boolean isDestroyedView() {
        return isDestroyed;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity = (T) getActivity();
    }

    @Optional
    @OnClick(R.id.btn_list_search_reset)
    void onSearchResetClick() {
        if (textSearch != null) {
            textSearch.setText("");
        }
    }

    public void hideMenu() {
//        if (btnFloatMenu != null) {
//            btnFloatMenu.close(true);
//        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // https://stackoverflow.com/questions/7575921/illegalstateexception-can-not-perform-this-action-after-onsaveinstancestate-wit
        super.onSaveInstanceState(outState);
    }

//    public void handleException(BinService.BinServiceException exception) {
//        BaseActivity.handleError(parentActivity, exception);
//    }
}
