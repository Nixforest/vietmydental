package vietmydental.immortal.com.gate.g01.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F01S02ListAdapter;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F01S02Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listInfo)
    protected ListView listInfo;
    /** Current data */
    private ArrayList<ConfigExtBean> mData = new ArrayList<>();

    /**
     * Constructor
     */
    public G01F01S02Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f01_s02, container, false);
        ButterKnife.bind(this, rootView);
        setListInfo();
        return rootView;
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return "Tiền sử bệnh";
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

    /**
     * Set data for screen
     * @param mData Array config extent bean
     */
    public void setData(ArrayList<ConfigExtBean> mData) {
        this.mData = mData;
    }

    /**
     * Set list view information data
     */
    public void setListInfo() {
        if (listInfo != null) {
            listInfo.setAdapter(new G01F01S02ListAdapter(mData, parentActivity.getLayoutInflater()));
        }
    }
}
