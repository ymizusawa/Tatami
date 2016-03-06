package jp.yoshi_misa_kae.tatami.annotations.view;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.yoshi_misa_kae.tatami.TatamiType;

/**
 * Created by Yoshitaka Mizusawa on 2016/03/06.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface View {
    int TYPE = TatamiType.VIEW;
}
