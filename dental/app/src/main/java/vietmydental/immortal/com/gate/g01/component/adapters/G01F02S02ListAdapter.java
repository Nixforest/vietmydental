package vietmydental.immortal.com.gate.g01.component.adapters;

import android.content.Context;
import android.graphics.Color;
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
import vietmydental.immortal.com.gate.utils.CommonProcess;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;

public class G01F02S02ListAdapter extends BaseAdapter {
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
    public G01F02S02ListAdapter(Context context) {
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
                    holder.imgNext = convertView.findViewById(R.id.imgNext);
                    break;
                case TYPE_HEADER:
                    convertView = mInflater.inflate(R.layout.header_item_g01_f00_s02, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    holder.textMore = convertView.findViewById(R.id.tv_more);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ConfigExtBean data = getItem(position);
        String updating = "Đang cập nhật";
        if(rowType == TYPE_ITEM){
            int imageId = 0;
            switch (getSectionId(position)) {
                case "0":
                    switch (data.getId()) {
                        case DomainConst.ITEM_START_DATE:
//                            imageId = DomainConst.VMD_IMG_LIST.get(DomainConst.ITEM_START_DATE);
                            imageId = CommonProcess.getImageId(DomainConst.ITEM_START_DATE);

                            holder.textView.setText(data.getName());
                            holder.textValue.setText(data.getDataStr());
                            holder.textDetail.setVisibility(View.GONE);
                            holder.imgNext.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_INSURANCE:
//                            imageId = DomainConst.VMD_IMG_LIST.get(DomainConst.ITEM_INSURANCE);
                            imageId = CommonProcess.getImageId(DomainConst.ITEM_INSURANCE);

                            holder.textView.setText(data.getName());
                            holder.textValue.setText(data.getDataStr());
                            holder.textDetail.setVisibility(View.GONE);
                            holder.imgNext.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_PATHOLOGICAL:
//                            imageId = DomainConst.VMD_IMG_LIST.get(DomainConst.ITEM_PATHOLOGICAL);
                            imageId = CommonProcess.getImageId(DomainConst.ITEM_PATHOLOGICAL);

                            holder.textView.setText(data.getName());
                            if (data.getDataStr().isEmpty()) {
                                holder.textValue.setText(updating);
                                holder.textValue.setTextColor(Color.RED);
                            } else {
                                holder.textValue.setText(data.getDataStr());
                            }
                            holder.textDetail.setVisibility(View.GONE);
                            holder.imgNext.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_DIAGNOSIS:
//                            imageId = DomainConst.VMD_IMG_LIST.get(DomainConst.ITEM_DIAGNOSIS);
                            imageId = CommonProcess.getImageId(DomainConst.ITEM_DIAGNOSIS);

                            holder.textView.setText(data.getName());
                            if (data.getDataStr().isEmpty()) {
                                holder.textValue.setText(updating);
                                holder.textValue.setTextColor(Color.RED);
                            } else {
                                holder.textValue.setText(data.getDataStr());
                            }
                            holder.textDetail.setVisibility(View.GONE);
                            holder.imgNext.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_HEALTHY:
//                            imageId = DomainConst.VMD_IMG_LIST.get(DomainConst.ITEM_HEALTHY);
                            imageId = CommonProcess.getImageId(DomainConst.ITEM_HEALTHY);

                            holder.textView.setText(data.getName());
                            holder.textDetail.setVisibility(View.GONE);
//                            holder.textValue.setVisibility(View.GONE);
                            holder.textValue.setText("");
                            break;
                        case DomainConst.ITEM_TEETH:
                            imageId = CommonProcess.getImageId(DomainConst.ITEM_TEETH);

                            holder.textView.setText(data.getName());
                            if (data.getDataStr().isEmpty()) {
                                holder.textValue.setText(updating);
                                holder.textValue.setTextColor(Color.RED);
                            } else {
                                holder.textValue.setText(data.getDataStr());
                            }
                            holder.textDetail.setVisibility(View.GONE);
                            holder.imgNext.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_TREATMENT:
                            imageId = CommonProcess.getImageId(DomainConst.ITEM_TREATMENT);

                            holder.textView.setText(data.getName());
                            if (data.getDataStr().isEmpty()) {
                                holder.textValue.setText(updating);
                                holder.textValue.setTextColor(Color.RED);
                            } else {
                                holder.textValue.setText(data.getDataStr());
                            }
                            holder.textDetail.setVisibility(View.GONE);
                            holder.imgNext.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_TIME:
                            imageId = CommonProcess.getImageId(DomainConst.ITEM_TIME);

                            holder.textView.setText(data.getName());
                            if (data.getDataStr().isEmpty()) {
                                holder.textValue.setText(updating);
                                holder.textValue.setTextColor(Color.RED);
                            } else {
                                holder.textValue.setText(data.getDataStr());
                            }
                            holder.textDetail.setVisibility(View.GONE);
                            holder.imgNext.setVisibility(View.GONE);
                            break;
                        case DomainConst.ITEM_NOTE:
                            imageId = CommonProcess.getImageId(DomainConst.ITEM_NOTE);

                            holder.textView.setText(data.getName());
                            holder.textValue.setText(data.getDataStr());
                            holder.textDetail.setVisibility(View.GONE);
                            holder.imgNext.setVisibility(View.GONE);
                            break;
                        default:
                            holder.textView.setText(data.getName());
                            break;
                    }
                    break;
                case DomainConst.ITEM_DETAILS:
                    holder.textView.setText(data.getValueByIdExt(DomainConst.ITEM_START_DATE));
                    if (data.getName().isEmpty()) {
                        holder.textDetail.setText(updating);
                        holder.textDetail.setTextColor(Color.RED);
                    } else {
                        holder.textDetail.setText(data.getName());
                    }

//                    holder.textValue.setVisibility(View.GONE);
                    holder.textValue.setText("");
                    String status = data.getValueByIdExt(DomainConst.ITEM_STATUS);
                    if (status.equals(DomainConst.TREATMENT_SCHEDULE_DETAIL_SCHEDULE)
                        || status.equals(DomainConst.TREATMENT_SCHEDULE_DETAIL_ACTIVE)) {
                        imageId = R.drawable.status_schedule;
                    } else if (status.equals(DomainConst.TREATMENT_SCHEDULE_DETAIL_COMPLETED)) {
                        imageId = R.drawable.status_treatment;
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
            switch (getSectionId(position)) {
                case "0":
                    holder.textMore.setVisibility(View.GONE);
                    break;
                case DomainConst.ITEM_DETAILS:
                    holder.textMore.setText(DomainConst.CONTENT00065);
                    break;
                default:
                    break;
            }
        }


        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public TextView textDetail;
        public TextView textValue;
        public ImageView imageView;
        public ImageView imgNext;
        public TextView textMore;
    }
}
