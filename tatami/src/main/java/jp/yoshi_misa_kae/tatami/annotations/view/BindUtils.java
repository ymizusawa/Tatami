package jp.yoshi_misa_kae.tatami.annotations.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/16.
 */
public class BindUtils {

    public static int getIdentifier(Context context, String name, String defType, String defPackage) {
        return context.getResources().getIdentifier(name, defType, defPackage);
    }

    public static Object getResource(Context context, BindValueType type, int id) {
        switch(type) {
            case TYPE_ANIM: {
                break;
            }
            case TYPE_ATTR: {
                break;
            }
            case TYPE_BOOL: {
                return context.getResources().getBoolean(id);
            }
            case TYPE_COLOR: {
                return ContextCompat.getColor(context, id);
            }
            case TYPE_DIMEN: {
                return context.getResources().getDimension(id);
            }
            case TYPE_DRAWABLE: {
                return ContextCompat.getDrawable(context, id);
            }
            case TYPE_ID: {
                return id;
            }
            case TYPE_INTEGER: {
                return context.getResources().getInteger(id);
            }
            case TYPE_LAYOUT: {
                return id;
            }
            case TYPE_STRING: {
                return context.getResources().getString(id);
            }
            case TYPE_STYLE: {
                return id;
            }
            case TYPE_STYLEABLE: {
                break;
            }
        }

        return null;
    }

}
