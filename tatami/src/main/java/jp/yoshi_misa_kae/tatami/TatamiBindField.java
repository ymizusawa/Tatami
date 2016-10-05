package jp.yoshi_misa_kae.tatami;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

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
import jp.yoshi_misa_kae.tatami.annotations.view.BindView;
import jp.yoshi_misa_kae.tatami.views.activity.TatamiActivity;
import jp.yoshi_misa_kae.tatami.views.fragment.TatamiFragment;

/**
 * Created by ymizusawa on 2016/09/16.
 */
class TatamiBindField extends TatamiBind {

    void bind(@NonNull Object obj, @NonNull Context context, View layoutView) {
        bindField(context, obj, obj.getClass(), layoutView);
    }

    private AnnotationData viewAnnotation(@NonNull Annotation a) {
        if(a instanceof BindView)
            return new AnnotationData(TatamiType.VIEW);
        return null;
    }

    private AnnotationData extraAnnotation(@NonNull Annotation a) {
        if(a instanceof BindAnim)
            return new AnnotationData(TatamiType.BIND_ANIM, "anim");
        else if(a instanceof BindBool)
            return new AnnotationData(TatamiType.BIND_BOOL, "bool");
        else if(a instanceof BindColor)
            return new AnnotationData(TatamiType.BIND_COLOR, "color");
        else if(a instanceof BindDimen)
            return new AnnotationData(TatamiType.BIND_DIMEN, "dimen");
        else if(a instanceof BindDrawable)
            return new AnnotationData(TatamiType.BIND_DRAWABLE, "drawable");
        else if(a instanceof BindInteger)
            return new AnnotationData(TatamiType.BIND_INTEGER, "integer");
        else if(a instanceof BindString)
            return new AnnotationData(TatamiType.BIND_STRING, "string");
        return null;
    }

    private AnnotationData bindAnnotation(@NonNull Annotation a) {
        if(a instanceof ExtraInt)
            return new AnnotationData(TatamiType.EXTRA_INT);
        else if(a instanceof ExtraLong)
            return new AnnotationData(TatamiType.EXTRA_LONG);
        else if(a instanceof ExtraParcelable)
            return new AnnotationData(TatamiType.EXTRA_PARCELABLE);
        else if(a instanceof ExtraSerializable)
            return new AnnotationData(TatamiType.EXTRA_SERIALIZABLE);
        else if(a instanceof ExtraString)
            return new AnnotationData(TatamiType.EXTRA_STRING);
        return null;
    }

    private void bindField(@NonNull Context context, @NonNull Object obj, @NonNull Class<?> clazz, View layoutView) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            if (annotations == null || annotations.length == 0) continue;

            field.setAccessible(true);

            for (Annotation annotation : annotations) {
                AnnotationData data = null;
                if((data = viewAnnotation(annotation)) != null)
                    bindView(context, obj, field, layoutView, data);
                if((data = extraAnnotation(annotation)) != null)
                    bindExtra(context, obj, field, data);
                if((data = bindAnnotation(annotation)) != null)
                    bindExtra(context, obj, field, data);
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && (superClass != TatamiActivity.class || superClass != TatamiFragment.class)) bindField(context, obj, superClass, layoutView);
    }

    private void bindView(@NonNull Context context, @NonNull Object obj, @NonNull Field field, View layoutView, AnnotationData data) {
        int viewId = BindUtils.getIdentifier(context, field.getName(), BindValueType.TYPE_ID.getType(), context.getPackageName());

        View view = findViewById(obj, viewId, layoutView);
        if (view != null)
            bindFieldReflection(obj, field, view);
    }

    private void bindExtra(@NonNull Context context, @NonNull Object obj, @NonNull Field field, @NonNull AnnotationData data) {
        int type = data.type;
        int id = BindUtils.getIdentifier(context, field.getName(), data.key, context.getPackageName());

        Object value = null;
        switch (type) {
            case TatamiType.BIND_ANIM:
                value = context.getResources().getAnimation(id);
                break;
            case TatamiType.BIND_BOOL:
                value = context.getResources().getBoolean(id);
                break;
            case TatamiType.BIND_COLOR:
                value = ContextCompat.getColor(context, id);
                break;
            case TatamiType.BIND_DIMEN:
                value = context.getResources().getDimensionPixelSize(id);
                break;
            case TatamiType.BIND_DRAWABLE:
                value = ContextCompat.getDrawable(context, id);
                break;
            case TatamiType.BIND_INTEGER:
                value = context.getResources().getInteger(id);
                break;
            case TatamiType.BIND_STRING:
                value = context.getString(id);
                break;
        }

        if (value != null)
            bindFieldReflection(obj, field, value);
    }

    private void bindValue(@NonNull Context context, @NonNull Object obj, @NonNull Field field, @NonNull AnnotationData data) {
        int type = data.type;

        Object value = null;
        switch (type) {
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

}
