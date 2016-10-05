package jp.yoshi_misa_kae.tatami.annotations.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import android.support.annotation.MenuRes;
import android.support.annotation.IdRes;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindToolbar
{
    @MenuRes int menu() default 0;
    @IdRes int toolbar() default 0;
}
