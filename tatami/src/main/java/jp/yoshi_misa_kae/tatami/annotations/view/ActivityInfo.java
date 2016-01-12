package jp.yoshi_misa_kae.tatami.annotations.view;

import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/10.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityInfo {

    @LayoutRes int layoutId();

    @StringRes int titleId() default -1;
    String titleStr() default "";

    @StringRes int subTitleId() default -1;
    String subTitleStr() default "";

    @MenuRes int menuId() default -1;

}
