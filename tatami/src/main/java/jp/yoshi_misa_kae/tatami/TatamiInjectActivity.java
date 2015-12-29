package jp.yoshi_misa_kae.tatami;

import android.app.Activity;

import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.view.TatamiActivity;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/29.
 */
class TatamiInjectActivity {

    static void setContentView(TatamiActivity activity) {
        ActivityInfo info = activity.getClass().getAnnotation(ActivityInfo.class);

        if (info == null)
            return;

        activity.setContentView(info.layoutId());
    }

    static void inject(Activity activity) {

    }

}
