package vietmydental.immortal.com.gate.g01.component.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.TextPaint;
import android.util.AttributeSet;
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

/**
 * TODO: document your custom view class.
 */
public class G01F00S01ListAdapter extends BaseAdapter {
    public class ViewHolder {
        @BindView(R.id.text)
        TextView textView;
        @BindView(R.id.image)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public G01F00S01ListAdapter(ArrayList<ConfigBean> list, LayoutInflater inflater) {
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
        G01F00S01ListAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_g01_f01_s00, parent, false);

            holder = new G01F00S01ListAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (G01F00S01ListAdapter.ViewHolder) convertView.getTag();
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
            default:
                break;
        }
        return 0;
    }
}
