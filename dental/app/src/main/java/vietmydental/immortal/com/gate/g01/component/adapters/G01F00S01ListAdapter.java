package vietmydental.immortal.com.gate.g01.component.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.g01.model.CustomerBean;
import vietmydental.immortal.com.vietmydental.R;

/**
 * TODO: document your custom view class.
 */
public class G01F00S01ListAdapter extends BaseAdapter {
    public class ViewHolder {
        @BindView(R.id.text)
        TextView textView;
        @BindView(R.id.textDetail)
        TextView textDetailView;
        @BindView(R.id.image)
        ImageView imageView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    public G01F00S01ListAdapter(ArrayList<CustomerBean> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }

    private ArrayList<CustomerBean> list;
    private LayoutInflater inflater;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CustomerBean getItem(int position) {
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
        CustomerBean item = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_g01_f00_s01, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {
            holder.textView.setText(item.getName());
            holder.textDetailView.setText(item.getAddress());
            int imageId = R.drawable.patient;
            if (!item.getGender().equals("Nam")) {
                imageId = R.drawable.patient_female;
            }
            if (imageId != 0) {
                holder.imageView.setImageResource(imageId);
                holder.imageView.setVisibility(View.VISIBLE);
            } else {
                holder.imageView.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

//    @DrawableRes
//    int getImageFromConfigId(String id) {
//        switch (id) {
//            case DomainConst.MENU_ID_LIST.HOME:
//                return R.drawable.ic_menu_home;
//            case DomainConst.MENU_ID_LIST.USER_PROFILE:
//                return R.drawable.ic_menu_profile;
//            case DomainConst.MENU_ID_LIST.CUSTOMER_LIST:
//                return R.drawable.ic_menu_family;
//            case DomainConst.MENU_ID_LIST.CONFIGURATION:
//                return R.drawable.configuration;
//            case DomainConst.MENU_ID_LIST.LOGOUT:
//                return R.drawable.logoutitem;
//            case DomainConst.MENU_ID_LIST.LOGIN:
//                return R.drawable.login;
//            default:
//                break;
//        }
//        return 0;
//    }
}
