package jp.yoshi_misa_kae.tatami;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.annotations.view.FragmentInfo;
import jp.yoshi_misa_kae.tatami.view.TatamiFragment;
import jp.yoshi_misa_kae.tatami.view.injector.TatamiActivityFieldInjector;
import jp.yoshi_misa_kae.tatami.view.injector.TatamiFragmentFieldInjector;

public class Tatami 
{

    public static View setContentView(TatamiFragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentInfo info = fragment.getClass().getAnnotation(FragmentInfo.class);

        return inflater.inflate(info.layoutId(), container, false);
    }
    
    public static void setContentView(Activity activity) {
        ActivityInfo tatamiContentView = activity.getClass().getAnnotation(ActivityInfo.class);

        if (tatamiContentView == null)
            return;

        int layoutId = tatamiContentView.layoutId();
        activity.setContentView(layoutId);
    }

    public static void injectField(Activity activity) {
        TatamiActivityFieldInjector.injectField(activity);
    }
    
    public static void injectField(Fragment fragment) {
        TatamiFragmentFieldInjector.injectField(fragment);
    }

    public static void injectEvent(Activity activity) {
        TatamiActivityFieldInjector.injectEvent(activity);
    }
    
    public static void injectEvent(Fragment fragment) {
        TatamiFragmentFieldInjector.injectEvent(fragment);
    }
}
