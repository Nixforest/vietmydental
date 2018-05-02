package vietmydental.immortal.com.gate.g01.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.component.BaseFragment;
import vietmydental.immortal.com.gate.g00.model.LoginBean;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;
import vietmydental.immortal.com.gate.g01.api.CustomerListRequest;
import vietmydental.immortal.com.gate.g01.component.adapters.G01F00S01ListAdapter;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.vietmydental.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class G01F00S01Fragment extends BaseFragment<G00HomeActivity> {
    /** ListView drawer list */
    @BindView(R.id.listCustomer)
    protected ListView listCustomer;
    /** Current page */
    private int page = 0;
    public G01F00S01Fragment() {
        // Required empty public constructor
    }

    /**
     * Get fragment UUID.
     *
     * @return Fragment UUID
     */
    @Override
    public String getFragmentUUID() {
        return "Danh sách Bệnh nhân";
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ButterKnife.bind(this.parentActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_g01_f00_s01, container, false);
        ButterKnife.bind(this, rootView);
        String token = BaseModel.getInstance().getToken(this.parentActivity.getBaseContext());
        String date = "02/05/2018";
        if (token != null) {
            this.parentActivity.showLoadingView(true);
            CustomerListRequest request = new CustomerListRequest(
                    token, String.valueOf(page), "0", date, date
            ) {
                @Override
                protected void onPostExecute(Object o) {
                    BaseResponse resp = getResponse();
                    if ((resp != null) && resp.isSuccess()) {
                        parentActivity.showLoadingView(false);
                        ArrayList<ConfigBean> data = LoginBean.getInstance().getMenu();
                        if (listCustomer != null) {
                            listCustomer.setAdapter(new G01F00S01ListAdapter(data, parentActivity.getLayoutInflater()));
                        }
                    } else {
                        parentActivity.showLoadingView(false);
                        CommonProcess.showErrorMessage(parentActivity, resp);
                    }
                }
            };
            request.execute();
        }

        return rootView;
    }

    // MARK: Event handler
    /**
     * Handle click on menu button
     */
//    @OnClick(R.id.btnTest)
//    public void onMenuClick(){
//        this.parentActivity.openG01F00S02();
//    }
}
