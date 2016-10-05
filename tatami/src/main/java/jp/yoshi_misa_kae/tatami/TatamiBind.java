package jp.yoshi_misa_kae.tatami;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by ymizusawa on 2016/09/16.
 */
class TatamiBind {

    @Nullable
    protected View findViewById(@NonNull Object obj, int id, View view) {
        if (obj instanceof Activity)
            return ((Activity) obj).findViewById(id);
        else if (obj instanceof View)
            return ((View) obj).findViewById(id);
        else if (obj instanceof Fragment)
            return view.findViewById(id);

        return null;
    }

    protected void bindFieldReflection(@NonNull Object obj, @NonNull Field field, @NonNull Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}
