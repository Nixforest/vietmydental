package vietmydental.immortal.com.gate.component.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.
public class DailyReportBranchAdapter extends ArrayAdapter<ReceiptBean> {

    private Context context;
    private int resource;
    private List<ReceiptBean> arrReceipt;

    public DailyReportBranchAdapter(Context context, int resource, ArrayList<ReceiptBean> arrReceipt) {
        super(context, resource, arrReceipt);
        this.context = context;
        this.resource = resource;
        this.arrReceipt = arrReceipt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DailyReportBranchAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_g03_f00_s01, parent, false);
            viewHolder = new DailyReportBranchAdapter.ViewHolder();
//            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
//            viewHolder.tvDoctorName = (TextView) convertView.findViewById(R.id.tv_doctor_name);
//            viewHolder.tvCashierName = (TextView) convertView.findViewById(R.id.tv_cashier_name);
//            viewHolder.tvDiscount = (TextView) convertView.findViewById(R.id.tv_discount);
            viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.imgStatus = (ImageView) convertView.findViewById(R.id.img_status);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DailyReportBranchAdapter.ViewHolder) convertView.getTag();
        }
        ReceiptBean receipt = arrReceipt.get(position);
        viewHolder.tvDate.setText(receipt.getName());
        if ( receipt.getData().size() > 0){
            for(int i = 0; i < receipt.getData().size(); i++){
                switch (receipt.getData().get(i).getId()){
                    case DomainConst.ITEM_STATUS_TEXT:  viewHolder.tvStatus.setText(receipt.getData().get(i).getData());
                        if (receipt.getData().get(i).getData().equals("Chưa tạo")) {
                            viewHolder.imgStatus.setImageResource(R.drawable.menu_add);

                        }
                        else if(receipt.getData().get(i).getData().equals("Đã duyệt")){
                            viewHolder.imgStatus.setImageResource(R.drawable.add_1);
                        }
                        else if(receipt.getData().get(i).getData().equals("Chưa duyệt")){
                            viewHolder.imgStatus.setImageResource(R.drawable.address);

                        }
                        else if(receipt.getData().get(i).getData().equals("Không duyệt")){
                            viewHolder.imgStatus.setImageResource(R.drawable.add_medical_history);
                        }
                        //++ BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
                        else{
                            viewHolder.imgStatus.setImageResource(R.drawable.address_icon);
                        }
                        //-- BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
                        break;
                    default:
                        break;
                }
            }
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tvDate, tvStatus;
        ImageView imgStatus;
    }
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.