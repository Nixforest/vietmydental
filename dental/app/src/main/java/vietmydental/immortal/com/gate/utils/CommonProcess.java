package vietmydental.immortal.com.gate.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.InputType;
import android.widget.EditText;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.g00.view.G00LoginActivity;
import vietmydental.immortal.com.gate.g02.model.PropertyBean;
import vietmydental.immortal.com.gate.g02.model.ReceiptBean;
import vietmydental.immortal.com.gate.model.BaseModel;
import vietmydental.immortal.com.gate.model.ConfigBean;
import vietmydental.immortal.com.gate.model.ConfigExtBean;
import vietmydental.immortal.com.vietmydental.R;

public class CommonProcess {
    /**
     * Confirm dialog callback.
     */
    public static class ConfirmDialogCallback {
        /**
         * Handle confirm.
         */
        public void onConfirmed() {
        }

        /**
         * Handle cancel.
         */
        public void onCancel() {
        }
    }

    /**
     * Show message.
     *
     * @param activity Current activity
     * @param response Response data
     */
    public static void showErrorMessage(final Activity activity, BaseResponse response) {
        try {
            int statusCode = response.getCode();
            if (statusCode == ErrorCode.ERROR_LOST_CONNECTION) {
                showErrorMessage(activity, activity.getString(R.string.CONTENT00046));
            } else if (statusCode == ErrorCode.ERROR_UNKNOWN) {
                showErrorMessage(activity, activity.getString(R.string.CONTENT00047));
            } else if (statusCode == BaseResponse.API_RESPONSE_CODE_UNAUTHORIZED) {
                CommonProcess.ConfirmDialogCallback listener = new CommonProcess.ConfirmDialogCallback() {
                    @Override
                    public void onConfirmed() {
                        try {
                            BaseModel.getInstance().setToken(activity, null);
                            Intent intent = new Intent(activity, G00LoginActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                showErrorMessage(activity, response.getMessage(), listener);
            } else {
                showErrorMessage(activity, response.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show message.
     *
     * @param context  Current context
     * @param message  Message to show
     * @param listener Listener
     */
    public static void showErrorMessage(Context context, String message, final CommonProcess.ConfirmDialogCallback listener) {
//        new AlertDialog.Builder(context)
//                .setTitle(context.getString(R.string.CONTENT00048))
//                .setMessage(Html.fromHtml(message))
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (listener != null) {
//                            listener.onConfirmed();
//                        }
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
        showMessage(context, context.getString(R.string.CONTENT00048), message, listener);
    }

    /**
     * Show message.
     * @param context Current context
     * @param message Message to show
     * @param listener Listener
     */
    public static void showErrorMessage(Activity context, String message, final ConfirmDialogCallback listener) {
//        new AlertDialog.Builder(context)
//                .setTitle(context.getString(R.string.CONTENT00048))
//                .setMessage(Html.fromHtml(message))
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (listener != null) {
//                            listener.onConfirmed();
//                        }
//                    }
//                })
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .show();
        showMessage(context, context.getString(R.string.CONTENT00048), message, listener);
    }
    /**
     * Show message dialog
     *
     * @param context  Current context
     * @param title    Title of
     * @param message
     * @param listener
     */
    public static void showMessage(Context context, String title, String message, final ConfirmDialogCallback listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(Html.fromHtml(message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listener != null) {
                            listener.onConfirmed();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (listener != null) {
                            listener.onCancel();
                        }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    /**
     * Show message.
     * @param context Current context
     * @param message Message to show
     */
    public static void showErrorMessage(Activity context, String message) {
        showErrorMessage(context, message, null);
    }

    /**
     * Create input text alert
     * @param context Context
     * @param title Title of alert
     * @param value Value of input text
     * @return AlertDialog.Builder
     */
    public static AlertDialog.Builder createInputTextAlert(Context context, String title, String value, EditText inputView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        // Specify the type of input expected
        inputView.setInputType(InputType.TYPE_CLASS_TEXT);
        inputView.setText(value);
        builder.setView(inputView);
        return builder;
    }

    /**
     * Create input number alert
     * @param context Context
     * @param title Title of alert
     * @param value Value of input text
     * @return AlertDialog.Builder
     */
    public static AlertDialog.Builder createInputNumberAlert(Context context, String title, String value, EditText inputView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        // Specify the type of input expected
        inputView.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        inputView.setText(value);
        builder.setView(inputView);
        return builder;
    }

    /**
     * Show selection alert
     * @param context Context
     * @param title Title of alert
     * @param values Value of list selection
     * @param listener Listener when click item
     */
    public static void showSelectionAlert(Context context, String title, String[] values, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(values, listener);
        builder.setNegativeButton(DomainConst.CONTENT00202, null);
        builder.show();
    }

    /**
     * Show selection alert
     * @param context Context
     * @param title Title of alert
     * @param values Value of list selection
     * @param listener Listener when click item
     */
    public static void showMultiSelectionAlert(Context context, String title, String[] values, boolean[] selecteds,
                                               DialogInterface.OnMultiChoiceClickListener listener,
                                               DialogInterface.OnClickListener okListener,
                                               DialogInterface.OnClickListener cancelListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMultiChoiceItems(values, selecteds, listener);
        builder.setNegativeButton(DomainConst.CONTENT00202, cancelListener);
        builder.setPositiveButton(DomainConst.CONTENT00008, okListener);
        builder.show();
    }

    /**
     * Get json string
     * @param data Json object data
     * @param key Key value
     * @return Value of string
     */
    public static String getJsonString(JsonObject data, String key) {
        try {
            return data.get(key).getAsString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get json int value
     * @param data Json object data
     * @param key Key value
     * @return Value of int
     */
    public static int getJsonInt(JsonObject data, String key) {
        try {
            return data.get(key).getAsInt();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Get json object
     * @param data Json object data
     * @param key Key value
     * @return Value of json object
     */
    public static JsonObject getJsonObject(JsonObject data, String key) {
        try {
            return data.get(key).getAsJsonObject();
        } catch (Exception e) {
            return new JsonObject();
        }
    }

    /**
     * Get json array
     * @param data Json object data
     * @param key Key value
     * @return Value of json array
     */
    public static JsonArray getJsonArray(JsonObject data, String key) {
        try {
            return data.get(key).getAsJsonArray();
        } catch (Exception e) {
            return new JsonArray();
        }
    }

    /**
     * Get list config
     * @param data Json object data
     * @param key Key value
     * @return List of config bean object
     */
    //++ BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.
    public static ArrayList<ReceiptBean> getListReceipt(JsonObject data, String key) {
        ArrayList<ReceiptBean> retVal = new ArrayList<>();
        try {
            JsonArray array = data.getAsJsonArray(key);
//            Gson gson = new Gson();
//            retVal = gson.fromJson(array.toString(), new TypeToken<ArrayList<ConfigBean>>(){}.getType());
            for (JsonElement obj :
                    array) {
                retVal.add(new ReceiptBean(obj.getAsJsonObject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }
    //-- BUG0089-IMT (KhoiVT20180113) [Android] Statistic Screen.

    /**
     * Get list config
     * @param data Json object data
     * @param key Key value
     * @return List of config bean object
     */
    public static ArrayList<PropertyBean> getListProperty(JsonObject data, String key) {
        ArrayList<PropertyBean> retVal = new ArrayList<>();
        try {
            JsonArray array = data.getAsJsonArray(key);
//            Gson gson = new Gson();
//            retVal = gson.fromJson(array.toString(), new TypeToken<ArrayList<ConfigBean>>(){}.getType());
            for (JsonElement obj :
                    array) {
                retVal.add(new PropertyBean(obj.getAsJsonObject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     * Get list config
     * @param data Json object data
     * @param key Key value
     * @return List of config bean object
     */
    public static ArrayList<ConfigBean> getListConfig(JsonObject data, String key) {
        ArrayList<ConfigBean> retVal = new ArrayList<>();
        try {
            JsonArray array = data.getAsJsonArray(key);
//            Gson gson = new Gson();
//            retVal = gson.fromJson(array.toString(), new TypeToken<ArrayList<ConfigBean>>(){}.getType());
            for (JsonElement obj :
                    array) {
                retVal.add(new ConfigBean(obj.getAsJsonObject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     * Get list config
     * @param data Json object data
     * @param key Key value
     * @return List of config bean object
     */
    public static ArrayList<ConfigExtBean> getListConfigExt(JsonObject data, String key) {
        ArrayList<ConfigExtBean> retVal = new ArrayList<>();
        try {
            JsonArray array = data.getAsJsonArray(key);
            for (JsonElement obj :
                    array) {
                retVal.add(new ConfigExtBean(obj.getAsJsonObject()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     * Get configext value by id
     * @param list List to find
     * @param id Id of item
     * @return Value of data string
     */
    public static String getConfigExtValueById(ArrayList<ConfigExtBean> list, String id) {
        for (ConfigExtBean item :
                list) {
            if (item.getId().equals(id)) {
                return item.getDataStr();
            }
        }
        return "";
    }

    /**
     * Get configext object by id
     * @param list List to find
     * @param id Id of item
     * @return Value of data string
     */
    public static ConfigExtBean getConfigExtObjById(ArrayList<ConfigExtBean> list, String id) {
        for (ConfigExtBean item :
                list) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Set name for configext Object
     * @param list List to find
     * @param id Id of item
     * @param name Name value
     * @return True if set name success, False otherwise
     */
    public static boolean setConfigExtNameById(ArrayList<ConfigExtBean> list, String id, String name) {
        for (ConfigExtBean item :
                list) {
            if (item.getId().equals(id)) {
                item.setName(name);
                return true;
            }
        }
        return false;
    }

    /**
     * Set data str for configext Object
     * @param list List to find
     * @param id Id of item
     * @param value Value
     * @return True if set name success, False otherwise
     */
    public static boolean setConfigExtDataStrById(ArrayList<ConfigExtBean> list, String id, String value) {
        for (ConfigExtBean item :
                list) {
            if (item.getId().equals(id)) {
                item.setDataStr(value);
                return true;
            }
        }
        return false;
    }

    /**
     * Get current date string
     * @param format Format string
     * @return Date string
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    /**
     * Convert date to string with format
     * @param date Date value
     * @param format Format value
     * @return Date value as string with format
     */
    public static String getDateString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * Convert date string to date object with format
     * @param date Date value (as string)
     * @param format Format value
     * @return Date object with format convert from date string
     */
    public static Date getDateObject(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException ex) {
            return new Date();
        }
    }

    /**
     * Convert date value to calendar object
     * @param date Date value
     * @return Calendar value
     */
    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Get image id
     * @param key Key value
     * @return Image id
     */
    public static int getImageId(String key) {
        if (DomainConst.VMD_IMG_LIST.get(key) != null) {
            return DomainConst.VMD_IMG_LIST.get(key);
        }
        return 0;
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
    public static String upperCaseAllFirst(String value) {

        char[] array = value.toCharArray();
        // Uppercase first letter.
        array[0] = Character.toUpperCase(array[0]);

        // Uppercase all letters that follow a whitespace character.
        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }

        // Result.
        return new String(array);
    }
}
