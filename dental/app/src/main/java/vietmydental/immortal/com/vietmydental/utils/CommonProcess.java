package vietmydental.immortal.com.vietmydental.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;

import java.util.List;

import vietmydental.immortal.com.vietmydental.LoginActivity;
import vietmydental.immortal.com.vietmydental.R;
import vietmydental.immortal.com.vietmydental.api.BaseRequest;
import vietmydental.immortal.com.vietmydental.model.ConfigBean;

/**
 * Created by nguyenpt on 4/16/18.
 */

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
     * @param activity Current activity
     * @param response Response data
     */
    public static void showErrorMessage(final Activity activity, BaseRequest.BaseResponse response) {
        try {
            int statusCode = response.getErrorCode();
            if (statusCode == ErrorCode.ERROR_LOST_CONNECTION) {
                showErrorMessage(activity, activity.getString(R.string.CONTENT00046));
            } else if (statusCode == ErrorCode.ERROR_UNKNOWN) {
                showErrorMessage(activity, activity.getString(R.string.CONTENT00047));
            } else if (statusCode == ErrorCode.ERROR_LOST_TOKEN) {
                ConfirmDialogCallback listener = new ConfirmDialogCallback() {
                    @Override
                    public void onConfirmed() {
                        try {
                            BaseModel.getInstance().setToken(activity, null);
                            Intent intent = new Intent(activity, LoginActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                showErrorMessage(activity, response.getData().getString("message"), listener);
            } else {
                showErrorMessage(activity, response.getData().getString("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * @param context Current context
     * @param title Title of
     * @param message
     * @param listener
     */
    public static void showMessage(Activity context, String title, String message, final ConfirmDialogCallback listener) {
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
                        listener.onCancel();
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
     * DungNT Dec 23, 2015
     * Check show/hide menu ở server php
     * @param context Current context
     * @param menuName Menu name
     * @return True: show, False: hide
     */
    public static boolean allowAccessMenu(Context context, String menuName){
        boolean ok = false;
        List<ConfigBean> checkMenu = BaseModel.getInstance().getCheckMenu(context);
        if (checkMenu != null) {
            for (int i = 0; i < checkMenu.size(); i++) {
                ConfigBean configBean = checkMenu.get(i);
                if (configBean != null) {
                    if (menuName.equals(configBean.getId())) {
                        if ("1".equals(configBean.getName())) {
                            ok = true;// Anh Dung fix Dec 23, 2015, check menu ở server php
                        }

                    }
                }
            }
        }
        return ok;
    }
}
