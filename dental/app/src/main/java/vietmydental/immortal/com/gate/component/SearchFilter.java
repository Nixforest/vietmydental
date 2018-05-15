package vietmydental.immortal.com.gate.component;

import android.support.annotation.NonNull;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import ir.mirrajabi.searchdialog.SimpleSearchFilter;
import ir.mirrajabi.searchdialog.core.BaseFilter;
import ir.mirrajabi.searchdialog.core.FilterResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Search filter class
 * @param <T> Searchable class
 */
public class SearchFilter<T extends Searchable> extends BaseFilter {
    private ArrayList<T> mItems;
    /**
     * Constructor
     * @param objects List data
     * @param filterResultListener Filter result listener
     * @param checkLCS
     * @param accuracyPercentage
     */
    public SearchFilter(List<T> objects, @NonNull FilterResultListener filterResultListener,
                        boolean checkLCS, float accuracyPercentage) {
//        super(objects, filterResultListener, checkLCS, accuracyPercentage);
        mItems = new ArrayList<>();
        synchronized (this) {
            mItems.addAll(objects);
        }
    }

    /**
     * Constructor
     * @param objects List data
     * @param filterResultListener Filter result listener
     */
    public SearchFilter(List<T> objects, @NonNull FilterResultListener filterResultListener) {
        this(objects, filterResultListener, false, 0);
    }
    @Override
    protected FilterResults performFiltering(CharSequence chars) {
        doBeforeFiltering();
        String filterSeq = chars.toString().toLowerCase();
        FilterResults result = new FilterResults();
        if (filterSeq != null && filterSeq.length() > 0) {
            ArrayList<T> filter = new ArrayList<>();
            for (T object : mItems)
                if (object.getTitle().toLowerCase().contains(filterSeq))
                    filter.add(object);

            result.values = filter;
            result.count = filter.size();
        } else {
            synchronized (this) {
                result.values = mItems;
                result.count = mItems.size();
            }
        }
        return result;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

    }
}
