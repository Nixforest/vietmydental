package vietmydental.immortal.com.gate.component.adapters;

import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

public class MenuListAdapter extends BaseAdapter {
    public class ViewHolder {
        @BindView(R.id.text)
        TextView textView;
        @BindView(R.id.image)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public MenuListAdapter(ArrayList<ConfigBean> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }

    private ArrayList<ConfigBean> list;
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ConfigBean getItem(int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ConfigBean item = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_menu_item, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {
            holder.textView.setText(item.getName());
            int imageId = getImageFromConfigId(item.getId());
            if (imageId != 0) {
                holder.imageView.setImageResource(imageId);
                holder.imageView.setVisibility(View.VISIBLE);
            } else {
                holder.imageView.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    @DrawableRes
    int getImageFromConfigId(String id) {
        switch (id) {
            case DomainConst.MENU_ID_LIST.HOME:
                return R.drawable.ic_menu_home;
            case DomainConst.MENU_ID_LIST.USER_PROFILE:
                return R.drawable.ic_menu_profile;
            case DomainConst.MENU_ID_LIST.CUSTOMER_LIST:
                return R.drawable.ic_menu_family;
            case DomainConst.MENU_ID_LIST.CONFIGURATION:
                return R.drawable.configuration;
            case DomainConst.MENU_ID_LIST.LOGOUT:
                return R.drawable.logoutitem;
            case DomainConst.MENU_ID_LIST.LOGIN:
                return R.drawable.login;
            //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
            case DomainConst.MENU_ID_LIST.REPORT_REVENUE:
                return R.drawable.ic_menu_report;
            //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
            //++
            case DomainConst.MENU_ID_LIST.KEY_DAILY_REPORT:
                return R.drawable.ic_menu_report;
            //--
            default:
                break;
        }
        return 0;
    }
}
