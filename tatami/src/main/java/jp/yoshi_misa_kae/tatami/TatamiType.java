package jp.yoshi_misa_kae.tatami;

import java.lang.annotation.Annotation;

import jp.yoshi_misa_kae.tatami.annotations.event.Click;
import jp.yoshi_misa_kae.tatami.annotations.event.LongClick;
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

/**
 * Created by Yoshitaka Mizusawa on 2016/03/06.
 */
public class TatamiType {

    public static final int EXTRA_INT = 1001;
    public static final int EXTRA_LONG = 1002;
    public static final int EXTRA_PARCELABLE = 1003;
    public static final int EXTRA_SERIALIZABLE = 1004;
    public static final int EXTRA_STRING = 1005;

    public static final int BIND_ANIM = 101;
    public static final int BIND_BOOL = 102;
    public static final int BIND_COLOR = 103;
    public static final int BIND_DIMEN = 104;
    public static final int BIND_DRAWABLE = 105;
    public static final int BIND_INTEGER = 106;
    public static final int BIND_STRING = 107;

    public static final int VIEW = 1;

    public static final int CLICK = 11;
    public static final int LONG_CLICK = 12;
    public static final int MENU_CLICK = 21;

    public static final int TYPE_VIEW = 10001;
    public static final int TYPE_EXTRA = 10002;
    public static final int TYPE_RES = 10003;
    public static final int TYPE_EVENT = 10004;

    public static int getAnnotation(Annotation a) {
        if(a instanceof BindView) {
            return VIEW;
        } else if(a instanceof Click) {
            return CLICK;
        } else if(a instanceof LongClick) {
            return LONG_CLICK;
        } else if(a instanceof ExtraInt) {
            return EXTRA_INT;
        } else if(a instanceof ExtraLong) {
            return EXTRA_LONG;
        } else if(a instanceof ExtraParcelable) {
            return EXTRA_PARCELABLE;
        } else if(a instanceof ExtraSerializable) {
            return EXTRA_SERIALIZABLE;
        } else if(a instanceof ExtraString) {
            return EXTRA_STRING;
        } else if(a instanceof BindAnim) {
            return BIND_ANIM;
        } else if(a instanceof BindBool) {
            return BIND_BOOL;
        } else if(a instanceof BindColor) {
            return BIND_COLOR;
        } else if(a instanceof BindDimen) {
            return BIND_DIMEN;
        } else if(a instanceof BindDrawable) {
            return BIND_DRAWABLE;
        } else if(a instanceof BindInteger) {
            return BIND_INTEGER;
        } else if(a instanceof BindString) {
            return BIND_STRING;
        }

        return 0;
    }

}
