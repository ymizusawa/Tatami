package jp.yoshi_misa_kae.tatami.view.injector;

import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;

import java.lang.reflect.Field;

import jp.yoshi_misa_kae.tatami.annotations.view.BindProcess;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/13.
 */
public class TatamiFragmentFieldInjector extends TatamiFieldInjector {

    private static SimpleArrayMap<String, SimpleArrayMap<String, BindProcess>> binderMap = null;
    private static SimpleArrayMap<String, SimpleArrayMap<String, BindProcess>> binderMapEvent = null;
    
    public static void injectEvent(Fragment fragment) {
        if (binderMapEvent == null) {
            binderMapEvent = new SimpleArrayMap<>();
        }

        String key = String.format("%s.%s", fragment.getContext().getPackageName(), fragment.getClass().getSimpleName());
        if (binderMapEvent.containsKey(key)) {
            binderMapEvent.remove(key);
        }

        SimpleArrayMap<String, BindProcess> bindMap = new SimpleArrayMap<>();
        bindEvent(fragment, fragment.getClass(), bindMap);
        binderMapEvent.put(key, bindMap);
    }

    public static void injectField(Fragment fragment) {
        if (binderMap == null) {
            binderMap = new SimpleArrayMap<>();
        }

        String key = String.format("%s.%s", fragment.getContext().getPackageName(), fragment.getClass().getSimpleName());
        if (binderMap.containsKey(key)) {
            binderMap.remove(key);
        }

        SimpleArrayMap<String, BindProcess> bindMap = new SimpleArrayMap<>();
        bind(fragment, fragment.getClass(), bindMap);
        binderMap.put(key, bindMap);
    }

    private static void bind(Fragment fragment, Class<?> clazz, SimpleArrayMap<String, BindProcess> bindMap) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            Class<?> type = field.getType();
            BindProcess binder = null;
            if (isSuperClassType(type, View.class))
                binder = injectView(fragment.getContext(), fragment, field);
            else
                binder = injectValue(fragment.getContext(), fragment, field);

            if (binder != null)
                bindMap.put(field.getName(), binder);

        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) return;
        else if (superClass == Fragment.class) return;
        else bind(fragment, superClass, bindMap);
    }

    private TatamiFragmentFieldInjector() {
    }

}
