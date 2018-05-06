package vietmydental.immortal.com.gate.g01.component.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

public class G01F01S02ListAdapter extends BaseAdapter {
    public static class ViewHolder {
        @BindView(R.id.text)
        public TextView textView;
        @BindView(R.id.textDetail)
        public TextView textDetail;
        @BindView(R.id.textValue)
        public TextView textValue;
        @BindView(R.id.image)
        public ImageView imageView;
        @BindView(R.id.imgNext)
        public ImageView imgNext;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private ArrayList<ConfigExtBean> list;
    private LayoutInflater inflater;


    public G01F01S02ListAdapter(ArrayList<ConfigExtBean> list, LayoutInflater inflater) {
        this.list = list;
        this.inflater = inflater;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ConfigExtBean getItem(int position) {
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
        ConfigExtBean item = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_g01_f01_s01, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (item != null) {
            if (item.getId().equals(DomainConst.ITEM_RECORD_NUMBER)
                    && item.getName().equals("Bổ sung số bệnh án")) {
                holder.textView.setTextColor(Color.RED);
            }
            holder.textView.setText(item.getName());
            holder.textDetail.setVisibility(View.GONE);
            holder.imgNext.setVisibility(View.GONE);
            holder.textValue.setText("");
//            int imageId = DomainConst.VMD_IMG_LIST.get(item.getId());
//
//            if (imageId != 0) {
//                holder.imageView.setImageResource(imageId);
//                holder.imageView.setVisibility(View.VISIBLE);
//            } else {
//                holder.imageView.setVisibility(View.GONE);
//            }
            holder.imageView.setVisibility(View.GONE);
        }
        return convertView;
    }
}
