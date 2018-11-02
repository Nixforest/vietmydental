package vietmydental.immortal.com.gate.g03.component.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.component.adapters.DailyReportListAdapter;
import vietmydental.immortal.com.gate.g02.model.PropertyBean;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0094-IMT (KhoiVT20180910) [Android] extend when update value for api
public class G03F00S03ExtListAdapter extends ArrayAdapter<PropertyBean> {

    private Context context;
    private int resource;
    private List<PropertyBean> arrProperty;

    public G03F00S03ExtListAdapter(Context context, int resource, ArrayList<PropertyBean> arrReceipt) {
        super(context, resource, arrReceipt);
        this.context = context;
        this.resource = resource;
        this.arrProperty = arrReceipt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        G03F00S03ExtListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_g03_f00_s01, parent, false);
            viewHolder = new G03F00S03ExtListAdapter.ViewHolder();
            viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.imgStatus = (ImageView) convertView.findViewById(R.id.img_status);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (G03F00S03ExtListAdapter.ViewHolder) convertView.getTag();
        }
        PropertyBean propertyBean = arrProperty.get(position);
        viewHolder.tvDate.setText(propertyBean.getName());
        viewHolder.tvStatus.setText(propertyBean.getData());
        int imageId = 0;
        if (DomainConst.VMD_IMG_LIST.get(propertyBean.getId()) != null) {
            imageId = DomainConst.VMD_IMG_LIST.get(propertyBean.getId());
        }

        if (imageId != 0) {
            viewHolder.imgStatus.setImageResource(imageId);
            viewHolder.imgStatus.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgStatus.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tvDate, tvStatus;
        ImageView imgStatus;
    }
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] extend when update value for api