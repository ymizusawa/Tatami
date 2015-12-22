package jp.yoshi_misa_kae.tatami.annotations.view;

import android.support.annotation.LayoutRes;
import android.support.annotation.MenuRes;
import android.support.annotation.StringRes;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/13.
 */
public @interface FragmentInfo {

    @LayoutRes int layoutId();

    @StringRes int titleId() default -1;
    String titleStr() default "";

    @StringRes int subTitleId() default -1;
    String subTitleStr() default "";

    @MenuRes int menuId() default -1;

}
