package jp.yoshi_misa_kae.tatami.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;

import jp.yoshi_misa_kae.tatami.views.activity.TatamiActivity;

/**
 * Created by ymizusawa on 2016/08/08.
 */
public class TatamiCommonPresenter {

    private TatamiActivity activity;

    public void setTatamiActivity(TatamiActivity activity) {
        this.activity = activity;
    }

    public TatamiActivity getTatamiActivity() {
        return activity;
    }

    public Context getApplicationContext() {
        return activity.getApplicationContext();
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        activity.startActivityForResult(intent, requestCode);
    }

    public void startService(Intent intent) {
        getTatamiActivity().startService(intent);
    }

    public void stopService(Intent intent) {
        getTatamiActivity().stopService(intent);
    }

    public boolean bindService(Intent service, @NonNull ServiceConnection conn, int flags) {
        return getTatamiActivity().bindService(service, conn, flags);
    }

    public void unbindService(@NonNull ServiceConnection conn) {
        getTatamiActivity().unbindService(conn);
    }

    public void startActivity(Intent intent) {
        getTatamiActivity().startActivity(intent);
    }

    public FragmentManager getSupportFragmentManager() {
        return getTatamiActivity().getSupportFragmentManager();
    }

    public String getString(@StringRes int strId) {
        return getApplicationContext().getString(strId);
    }

    public String getString(@StringRes int strId, Object... formatArgs) {
        return getApplicationContext().getString(strId, formatArgs);
    }

    public void onTatamiCreate(Bundle savedInstanceState) {
    }

    public void onTatamiStart() {
    }

    public void onTatamiResume() {
    }

    public void onTatamiPause() {
    }

    public void onTatamiStop() {
    }

    public void onTatamiDestroy() {
        activity = null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

}
