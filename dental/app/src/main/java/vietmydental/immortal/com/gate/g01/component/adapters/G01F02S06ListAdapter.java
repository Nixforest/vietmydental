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
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

public class G01F02S06ListAdapter extends BaseAdapter {
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
        public ImageView imageNext;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private ArrayList<ConfigExtBean> list;
    private LayoutInflater inflater;


    public G01F02S06ListAdapter(ArrayList<ConfigExtBean> list, LayoutInflater inflater) {
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
            convertView = inflater.inflate(R.layout.list_item_g01_f00_s02, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String updating = DomainConst.CONTENT00555;

        if (item != null) {
            int imageId = 0;
            if (DomainConst.VMD_IMG_LIST.get(item.getId()) != null) {
                imageId = DomainConst.VMD_IMG_LIST.get(item.getId());
            }
            switch (item.getId()) {

                case DomainConst.ITEM_TEETH_INFO:
                    imageId = CommonProcess.getImageId(DomainConst.ITEM_TEETH_INFO);
                    String name = item.getName();
                    String value = DomainConst.CONTENT00555;
                    if (item.getDataExt().size() == 1) {
                        value = item.getDataExt().get(0).getName();
                    } else if (item.getDataExt().size() > 1) {
                        name = DomainConst.CONTENT00575;
                        value = DomainConst.BLANK;
                    } else {
                        holder.textValue.setTextColor(Color.RED);
                    }
                    holder.textView.setText(name);
                    holder.textValue.setText(value);
                    holder.textDetail.setVisibility(View.GONE);
                    break;
                default:
                    holder.textView.setText(item.getName());
                    holder.textDetail.setVisibility(View.GONE);
                    holder.textValue.setText(item.getDataStr());
                    holder.imageNext.setVisibility(View.GONE);

                    if (item.getDataStr().isEmpty()) {
                        holder.textValue.setTextColor(Color.RED);
                        holder.textValue.setText(updating);
                    }
                    break;
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
}
