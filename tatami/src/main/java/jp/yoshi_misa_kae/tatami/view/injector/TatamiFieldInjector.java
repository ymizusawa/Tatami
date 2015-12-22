package jp.yoshi_misa_kae.tatami.view.injector;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jp.yoshi_misa_kae.tatami.annotations.view.BindProcess;
import jp.yoshi_misa_kae.tatami.annotations.view.BindValueType;
import jp.yoshi_misa_kae.tatami.annotations.view.Click;
import jp.yoshi_misa_kae.tatami.annotations.view.LongClick;

public class TatamiFieldInjector {

    protected static BindProcess injectView(Context context, Object obj, Field field) {
        BindProcess viewBinder = new BindProcess();
        boolean result = viewBinder.binder(context, obj, field, BindValueType.TYPE_ID.getType());
        if (result)
            return viewBinder;

        return fromAnnotation(context, obj, field);
    }

    protected static BindProcess injectValue(Context context, Object obj, Field field) {
        BindProcess viewBinder = fromAnnotation(context, obj, field);
        if(viewBinder != null) return viewBinder;

        viewBinder = new BindProcess();
        boolean result = viewBinder.binder(context, obj, field);
        if (result)
            return viewBinder;
        else
            return null;
    }

    private static BindProcess fromAnnotation(Context context, Object obj, Field field) {
        Annotation[] annotations = field.getAnnotations();

        if(annotations == null) return null;

        for (Annotation annotation : annotations) {
            BindProcess viewBinder = new BindProcess();
            viewBinder.binder(context, obj, field, annotation);

            return viewBinder;
        }

        return null;
    }

    protected static boolean isSuperClassType(Class<?> type, Class<?> checkClass) {
        if(type == null) return false;

        if(type == checkClass) return true;

        Class<?> superClass = type.getSuperclass();
        if(superClass == null) return false;
        return isSuperClassType(superClass, checkClass);
    }

    protected static void bindEvent(final Object obj, Class<?> clazz, SimpleArrayMap<String, BindProcess> bindMap) {
        Method[] fields = clazz.getDeclaredMethods();
        for (final Method method : fields) {
            Annotation[] annotations = method.getAnnotations();
            for(Annotation a : annotations) {
                if (a.annotationType().equals(Click.class)) {
                    Click click = (Click) a;
                    int id = click.value();
                    View view = null;
                    
                    if(obj instanceof Activity)
                        view = ((Activity) obj).findViewById(id);
                    else if(obj instanceof View)
                        view = ((View) obj).findViewById(id);
                    else if(obj instanceof Fragment)
                        view = ((Fragment) obj).getView().findViewById(id);

                    if(view != null)
                    view.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                callMethod(obj, method, new Class[] { View.class }, new Object[] { view });
                            }
                        });
                } else if (a.annotationType().equals(LongClick.class)) {
                    LongClick click = (LongClick) a;
                    int id = click.value();
                    
                    View view = null;
                    if(obj instanceof Activity)
                        view = ((Activity) obj).findViewById(id);
                    else if(obj instanceof View)
                        view = ((View) obj).findViewById(id);

                    if(view != null)
                    view.setOnLongClickListener(new View.OnLongClickListener() {

                            @Override
                            public boolean onLongClick(View view) {
                                callMethod(obj, method, new Class[] { View.class }, new Object[] { view });
                                return false;
                            }

                        });
                }
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null) return;
        else if (superClass == Activity.class) return;
        else bindEvent(obj, superClass, bindMap);
        
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
