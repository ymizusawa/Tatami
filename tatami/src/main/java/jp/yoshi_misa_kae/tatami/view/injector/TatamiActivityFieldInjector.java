package jp.yoshi_misa_kae.tatami.view.injector;

import android.app.Activity;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import jp.yoshi_misa_kae.tatami.annotations.view.BindProcess;
import jp.yoshi_misa_kae.tatami.annotations.view.Click;
import jp.yoshi_misa_kae.tatami.annotations.view.LongClick;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/10.
 */
public class TatamiActivityFieldInjector extends TatamiFieldInjector {

    private static SimpleArrayMap<String, SimpleArrayMap<String, BindProcess>> binderMap = null;
    private static SimpleArrayMap<String, SimpleArrayMap<String, BindProcess>> binderMapEvent = null;
    
    public static void injectEvent(Activity activity) {
        if (binderMapEvent == null) {
            binderMapEvent = new SimpleArrayMap<>();
        }

        String key = String.format("%s.%s", activity.getPackageName(), activity.getLocalClassName());
        if (binderMapEvent.containsKey(key)) {
            binderMapEvent.remove(key);
        }

        SimpleArrayMap<String, BindProcess> bindMap = new SimpleArrayMap<>();
        bindEvent(activity, activity.getClass(), bindMap);
        binderMapEvent.put(key, bindMap);
    }

    public static void injectField(Activity activity) {
        if (binderMap == null) {
            binderMap = new SimpleArrayMap<>();
        }

        String key = String.format("%s.%s", activity.getPackageName(), activity.getLocalClassName());
        if (binderMap.containsKey(key)) {
            binderMap.remove(key);
        }

        SimpleArrayMap<String, BindProcess> bindMap = new SimpleArrayMap<>();
        bind(activity, activity.getClass(), bindMap);
        binderMap.put(key, bindMap);
    }

    private static void bind(Activity activity, Class<?> clazz, SimpleArrayMap<String, BindProcess> bindMap) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            Class<?> type = field.getType();
            BindProcess binder = null;
            if (isSuperClassType(type, View.class))
                binder = injectView(activity.getApplicationContext(), activity, field);
            else
                binder = injectValue(activity.getApplicationContext(), activity, field);

            if (binder != null)
                bindMap.put(field.getName(), binder);
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) return;
        else if (superClass == Activity.class) return;
        else bind(activity, superClass, bindMap);
    }

}
