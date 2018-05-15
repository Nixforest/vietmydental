package ir.mirrajabi.searchdialog;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import ir.mirrajabi.searchdialog.core.BaseFilter;
import ir.mirrajabi.searchdialog.core.FilterResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by MADNESS on 5/14/2017.
 */

public class SimpleSearchFilter<T extends Searchable> extends BaseFilter {
    private ArrayList<T> mItems;
    private FilterResultListener mFilterResultListener;
    private boolean mCheckLCS;
    private final float mAccuracyPercentage;

    public SimpleSearchFilter(List<T> objects, @NonNull FilterResultListener filterResultListener,
                              boolean checkLCS, float accuracyPercentage) {
        mFilterResultListener = filterResultListener;
        mCheckLCS = checkLCS;
        mAccuracyPercentage = accuracyPercentage;
        mItems = new ArrayList<>();
        synchronized (this) {
            mItems.addAll(objects);
        }
    }

    public SimpleSearchFilter(List<T> objects, @NonNull FilterResultListener filterResultListener) {
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
//                if (object.getTitle().toLowerCase().contains(filterSeq))
                if (contains(filterSeq, object.getTitle()))
                    filter.add(object);
//                else if (mCheckLCS)
//                    if (StringsHelper.lcs(object.getTitle(), filterSeq).length()
//                            > object.getTitle().length() * mAccuracyPercentage)
//                        filter.add(object);

            if (filter.size() == 0) {
                T obj = (T)cloneObject(mItems.get(0));
                obj.setId("XXX");
                obj.setName("Tạo mới");
                filter.add(obj);
            }
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

    /** String array to remove sign in Vietnamese. */
    private static String[] vietnameseSigns = new String[]{"aAeEoOuUiIdDyY", "áàạảãâấầậẩẫăắằặẳẵ", "ÁÀẠẢÃÂẤẦẬẨẪĂẮẰẶẲẴ",
            "éèẹẻẽêếềệểễ", "ÉÈẸẺẼÊẾỀỆỂỄ", "óòọỏõôốồộổỗơớờợởỡ", "ÓÒỌỎÕÔỐỒỘỔỖƠỚỜỢỞỠ", "úùụủũưứừựửữ", "ÚÙỤỦŨƯỨỪỰỬỮ",
            "íìịỉĩ", "ÍÌỊỈĨ", "đ", "Đ", "ýỳỵỷỹ", "ÝỲỴỶỸ"};

    /**
     * Method remove sign in Vietnamese string.
     * @param text String to remove sign
     * @return String after remove sign
     */
    public static String removeSign4VietNameseString(String text) {
        for (int i = 1; i < vietnameseSigns.length; i++) {
            for (int j = 0; j < vietnameseSigns[i].length(); j++) {
                text = text.replace(vietnameseSigns[i].charAt(j), vietnameseSigns[0].charAt(i - 1));
            }
        }
        return text;
    }
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }

    private static Object cloneObject(Object obj){
        try{
            Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(clone, field.get(obj));
            }
            return clone;
        }catch(Exception e){
            return null;
        }
    }

    public boolean contains(String keyword, String title) {
        String formedKeywork = removeAccent(keyword).toLowerCase();
        String searchString = removeAccent(title).toLowerCase();
        return searchString.contains(formedKeywork);
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        ArrayList<T> filtered = (ArrayList<T>) results.values;
        mFilterResultListener.onFilter(filtered);
        doAfterFiltering();
    }
}