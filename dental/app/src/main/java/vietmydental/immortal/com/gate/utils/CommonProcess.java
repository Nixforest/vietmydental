package vietmydental.immortal.com.gate.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import vietmydental.immortal.com.gate.api.BaseResponse;
import vietmydental.immortal.com.gate.g00.view.G00LoginActivity;
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
     * Show message dialog
     *
     * @param context  Current context
     * @param title    Title of
     * @param message
     * @param listener
     */
    public static void showMessage(Context context, String title, String message, final CommonProcess.ConfirmDialogCallback listener) {
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
     *
     * @param context Current context
     * @param message Message to show
     */
    public static void showErrorMessage(Activity context, String message) {
        showErrorMessage(context, message, null);
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
     * Get list config
     * @param data Json object data
     * @param key Key value
     * @return List of config bean object
     */
    public static ArrayList<ConfigBean> getListConfig(JsonObject data, String key) {
        ArrayList<ConfigBean> retVal = new ArrayList<>();
        try {
            JsonArray array = data.getAsJsonArray(key);
            Gson gson = new Gson();
            retVal = gson.fromJson(array.toString(), new TypeToken<ArrayList<ConfigBean>>(){}.getType());
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
     * Get current date string
     * @param format Format string
     * @return Date string
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }
}
