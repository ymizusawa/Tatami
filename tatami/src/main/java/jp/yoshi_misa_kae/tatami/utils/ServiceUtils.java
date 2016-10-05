package jp.yoshi_misa_kae.tatami.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class ServiceUtils {

    public static boolean isServiceRunning(Context context, String className) {
        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        try {
            Log.v("test", "className = " + className);

            for (ActivityManager.RunningServiceInfo info : services) {
                if (className.equals(info.service.getClassName())
//                    && (info.pid == android.os.Process.myPid())
                        ) {
                        Log.v("test", "service running.");
                    return true;
                }
            }
            return false;
        } finally {
            services.clear();
        }
    }
}
