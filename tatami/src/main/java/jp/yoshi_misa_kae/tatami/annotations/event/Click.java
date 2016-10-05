package jp.yoshi_misa_kae.tatami.annotations.event;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.yoshi_misa_kae.tatami.TatamiType;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Click {
    @IdRes int value();
    public static final int annotationType = TatamiType.TYPE_EVENT;
    public static final int annotationProcess = TatamiType.CLICK;
}
