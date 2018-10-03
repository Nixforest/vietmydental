package vietmydental.immortal.com.gate.component.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.R;
//++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
public class ReceiptAdapter extends ArrayAdapter<ReceiptBean> {

    private Context context;
    private int resource;
    private List<ReceiptBean> arrReceipt;

    public ReceiptAdapter(Context context, int resource, ArrayList<ReceiptBean> arrReceipt) {
        super(context, resource, arrReceipt);
        this.context = context;
        this.resource = resource;
        this.arrReceipt = arrReceipt;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_g02_f00_s03, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvDoctorName = (TextView) convertView.findViewById(R.id.tv_doctor_name);
            viewHolder.tvCashierName = (TextView) convertView.findViewById(R.id.tv_cashier_name);
            viewHolder.tvDiscount = (TextView) convertView.findViewById(R.id.tv_discount);
            viewHolder.tvFinal = (TextView) convertView.findViewById(R.id.tv_final);
            viewHolder.tvTotal = (TextView) convertView.findViewById(R.id.tv_total);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ReceiptBean receipt = arrReceipt.get(position);
        if ( receipt.getData().size() > 0){
            for(int i = 0; i < receipt.getData().size(); i++){
                switch (receipt.getData().get(i).getId()){
                    case DomainConst.ITEM_NAME:  viewHolder.tvName.setText(receipt.getData().get(i).getData());
                        break;
                    case DomainConst.ITEM_DOCTOR:  viewHolder.tvDoctorName.setText(receipt.getData().get(i).getData());
                        break;
                    case DomainConst.ITEM_RECEIPTIONIST:  viewHolder.tvCashierName.setText(receipt.getData().get(i).getData());
                        break;
                    case DomainConst.ITEM_DISCOUNT:  viewHolder.tvDiscount.setText(receipt.getData().get(i).getData());
                        break;
                    case DomainConst.ITEM_TOTAL:  viewHolder.tvTotal.setText(receipt.getData().get(i).getData());
                        break;
                    case DomainConst.ITEM_FINAL:  viewHolder.tvFinal.setText(receipt.getData().get(i).getData());
                        break;
                    default:
                        break;
                }
            }
        }
        return convertView;
    }

    public class ViewHolder {
        TextView tvName, tvDoctorName, tvCashierName, tvDiscount, tvFinal, tvTotal;

    }
}
//-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.