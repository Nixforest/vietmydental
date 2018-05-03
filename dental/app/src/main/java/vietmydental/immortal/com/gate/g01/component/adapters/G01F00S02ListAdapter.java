package vietmydental.immortal.com.gate.g01.component.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import vietmydental.immortal.com.gate.g01.model.TreatmentBean;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

public class G01F00S02ListAdapter extends BaseAdapter {
    /** Type of row: Item */
    private static final int TYPE_ITEM = 0;
    /** Type of row: Header */
    private static final int TYPE_HEADER = 1;

    /** Data */
//    private ArrayList<ConfigExtBean> mData = new ArrayList<>();
    private List<List<ConfigExtBean>> mData = new ArrayList<>();
    /** Section header */
    private TreeSet<Integer> sectionHeader = new TreeSet<>();
    /** Layout handler */
    private LayoutInflater mInflater;

    /**
     * Constructor
     * @param context
     */
    public G01F00S02ListAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Add new item
     * @param item Item data
     */
    public void addItem(final ConfigExtBean item, int section) {
//        mData.add(item);
        mData.get(section).add(item);
        notifyDataSetChanged();
    }

    /**
     * Add new section
     * @param item Section header data
     */
    public void addSectionHeaderItem(final ConfigExtBean item, int section) {
        mData.add(new ArrayList<ConfigExtBean>());
        mData.get(section).add(item);
        int size = 0;
        for (List<ConfigExtBean> iterator :
                mData) {
            size += iterator.size();
        }
        sectionHeader.add(size - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        int size = 0;
        for (List<ConfigExtBean> iterator :
                mData) {
            size += iterator.size();
        }

        return size;
    }

    @Override
    public ConfigExtBean getItem(int position) {
        int index = -1;
        for (List<ConfigExtBean> iterator :
                mData) {
            for (ConfigExtBean bean :
                    iterator) {
                index++;
                if (index == position) {
                    return bean;
                }
            }
        }
        return new ConfigExtBean();
//        return mData.get(position);
    }

    /**
     * Get section id string from position
     * @param position Position
     * @return Value of section id
     */
    public String getSectionId(int position) {
        int index = -1;
        for (List<ConfigExtBean> iterator :
                mData) {
            for (ConfigExtBean bean :
                    iterator) {
                index++;
                if (index == position) {
                    return iterator.get(0).getId();
                }
            }
        }
        return "";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get view object
     * @param position Position index
     * @param convertView View object
     * @param parent View group
     * @return View object
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.list_item_g01_f00_s02, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    holder.textValue = (TextView) convertView.findViewById(R.id.textValue);
                    holder.textDetail = (TextView) convertView.findViewById(R.id.textDetail);
                    holder.imageView = (ImageView) convertView.findViewById(R.id.image);
                    break;
                case TYPE_HEADER:
                    convertView = mInflater.inflate(R.layout.header_item_g01_f00_s02, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ConfigExtBean data = getItem(position);
        if(rowType == TYPE_ITEM){
            int imageId = 0;
            switch (getSectionId(position)) {
                case DomainConst.GROUP_MEDICAL_RECORD:
                    switch (data.getId()) {
                        case DomainConst.ITEM_NAME:
                            imageId = DomainConst.VMD_IMG_LIST.get(DomainConst.ITEM_NAME);

                            holder.textView.setText(data.getName());
                            holder.textDetail.setText(data.getDataStr());
                            holder.textValue.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_BIRTHDAY:
                            imageId = DomainConst.VMD_IMG_LIST.get(DomainConst.ITEM_BIRTHDAY);

                            holder.textView.setText(data.getName());
                            holder.textDetail.setText(data.getDataStr());
                            holder.textValue.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_MEDICAL_HISTORY:
                            imageId = DomainConst.VMD_IMG_LIST.get(DomainConst.ITEM_MEDICAL_HISTORY);

                            holder.textView.setText(data.getName());
                            holder.textDetail.setVisibility(View.GONE);
                            holder.textValue.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_UPDATE_DATA:
                            imageId = DomainConst.VMD_IMG_LIST.get(DomainConst.ITEM_UPDATE_DATA);

                            holder.textView.setText(data.getName());
                            holder.textDetail.setVisibility(View.GONE);
                            holder.textValue.setVisibility(View.GONE);
                            break;
                        default:
                            holder.textView.setText(data.getName());
                            break;
                    }
                    break;
                case DomainConst.GROUP_TREATMENT:
                    switch (data.getId()) {
                        case DomainConst.ITEM_UPDATE_DATA:
                            imageId = R.drawable.add_1;

                            holder.textView.setText(data.getName());
                            holder.textDetail.setVisibility(View.GONE);
                            holder.textValue.setVisibility(View.GONE);
                            break;
                        default:
//                            holder.textView.setText(data.getName());
                            TreatmentBean bean = new TreatmentBean(data.getDataObj());
                            holder.textView.setText(bean.getStart_date());
                            holder.textDetail.setVisibility(View.GONE);
                            holder.textValue.setText(data.getName());
                            imageId = R.drawable.status_schedule;
                            if (bean.getStatus().equals(DomainConst.TREATMENT_SCHEDULE_COMPLETED)) {
                                imageId = R.drawable.status_treatment;
                            }
                            break;
                    }
                    break;
                default:
                    break;
            }

            if (imageId != 0) {
                holder.imageView.setImageResource(imageId);
                holder.imageView.setVisibility(View.VISIBLE);
            } else {
                holder.imageView.setVisibility(View.GONE);
            }
//            holder.txtValue.setText(""+mData.get(position).getAmount());
        } else if(rowType == TYPE_HEADER){
            holder.textView.setText(data.getName());
        }


        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public TextView textDetail;
        public TextView textValue;
        public ImageView imageView;
    }
}
