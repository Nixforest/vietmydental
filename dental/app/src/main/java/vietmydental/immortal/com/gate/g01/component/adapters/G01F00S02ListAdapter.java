package vietmydental.immortal.com.gate.g01.component.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.vietmydental.R;

public class G01F00S02ListAdapter extends BaseAdapter {
    /** Type of row: Item */
    private static final int TYPE_ITEM = 0;
    /** Type of row: Header */
    private static final int TYPE_HEADER = 1;

    /** Data */
    private ArrayList<ConfigExtBean> mData = new ArrayList<>();
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
    public void addItem(final ConfigExtBean item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    /**
     * Add new section
     * @param item Section header data
     */
    public void addSectionHeaderItem(final ConfigExtBean item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
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
        return mData.size();
    }

    @Override
    public ConfigExtBean getItem(int position) {
        return mData.get(position);
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
                    convertView = mInflater.inflate(R.layout.row_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.txtName);
                    holder.txtValue = (TextView) convertView.findViewById(R.id.txtValue);
                    break;
                case TYPE_HEADER:
                    convertView = mInflater.inflate(R.layout.header_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(rowType == TYPE_ITEM){
            holder.textView.setText(mData.get(position).getName());
//            holder.txtValue.setText(""+mData.get(position).getAmount());
        }else if(rowType == TYPE_HEADER){
            holder.textView.setText(mData.get(position).getName());
        }


        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public TextView txtValue;
    }
}
