package jp.yoshi_misa_kae.tatami;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * アプリで使用するパーミッションを管理する
 * <p/>
 * Created by Yoshitaka Mizusawa on 2016/03/09.
 */
public class PermissionUtils {

    public static boolean hasPermission(Context context, String... permissions) {
        return requestPermissions(context, permissions).size() == 0;
    }

    public static List<String> requestPermissions(Context context, String... permissions) {
        List<String> returnValue = new ArrayList<>();

        for(String permission : permissions) {
            int result = ActivityCompat.checkSelfPermission(context, permission);
            if (result != PackageManager.PERMISSION_GRANTED)
                returnValue.add(permission);
        }

        return returnValue;
    }

}
