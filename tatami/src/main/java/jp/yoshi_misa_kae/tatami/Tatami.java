package jp.yoshi_misa_kae.tatami;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraInt;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraLong;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraParcelable;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraSerializable;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraString;
import jp.yoshi_misa_kae.tatami.annotations.res.BindAnim;
import jp.yoshi_misa_kae.tatami.annotations.res.BindBool;
import jp.yoshi_misa_kae.tatami.annotations.res.BindColor;
import jp.yoshi_misa_kae.tatami.annotations.res.BindDimen;
import jp.yoshi_misa_kae.tatami.annotations.res.BindDrawable;
import jp.yoshi_misa_kae.tatami.annotations.res.BindInteger;
import jp.yoshi_misa_kae.tatami.annotations.res.BindString;
import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.annotations.view.BindProcess;
import jp.yoshi_misa_kae.tatami.annotations.view.BindUtils;
import jp.yoshi_misa_kae.tatami.annotations.view.BindValueType;
import jp.yoshi_misa_kae.tatami.annotations.view.Click;
import jp.yoshi_misa_kae.tatami.annotations.view.FragmentInfo;
import jp.yoshi_misa_kae.tatami.annotations.view.LongClick;
import jp.yoshi_misa_kae.tatami.view.TatamiActivity;
import jp.yoshi_misa_kae.tatami.view.TatamiFragment;

public class Tatami {

    private Context context;
    private Object obj = null;
    private View view = null;

    public Tatami(Activity activity) {
        this.obj = activity;
        context = activity.getApplicationContext();
    }

    public Tatami(TatamiActivity activity) {
        this.obj = activity;
        context = activity.getApplicationContext();

        ActivityInfo info = activity.getClass().getAnnotation(ActivityInfo.class);
        if (info != null) activity.setContentView(info.layoutId());
    }

    public Tatami(TatamiFragment fragment, LayoutInflater inflater, ViewGroup container) {
        this.obj = fragment;
        context = fragment.getActivity().getApplicationContext();

        FragmentInfo info = fragment.getClass().getAnnotation(FragmentInfo.class);
        if (info != null)
            view = inflater.inflate(info.layoutId(), container, false);
    }

    public Tatami(Fragment fragment) {
        this.obj = fragment;
        context = fragment.getActivity().getApplicationContext();
    }

    public void inject() {
        bindField();
        bindEvent();
    }

    public void bindField() {
        bindField(obj.getClass());
    }

    public void bindEvent() {
        bindEvent(obj.getClass());
    }

    private void bindEvent(Class<?> clazz) {
        Method[] fields = clazz.getDeclaredMethods();
        for (final Method method : fields) {
            List<Annotation> annotations = Arrays.asList(method.getAnnotations());

            for (Annotation a : annotations)
                if (a instanceof Click) {
                    Click click = (Click) a;
                    int id = click.value();
                    View view = findViewById(id);
                    if (view != null)
                        view.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    callMethodReflection(method, new Class[]{View.class}, new Object[]{view});
                                }
                                
                            
                            });
                } else if (a instanceof LongClick) {
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
                                public boolean onLongClick(View view1) {
                            callMethodReflection(method, new Class[]{View.class}, new Object[]{view1});
                            return false;
                            }
                        });
                }
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Activity.class) bindEvent(superClass);
    }

    private Object callMethodReflection(Method callMethod, Class[] params, Object[] values) {
        Object returnValue = null;

        try {
            callMethod.setAccessible(true);
            returnValue = callMethod.invoke(obj, callMethod.getParameterTypes().length == 0 ? null : values);
        } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return returnValue;
    }

    public View getView() {
        return view;
    }

    private void bindField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            Class<?> type = field.getType();
            BindProcess binder = null;
            if (isSuperClassType(type, View.class))
                bindView(field);
            else
                bindValue(field);
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Activity.class) bindField(superClass);
    }

    private void bindValue(Field field) {
        Annotation[] annotations = field.getAnnotations();

        if (annotations != null)
            for (Annotation annotation : annotations)
                binder(obj, field, annotation);
    }

    private void bindView(Field field) {
        int viewId = BindUtils.getIdentifier(context, field.getName(), BindValueType.TYPE_ID.getType(), context.getPackageName());

        View view = findViewById(viewId);
        if (view != null)
            bindFieldReflection(obj, field, view);
    }

    private void binder(Object obj, Field field, Annotation annotation) {
        List<BindValueType> typeList = BindValueType.list();

        Object value = null;
        if (annotation instanceof BindAnim) {
            int id = BindUtils.getIdentifier(context, field.getName(), "anim", context.getPackageName());
            value = context.getResources().getAnimation(id);
        } else if (annotation instanceof BindBool) {
            int id = BindUtils.getIdentifier(context, field.getName(), "bool", context.getPackageName());
            value = context.getResources().getBoolean(id);
        } else if (annotation instanceof BindColor) {
            int id = BindUtils.getIdentifier(context, field.getName(), "color", context.getPackageName());
            value = ContextCompat.getColor(context, id);
        } else if (annotation instanceof BindDimen) {
            int id = BindUtils.getIdentifier(context, field.getName(), "dimen", context.getPackageName());
            value = context.getResources().getDimensionPixelSize(id);
        } else if (annotation instanceof BindDrawable) {
            int id = BindUtils.getIdentifier(context, field.getName(), "drawable", context.getPackageName());
            value = ContextCompat.getDrawable(context, id);
        } else if (annotation instanceof BindInteger) {
            int id = BindUtils.getIdentifier(context, field.getName(), "integer", context.getPackageName());
            value = context.getResources().getInteger(id);
        } else if (annotation instanceof BindString) {
            int id = BindUtils.getIdentifier(context, field.getName(), "string", context.getPackageName());
            value = context.getString(id);
        } else if (annotation instanceof ExtraSerializable) {
            if (obj instanceof Activity)
                value = ((Activity) obj).getIntent().getSerializableExtra(field.getName());
            else if (obj instanceof Fragment)
                value = ((Fragment) obj).getArguments().getSerializable(field.getName());
        } else if (annotation instanceof ExtraParcelable) {
            if (obj instanceof Activity)
                value = ((Activity) obj).getIntent().getParcelableExtra(field.getName());
            else if (obj instanceof Fragment)
                value = ((Fragment) obj).getArguments().getParcelable(field.getName());
        } else if (annotation instanceof ExtraInt) {
            if (obj instanceof Activity)
                value = ((Activity) obj).getIntent().getIntExtra(field.getName(), -1);
            else if (obj instanceof Fragment)
                value = ((Fragment) obj).getArguments().getInt(field.getName());
        } else if (annotation instanceof ExtraString) {
            if (obj instanceof Activity)
                value = ((Activity) obj).getIntent().getStringExtra(field.getName());
            else if (obj instanceof Fragment)
                value = ((Fragment) obj).getArguments().getString(field.getName());
        } else if (annotation instanceof ExtraLong) {
            if (obj instanceof Activity)
                value = ((Activity) obj).getIntent().getLongExtra(field.getName(), -1);
            else if (obj instanceof Fragment)
                value = ((Fragment) obj).getArguments().getLong(field.getName());
        }

        if(value != null)
            bindFieldReflection(obj, field, value);
    }

    private void bindFieldReflection(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private View findViewById(int id) {
        if (obj instanceof Activity)
            return ((Activity) obj).findViewById(id);
        else if (obj instanceof View)
            return ((View) obj).findViewById(id);
        else if (obj instanceof Fragment)
            return view.findViewById(id);

        return null;
    }

    private boolean isSuperClassType(Class<?> type, Class<?> checkClass) {
        if (type == null) return false;

        if (type == checkClass) return true;

        Class<?> superClass = type.getSuperclass();
        return superClass != null && isSuperClassType(superClass, checkClass);
    }

    public void destroy() {

    }

//
//
//
//    public void injectActivity(Activity activity) {
//        if(activity instanceof TatamiActivity)
//            TatamiInjectActivity.setContentView((TatamiActivity) activity);
//
//        // inject
//        TatamiInjectActivity.inject(activity, activity.getClass());
//    }
//
//    public static View setContentView(TatamiFragment fragment, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        FragmentInfo info = fragment.getClass().getAnnotation(FragmentInfo.class);
//
//        if (info == null)
//            return null;
//
//        return inflater.inflate(info.layoutId(), container, false);
//    }
//
//    public static void setContentView(TatamiActivity activity) {
//        ActivityInfo info = activity.getClass().getAnnotation(ActivityInfo.class);
//
//        if (info == null)
//            return;
//
//        activity.setContentView(info.layoutId());
//    }
//
//    public static void injectField(Activity activity) {
//        TatamiActivityFieldInjector.injectField(activity);
//    }
//
//    public static void injectField(Fragment fragment) {
//        TatamiFragmentFieldInjector.injectField(fragment);
//    }
//
//    public static void injectEvent(Activity activity) {
//        TatamiActivityFieldInjector.injectEvent(activity);
//    }
//
//    public static void injectEvent(Fragment fragment) {
//        TatamiFragmentFieldInjector.injectEvent(fragment);
//    }
//
//    public static void injectExtra(Activity activity) {
//        TatamiActivityFieldInjector.injectExtra(activity);
//    }
//
//    public static void injectExtra(Fragment fragment) {
//        TatamiFragmentFieldInjector.injectExtra(fragment);
//    }
}
