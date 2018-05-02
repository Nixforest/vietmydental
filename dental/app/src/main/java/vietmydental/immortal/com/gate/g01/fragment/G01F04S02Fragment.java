package vietmydental.immortal.com.gate.g01.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F04S02Fragment extends BaseFragment<G00HomeActivity> {


    public G01F04S02Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_g01_f04_s02, container, false);
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return getString(R.string.CONTENT00556);
    }

    /**
     * Get title config to show on menu
     *
     * @return
     */
    @Override
    public TitleConfigObject getTitleConfig() {
        return new BaseFragment.TitleConfigObject(false, true);
    }
}
