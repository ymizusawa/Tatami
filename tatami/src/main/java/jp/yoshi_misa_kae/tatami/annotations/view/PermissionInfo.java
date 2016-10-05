package jp.yoshi_misa_kae.tatami.annotations.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.yoshi_misa_kae.tatami.TatamiType;

/**
 * Created by ymizusawa on 2016/08/19.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionInfo {
    String[] value();
    public static final int annotationType = TatamiType.TYPE_VIEW;
}
