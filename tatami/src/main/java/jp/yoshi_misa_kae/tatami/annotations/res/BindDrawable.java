package jp.yoshi_misa_kae.tatami.annotations.res;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.yoshi_misa_kae.tatami.TatamiType;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BindDrawable {
    public static final int annotationType = TatamiType.TYPE_RES;
    public static final int annotationProcess = TatamiType.BIND_DRAWABLE;
}
