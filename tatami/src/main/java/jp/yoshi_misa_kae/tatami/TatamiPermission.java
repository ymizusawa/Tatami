package jp.yoshi_misa_kae.tatami;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import jp.yoshi_misa_kae.tatami.annotations.view.PermissionInfo;
import jp.yoshi_misa_kae.tatami.views.activity.TatamiActivity;

/**
 * Created by ymizusawa on 2016/08/19.
 */
public class TatamiPermission {

    private String[] permissions;
    private TatamiActivity activity;

    public void checkPermission(@NonNull TatamiActivity activity) {
        this.activity = activity;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Context context = activity.getApplicationContext();
            PermissionInfo info = activity.getClass().getAnnotation(PermissionInfo.class);
            if (info != null) {
                permissions = info.value();

                if (permissions != null)
                    if (PermissionUtils.hasPermission(context, permissions)) {
                        activity.allPermissionSettings();
                    } else {
                        // 権限がない場合はリクエスト
                        requestPermission();
                    }
            }
        } else {
            activity.allPermissionSettings();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        boolean flag = false;

        for (String permission : permissions) {
            boolean result = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
            if (result) {
                flag = true;
                break;
            }
        }

        if (flag) {
            activity.shouldShowRequestPermissionRationale();
            return;
        }

//        activity.requestPermissions(permissions, TatamiActivity.REQUEST_CODE_PERMISSION);

        requestPermissions();
    }

    public void requestPermissions() {
        // 権限を取得する
        ActivityCompat.requestPermissions(activity, permissions, TatamiActivity.REQUEST_CODE_PERMISSION);
    }

    public void onRequestPermissionsResult(String[] permissions, int[] grantResults) {
        boolean flag = true;
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i])) {
                    activity.shouldShowRequestPermissionRationale();
                    flag = false;
                    break;
                } else {
                    activity.nonPermissionSettings();
                    flag = false;
                    break;
                }
            }
        }

        if(flag) activity.allPermissionSettings();
    }

}
