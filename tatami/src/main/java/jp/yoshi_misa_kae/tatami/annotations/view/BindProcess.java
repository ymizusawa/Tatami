package jp.yoshi_misa_kae.tatami.annotations.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import static jp.yoshi_misa_kae.tatami.annotations.view.BindValueType.list;

/**
 * Created by ymizusawa on 2015/12/15.
 */
public class BindProcess {

    protected static boolean binder(Context context, Object obj, Field field) {
        List<BindValueType> typeList = list();
        for(BindValueType type : typeList) {
            boolean result = isBindView(context, obj, field, type);
            if(result) return result;
        }

        return false;
    }

    protected static boolean isBindView(Context context, Object obj, Field field, BindValueType type) {
        int id = BindUtils.getIdentifier(context, field.getName(), type.getType(), context.getPackageName());
        if(id != 0) {
            Object value = BindUtils.getResource(context, type, id);

            if (value != null) {
                return bindField(obj, field, value);
            }
        }
        return false;
    }

    protected static boolean bindField(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);

            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static boolean binder(Context context, Object obj, Field field, String defType) {
        int viewId = BindUtils.getIdentifier(context, field.getName(), BindValueType.TYPE_ID.getType(), context.getPackageName());

        View view = findViewById(obj, viewId);
        if(view == null)
            return false;

        field.setAccessible(true);
        try {
            field.set(obj, view);

            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected static boolean binder(Context context, Object obj, Field field, Annotation annotation) {
        List<BindValueType> typeList = BindValueType.list();
        for(BindValueType type : typeList) {
            int id = BindUtils.getIdentifier(context, field.getName(), type.getType(), context.getPackageName());

            if(id != 0) {
                Object createObject = BindUtils.getResource(context, type, id);

                bindField(obj, field, createObject);
                return true;
            }
        }

        return false;
    }

    protected static View findViewById(Object obj, int id) {
        if(obj instanceof Activity)
            return ((Activity) obj).findViewById(id);
        else if(obj instanceof View)
            return ((View) obj).findViewById(id);
        else if(obj instanceof Fragment)
            return ((Fragment) obj).getView().findViewById(id);

        return null;
    }

}
