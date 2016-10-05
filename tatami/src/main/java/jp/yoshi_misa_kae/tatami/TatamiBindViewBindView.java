package jp.yoshi_misa_kae.tatami;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.annotations.view.BindToolbar;
import jp.yoshi_misa_kae.tatami.annotations.view.FragmentInfo;
import jp.yoshi_misa_kae.tatami.views.activity.TatamiActivity;
import jp.yoshi_misa_kae.tatami.views.fragment.TatamiFragment;

/**
 * Created by Yoshitaka Mizusawa on 2016/03/12.
 */
public class TatamiBindViewBindView {

    private Object obj = null;
    private Context context = null;
    private View view = null;

    private TatamiBindEvent bindEvent = new TatamiBindEvent();
    private TatamiBindField bindField = new TatamiBindField();

    public void bindView(@NonNull TatamiActivity activity) {
        obj = activity;
        context = activity.getApplicationContext();

        ActivityInfo info = activity.getClass().getAnnotation(ActivityInfo.class);

        if (info == null) {
            Class<?> superClass = activity.getClass();
            while (info == null) {
                superClass = superClass.getSuperclass();
                if (superClass != null && superClass == Activity.class) break;
                info = superClass.getAnnotation(ActivityInfo.class);
            }
        }

        if (info != null) {
            activity.setContentView(info.value());

            BindToolbar t = activity.getClass().getAnnotation(BindToolbar.class);
            Menu menu = null;
            if (t != null) {
                int toolbarId = t.toolbar();
                if (toolbarId != 0) {
                    Toolbar toolbar = (Toolbar) activity.findViewById(toolbarId);
//                    activity.setSupportActionBar(toolbar);
                    int menuId = t.menu();
                    if (menuId != 0) {
                        toolbar.inflateMenu(menuId);
                        menu = toolbar.getMenu();
                    }
                }
            }

            bindEvent.bind(obj, context, null, menu);
            bindField.bind(obj, context, null);
        }
    }

    public void bindView(@NonNull TatamiActivity activity, @LayoutRes int layoutId) {
        obj = activity;
        context = activity.getApplicationContext();

        activity.setContentView(layoutId);
        
        BindToolbar t = activity.getClass().getAnnotation(BindToolbar.class);
        Menu menu = null;
        if (t != null) {
            int toolbarId = t.toolbar();
            if (toolbarId != 0) {
                Toolbar toolbar = (Toolbar) activity.findViewById(toolbarId);
//                    activity.setSupportActionBar(toolbar);
                int menuId = t.menu();
                if (menuId != 0) {
                    toolbar.inflateMenu(menuId);
                    menu = toolbar.getMenu();
                }
            }
        }

        bindEvent.bind(obj, context, null, menu);
        bindField.bind(obj, context, null);
    }

    public View bindView(TatamiFragment fragment, LayoutInflater inflater, ViewGroup container) {
        obj = fragment;
        TatamiActivity activity = fragment.getTatamiActivity();
        context = activity.getApplicationContext();

        FragmentInfo info = fragment.getClass().getAnnotation(FragmentInfo.class);
        if (info != null) {
            view = inflater.inflate(info.value(), container, false);
            
            BindToolbar t = fragment.getClass().getAnnotation(BindToolbar.class);
            Menu menu = null;
            if (t != null) {
                int toolbarId = t.toolbar();
                if (toolbarId != 0) {
                    Toolbar toolbar = (Toolbar) view.findViewById(toolbarId);
//                    activity.setSupportActionBar(toolbar);
                    int menuId = t.menu();
                    if (menuId != 0) {
                        toolbar.inflateMenu(menuId);
                        menu = toolbar.getMenu();
                    }
                }
            }
            bindEvent.bind(obj, context, view, menu);
            bindField.bind(obj, context, view);

            return view;
        }

        return null;
    }

}
