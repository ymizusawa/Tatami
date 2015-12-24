package jp.yoshi_misa_kae.tatami.view.injector;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraInt;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraLong;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraParcelable;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraSerializable;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraString;
import jp.yoshi_misa_kae.tatami.annotations.view.BindProcess;
import jp.yoshi_misa_kae.tatami.annotations.view.BindValueType;
import jp.yoshi_misa_kae.tatami.annotations.view.Click;
import jp.yoshi_misa_kae.tatami.annotations.view.LongClick;

public class TatamiFieldInjector extends BindProcess {

    protected static void injectView(Context context, Object obj, Field field) {
        boolean result = binder(context, obj, field, BindValueType.TYPE_ID.getType());
        if (result)
            return;

        fromAnnotation(context, obj, field);
    }

    protected static void injectValue(Context context, Object obj, Field field) {
        boolean result = fromAnnotation(context, obj, field);
        if (result) return;

        binder(context, obj, field);
    }

    private static boolean fromAnnotation(Context context, Object obj, Field field) {
        Annotation[] annotations = field.getAnnotations();

        if (annotations == null) return false;

        boolean returnValue = false;
        for (Annotation annotation : annotations) {
            boolean result = binder(context, obj, field, annotation);
            if(!returnValue && result)
                returnValue = result;
        }

        return returnValue;
    }

    protected static boolean isSuperClassType(Class<?> type, Class<?> checkClass) {
        if (type == null) return false;

        if (type == checkClass) return true;

        Class<?> superClass = type.getSuperclass();
        if (superClass == null) return false;
        return isSuperClassType(superClass, checkClass);
    }

    protected static void bindExtra(final Object obj, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            Object value = null;
            for (Annotation a : field.getAnnotations()) {
                if (a instanceof ExtraSerializable) {
                    if (obj instanceof Activity)
                        value = ((Activity) obj).getIntent().getSerializableExtra(field.getName());
                    else if (obj instanceof Fragment)
                        value = ((Fragment) obj).getArguments().getSerializable(field.getName());
                } else if (a instanceof ExtraParcelable) {
                    if (obj instanceof Activity)
                        value = ((Activity) obj).getIntent().getParcelableExtra(field.getName());
                    else if (obj instanceof Fragment)
                        value = ((Fragment) obj).getArguments().getParcelable(field.getName());
                } else if (a instanceof ExtraInt) {
                    if (obj instanceof Activity)
                        value = ((Activity) obj).getIntent().getIntExtra(field.getName(), -1);
                    else if (obj instanceof Fragment)
                        value = ((Fragment) obj).getArguments().getInt(field.getName());
                } else if (a instanceof ExtraString) {
                    if (obj instanceof Activity)
                        value = ((Activity) obj).getIntent().getStringExtra(field.getName());
                    else if (obj instanceof Fragment)
                        value = ((Fragment) obj).getArguments().getString(field.getName());
                } else if (a instanceof ExtraLong) {
                    if (obj instanceof Activity)
                        value = ((Activity) obj).getIntent().getLongExtra(field.getName(), -1);
                    else if (obj instanceof Fragment)
                        value = ((Fragment) obj).getArguments().getLong(field.getName());
                }

                if (value != null) break;
            }

            if(value != null)
                bindField(obj, field, value);
        }

    }

    protected static void bindEvent(final Object obj, Class<?> clazz) {
        Method[] fields = clazz.getDeclaredMethods();
        for (final Method method : fields) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Click.class)) {
                    Click click = (Click) a;
                    int id = click.value();
                    View view = null;

                    if (obj instanceof Activity)
                        view = ((Activity) obj).findViewById(id);
                    else if (obj instanceof View)
                        view = ((View) obj).findViewById(id);
                    else if (obj instanceof Fragment)
                        view = ((Fragment) obj).getView().findViewById(id);

                    if (view != null)
                        view.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                callMethod(obj, method, new Class[]{View.class}, new Object[]{view});
                            }
                        });
                } else if (a.annotationType().equals(LongClick.class)) {
                    LongClick click = (LongClick) a;
                    int id = click.value();

                    View view = null;
                    if (obj instanceof Activity)
                        view = ((Activity) obj).findViewById(id);
                    else if (obj instanceof View)
                        view = ((View) obj).findViewById(id);

                    if (view != null)
                        view.setOnLongClickListener(new View.OnLongClickListener() {

                            @Override
                            public boolean onLongClick(View view) {
                                callMethod(obj, method, new Class[]{View.class}, new Object[]{view});
                                return false;
                            }

                        });
                }
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) return;
        else if (superClass == Activity.class) return;
        else bindEvent(obj, superClass);
    }

    protected static Object callMethod(Object obj, Method callMethod, Class[] params, Object[] values) {
        Object returnValue = null;

        try {
            callMethod.setAccessible(true);
            returnValue = callMethod.invoke(obj, callMethod.getParameterTypes().length == 0 ? null : values);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

}
