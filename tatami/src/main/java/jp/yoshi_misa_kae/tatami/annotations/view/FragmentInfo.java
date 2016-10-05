package jp.yoshi_misa_kae.tatami.annotations.view;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.yoshi_misa_kae.tatami.TatamiType;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/13.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentInfo {
    @LayoutRes int value();
    public static final int annotationType = TatamiType.TYPE_VIEW;
}
