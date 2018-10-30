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
public class DailyReportListAdapter extends ArrayAdapter<ReceiptBean> {

    private Context context;
    private int resource;
    private List<ReceiptBean> arrReceipt;

    public DailyReportListAdapter(Context context, int resource, ArrayList<ReceiptBean> arrReceipt) {
        super(context, resource, arrReceipt);
        this.context = context;
        this.resource = resource;
        this.arrReceipt = arrReceipt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DailyReportListAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_g03_f00_s01, parent, false);
            viewHolder = new DailyReportListAdapter.ViewHolder();
//            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
//            viewHolder.tvDoctorName = (TextView) convertView.findViewById(R.id.tv_doctor_name);
//            viewHolder.tvCashierName = (TextView) convertView.findViewById(R.id.tv_cashier_name);
//            viewHolder.tvDiscount = (TextView) convertView.findViewById(R.id.tv_discount);
            viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tv_status);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.imgStatus = (ImageView) convertView.findViewById(R.id.img_status);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DailyReportListAdapter.ViewHolder) convertView.getTag();
        }
        ReceiptBean receipt = arrReceipt.get(position);
        viewHolder.tvDate.setText(receipt.getName());
        //++ BUG0127-IMT (KhoiVT 20181030) [Android] Update status icon for daily report.
        if ( receipt.getData().size() > 0){
            for(int i = 0; i < receipt.getData().size(); i++){
                switch (receipt.getData().get(i).getId()){
                    case DomainConst.ITEM_STATUS:
                        switch (receipt.getData().get(i).getData()) {
                            case DomainConst.REPORT_STATUS_NEW:
                                viewHolder.imgStatus.setImageResource(R.drawable.report_status_new);
                                break;
                            case DomainConst.REPORT_STATUS_ACTIVE_:
                                viewHolder.imgStatus.setImageResource(R.drawable.report_status_active);
                                break;
                            case DomainConst.REPORT_STATUS_REQUEST:
                                viewHolder.imgStatus.setImageResource(R.drawable.report_status_request);
                                break;
                            case DomainConst.REPORT_STATUS_APPROVED:
                                viewHolder.imgStatus.setImageResource(R.drawable.report_status_approved);
                                break;
                            case DomainConst.REPORT_STATUS_CANCEL:
                                viewHolder.imgStatus.setImageResource(R.drawable.report_status_cancel);
                                break;
                            case DomainConst.REPORT_STATUS_SHOULD_REVIEW:
                                viewHolder.imgStatus.setImageResource(R.drawable.report_status_request);
                                break;
                            //++ BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
                            default:
                                viewHolder.imgStatus.setImageResource(R.drawable.report_status_new);
                                break;
                        }
                        //-- BUG0094_1-IMT (KhoiVT20180910) [Android] Fix bug Daily Report.
                        break;
                    case DomainConst.ITEM_STATUS_TEXT:  viewHolder.tvStatus.setText(receipt.getData().get(i).getData());
                    default:
                        break;
                }
            }
        }
        //-- BUG0127-IMT (KhoiVT 20181030) [Android] Update status icon for daily report.
        return convertView;
    }

    public class ViewHolder {
        TextView tvDate, tvStatus;
        ImageView imgStatus;
    }
}
//-- BUG0094-IMT (KhoiVT20180910) [Android] Daily Report.