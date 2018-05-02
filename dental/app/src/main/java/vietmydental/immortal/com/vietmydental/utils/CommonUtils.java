package vietmydental.immortal.com.vietmydental.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;

import java.util.List;

import vietmydental.immortal.com.vietmydental.LoginActivity;
import vietmydental.immortal.com.vietmydental.api.BaseRequest;
import vietmydental.immortal.com.vietmydental.model.ConfigBean;

/**
 * Created by commitflame-lap on 11/8/2015.
 */
public class CommonUtils {

    public static final String UPHOLD_TYPE_DINH_KY = "1";
    public static final String UPHOLD_TYPE_SU_CO = "2";

    public static final String UPHOLD_STATUS_COMPLETE = "3";// Dec 02, 2015 STATUS map with live site
    public static final String UPHOLD_STATUS_NEW = "1";
    public static final String UPHOLD_STATUS_HANDLE = "2";
    public static final String UPHOLD_TYPE_OTHER = "6";
    public static final String UPHOLD_CONTACT_OTHER = "4";

    public static final String ROLE_CUSTOMER = "4";// Now 30, 2015 role map with live site
    public static final String ROLE_DIEU_PHOI = "17";// Dec 02, 2015 role map with live site
    public static final String ROLE_AUDIT = "54";// Dec 15, 2015 role map with live site
    public static final String ROLE_CHIEF_MONITOR = "28";// Dec 15, 2015 - Tổng giám sát
    public static final String ROLE_DIRECTOR = "19";// Dec 20, 2015 - Giám Đốc

	//++add XXXXXX-SPJ (NguyenPT 20160418) Add Training mode
    public static final int MODE_RUNNING = 0;
    public static final int MODE_TRAINING = 1;
    public static final String URL_RUNNING = "http://nkvietmy.com/index.php/api/";
    public static final String URL_TRAINING = "http://vietmy.immortal.vn/index.php/api/";
    public static final String NEED_CHANGE_PASS = "1";
    public static final String NO_NEED_CHANGE_PASS = "0";
	//--add XXXXXX-SPJ (NguyenPT 20160418) Add Training mode

    public static class ConfirmDialogCallback {
        public void onConfirmed() {
        }

        public void onCancel() {
        }
    }

    public static void showErrorMessage(final Activity activity, BaseRequest.BaseResponse response) {
        try {
            int statusCode = response.getErrorCode();
            if (statusCode == ErrorCode.ERROR_LOST_CONNECTION) {
//                showErrorMessage(activity, response.getResponseBody());
                showErrorMessage(activity, "Mất kết nối mạng, vui lòng thử lại!");
            } else if (statusCode == ErrorCode.ERROR_UNKNOWN) {
                showErrorMessage(activity, "Có lỗi xảy ra, vui lòng thử lại!");
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

    public static void showErrorMessage(Activity context, String message, final ConfirmDialogCallback listener) {
        new AlertDialog.Builder(context)
                .setTitle("Có lỗi xảy ra")
                .setMessage(Html.fromHtml(message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onConfirmed();
                        }
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void showErrorMessage(Activity context, String message) {
        showErrorMessage(context, message, null);
    }


    /** Anh Dung Dec 23, 2015
     * check show/hide menu ở server php
     * true: show, false: hide
     */
    public static boolean allowAccessMenu(Context context, String menuName){
        boolean ok = false;
        List<ConfigBean> checkMenu = BaseModel.getInstance().getCheckMenu(context);
        if (checkMenu != null) {
            for (int i = 0; i < checkMenu.size(); i++) {
                ConfigBean configItem = checkMenu.get(i);
                if (configItem != null) {
                    if (menuName.equals(configItem.getId())) {
                        if ("1".equals(configItem.getName())) {
                            ok = true;// Anh Dung fix Dec 23, 2015, check menu ở server php
                        }

                    }
                }
            }
        }
        return ok;
    }

}
