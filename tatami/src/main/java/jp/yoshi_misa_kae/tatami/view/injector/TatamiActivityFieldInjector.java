package jp.yoshi_misa_kae.tatami.view.injector;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import jp.yoshi_misa_kae.tatami.annotations.view.BindProcess;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/10.
 */
public class TatamiActivityFieldInjector extends TatamiFieldInjector {

    public static void injectExtra(Activity activity) {
        bindExtra(activity, activity.getClass());
    }

    public static void injectEvent(Activity activity) {
        bindEvent(activity, activity.getClass());
    }

    public static void injectField(Activity activity) {
        bind(activity, activity.getClass());
    }

    private static void bind(Activity activity, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            Class<?> type = field.getType();
            BindProcess binder = null;
            if (isSuperClassType(type, View.class))
                injectView(activity.getApplicationContext(), activity, field);
            else
                injectValue(activity.getApplicationContext(), activity, field);
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) return;
        else if (superClass == Activity.class) return;
        else bind(activity, superClass);
    }

}
