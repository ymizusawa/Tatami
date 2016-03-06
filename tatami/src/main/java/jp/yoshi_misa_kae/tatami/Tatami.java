package jp.yoshi_misa_kae.tatami;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
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

    public void bind() {
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
            Annotation[] annotations = field.getAnnotations();
            if (annotations == null || annotations.length == 0) continue;

            field.setAccessible(true);

            for (Annotation annotation : annotations) {
                int type = TatamiType.getAnnotation(annotation);
                switch (type) {
                    case TatamiType.VIEW: {
                        bindView(field);
                        break;
                    }
                    default:
                    {
//                        bindValue(field, type);
                        binder(obj, field, type);
                        break;
                    }
                }
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Activity.class) bindField(superClass);
    }

    private void bindView(Field field) {
        int viewId = BindUtils.getIdentifier(context, field.getName(), BindValueType.TYPE_ID.getType(), context.getPackageName());

        View view = findViewById(viewId);
        if (view != null)
            bindFieldReflection(obj, field, view);
    }

    private void binder(Object obj, Field field, int type) {
        List<BindValueType> typeList = BindValueType.list();

        Object value = null;
        switch(type) {
            case TatamiType.BIND_ANIM:{
                int id = BindUtils.getIdentifier(context, field.getName(), "anim", context.getPackageName());
                value = context.getResources().getAnimation(id);

                break;
            }
            case TatamiType.BIND_BOOL:{
                int id = BindUtils.getIdentifier(context, field.getName(), "bool", context.getPackageName());
                value = context.getResources().getBoolean(id);

                break;
            }
            case TatamiType.BIND_COLOR:{
                int id = BindUtils.getIdentifier(context, field.getName(), "color", context.getPackageName());
                value = ContextCompat.getColor(context, id);

                break;
            }
            case TatamiType.BIND_DIMEN:{
                int id = BindUtils.getIdentifier(context, field.getName(), "dimen", context.getPackageName());
                value = context.getResources().getDimensionPixelSize(id);

                break;
            }
            case TatamiType.BIND_DRAWABLE:{
                int id = BindUtils.getIdentifier(context, field.getName(), "drawable", context.getPackageName());
                value = ContextCompat.getDrawable(context, id);

                break;
            }
            case TatamiType.BIND_INTEGER:{
                int id = BindUtils.getIdentifier(context, field.getName(), "integer", context.getPackageName());
                value = context.getResources().getInteger(id);

                break;
            }
            case TatamiType.BIND_STRING:{
                int id = BindUtils.getIdentifier(context, field.getName(), "string", context.getPackageName());
                value = context.getString(id);

                break;
            }
            case TatamiType.EXTRA_INT:{
                if (obj instanceof Activity)
                    value = ((Activity) obj).getIntent().getIntExtra(field.getName(), -1);
                else if (obj instanceof Fragment)
                    value = ((Fragment) obj).getArguments().getInt(field.getName());

                break;
            }
            case TatamiType.EXTRA_LONG:{
                if (obj instanceof Activity)
                    value = ((Activity) obj).getIntent().getLongExtra(field.getName(), -1);
                else if (obj instanceof Fragment)
                    value = ((Fragment) obj).getArguments().getLong(field.getName());

                break;
            }
            case TatamiType.EXTRA_PARCELABLE:{
                if (obj instanceof Activity)
                    value = ((Activity) obj).getIntent().getParcelableExtra(field.getName());
                else if (obj instanceof Fragment)
                    value = ((Fragment) obj).getArguments().getParcelable(field.getName());

                break;
            }
            case TatamiType.EXTRA_SERIALIZABLE:{
                if (obj instanceof Activity)
                    value = ((Activity) obj).getIntent().getSerializableExtra(field.getName());
                else if (obj instanceof Fragment)
                    value = ((Fragment) obj).getArguments().getSerializable(field.getName());

                break;
            }
            case TatamiType.EXTRA_STRING:{
                if (obj instanceof Activity)
                    value = ((Activity) obj).getIntent().getStringExtra(field.getName());
                else if (obj instanceof Fragment)
                    value = ((Fragment) obj).getArguments().getString(field.getName());

                break;
            }

        }

        if (value != null)
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

    @Nullable
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

}
