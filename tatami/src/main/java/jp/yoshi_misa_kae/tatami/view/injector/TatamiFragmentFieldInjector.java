package jp.yoshi_misa_kae.tatami.view.injector;

import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;

import jp.yoshi_misa_kae.tatami.annotations.view.BindProcess;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/13.
 */
public class TatamiFragmentFieldInjector extends TatamiFieldInjector {

    public static void injectExtra(Fragment fragment) {
        bindExtra(fragment, fragment.getClass());
    }

    public static void injectEvent(Fragment fragment) {
        bindEvent(fragment, fragment.getClass());
    }

    public static void injectField(Fragment fragment) {
        bind(fragment, fragment.getClass());
    }

    private static void bind(Fragment fragment, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            Class<?> type = field.getType();
            BindProcess binder = null;
            if (isSuperClassType(type, View.class))
                injectView(fragment.getContext(), fragment, field);
            else
                injectValue(fragment.getContext(), fragment, field);
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) return;
        else if (superClass == Fragment.class) return;
        else bind(fragment, superClass);
    }

    private TatamiFragmentFieldInjector() {
    }

}
