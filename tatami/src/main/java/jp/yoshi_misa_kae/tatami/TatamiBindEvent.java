package jp.yoshi_misa_kae.tatami;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SimpleArrayMap;
import android.view.Menu;
import android.view.View;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jp.yoshi_misa_kae.tatami.annotations.event.Click;
import jp.yoshi_misa_kae.tatami.annotations.event.LongClick;
import jp.yoshi_misa_kae.tatami.annotations.event.MenuClick;
import jp.yoshi_misa_kae.tatami.views.activity.TatamiActivity;
import jp.yoshi_misa_kae.tatami.views.fragment.TatamiFragment;
import android.view.MenuItem;

/**
 * Created by ymizusawa on 2016/09/16.
 */
class TatamiBindEvent extends TatamiBind {

    private static SimpleArrayMap<Class<?>, AnnotationData> eventList;

    static {
        if (eventList == null) {
            SimpleArrayMap<Class<?>, AnnotationData> data = new SimpleArrayMap<>();
            eventList = data;
        }
    }

    void bind(@NonNull Object obj, @NonNull Context context, View layoutView, Menu menu) {
        bindEvent(context, obj, obj.getClass(), layoutView, menu);
    }

    private AnnotationData eventAnnotation(@NonNull Annotation a) {
        if (a instanceof Click)
            return new AnnotationData(TatamiType.CLICK);
        else if (a instanceof LongClick)
            return new AnnotationData(TatamiType.LONG_CLICK);
        else if (a instanceof MenuClick)
            return new AnnotationData(TatamiType.MENU_CLICK);

        return null;
    }

    private void bindEvent(@NonNull Context context, @NonNull final Object obj, @NonNull Class<?> clazz, View layoutView, Menu menu) {
        Method[] fields = clazz.getDeclaredMethods();
        for (final Method method : fields) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            if (annotations == null || annotations.length == 0) continue;

            for (Annotation annotation : annotations) {
                AnnotationData data = null;
                if ((data = eventAnnotation(annotation)) != null) {
                    switch (data.type) {
                        case TatamiType.CLICK: {
                                Click clickAnnotation = (Click) annotation;
                                int id = clickAnnotation.value();
                                View view = findViewById(obj, id, layoutView);
                                if (view != null)
                                    view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                callMethodReflection(obj, method, new Class[]{View.class}, new Object[]{view});
                                            }
                                        });

                                break;
                            }
                        case TatamiType.LONG_CLICK: {
                                LongClick longClickAnnotation = (LongClick) annotation;
                                int id = longClickAnnotation.value();
                                View view = findViewById(obj, id, layoutView);
                                if (view != null)
                                    view.setOnLongClickListener(new View.OnLongClickListener() {
                                            @Override
                                            public boolean onLongClick(View view) {
                                                return (Boolean) callMethodReflection(obj, method, new Class[]{View.class}, new Object[]{view});
                                            }
                                        });

                                break;
                            }
                        case TatamiType.MENU_CLICK: {
                                MenuClick menuClick = (MenuClick) annotation;
                                int id = menuClick.value();
                                if (menu != null) {
                                    MenuItem item = menu.findItem(id);
                                    if (item != null)
                                        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                                                @Override
                                                public boolean onMenuItemClick(MenuItem menuItem) {
                                                    return (Boolean) callMethodReflection(obj, method, new Class[]{MenuItem.class}, new Object[]{menuItem});
                                                }

                                            });
                                }

                                break;
                            }
                    }

                }
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && (superClass != TatamiActivity.class || superClass != TatamiFragment.class)) bindEvent(context, obj, superClass, layoutView, menu);
    }

    private Object callMethodReflection(Object obj, Method callMethod, Class[] params, Object[] values) {
        Object returnValue = null;

        try {
            callMethod.setAccessible(true);
            returnValue = callMethod.invoke(obj, callMethod.getParameterTypes().length == 0 ? null : values);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

}
