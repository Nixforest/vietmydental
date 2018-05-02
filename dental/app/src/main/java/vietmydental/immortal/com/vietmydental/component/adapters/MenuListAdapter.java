package vietmydental.immortal.com.vietmydental.component.adapters;

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
import vietmydental.immortal.com.vietmydental.R;
import vietmydental.immortal.com.vietmydental.model.ConfigBean;
import vietmydental.immortal.com.vietmydental.utils.DomainConst;

public class MenuListAdapter extends BaseAdapter {
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
        if (DomainConst.MENU_ID_LIST.HOME.equalsIgnoreCase(id)) {
            return R.drawable.ic_menu_home;
        } else if (DomainConst.MENU_ID_LIST.HOME_CUSTOMER.equalsIgnoreCase(id)) {
            return R.drawable.ic_menu_home;
        } else {
            return 0;
        }
    }

    public class ViewHolder {
        @BindView(R.id.text)
        TextView textView;
        @BindView(R.id.image)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
