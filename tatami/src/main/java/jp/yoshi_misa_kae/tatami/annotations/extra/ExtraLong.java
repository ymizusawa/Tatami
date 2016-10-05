package jp.yoshi_misa_kae.tatami.annotations.extra;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.yoshi_misa_kae.tatami.TatamiType;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExtraLong {
    public static final int annotationType = TatamiType.TYPE_EXTRA;
    public static final int annotationProcess = TatamiType.EXTRA_LONG;
}
