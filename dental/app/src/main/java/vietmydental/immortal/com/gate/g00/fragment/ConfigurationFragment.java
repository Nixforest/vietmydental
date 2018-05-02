package vietmydental.immortal.com.gate.g00.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends BaseFragment<G00HomeActivity> {


    public ConfigurationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_configuration, container, false);
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return DomainConst.MENU_ID_LIST.CONFIGURATION;
    }

    /**
     * Get title config to show on menu
     *
     * @return
     */
    @Override
    public TitleConfigObject getTitleConfig() {
        return new BaseFragment.TitleConfigObject(true, false);
    }
}
