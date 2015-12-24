package jp.yoshi_misa_kae.tatami.view.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jp.yoshi_misa_kae.tatami.R;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraInt;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraLong;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraParcelable;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraSerializable;
import jp.yoshi_misa_kae.tatami.annotations.extra.ExtraString;
import jp.yoshi_misa_kae.tatami.view.TatamiFragment;

/**
 * Created by ymizusawa on 2015/08/07.
 */
public class TatamiViewPager extends ViewPager {

    public interface TatamiViewPagerItemListener {
        Fragment getViewPagerItem(Object obj, int position);
    }

    public interface TatamiViewPagerPageScrolledListener {
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
    }

    public interface TatamiViewPagerPageSelectedListener {
        void onPageSelected(int position);
    }

    public interface TatamiViewPagerPageScrollStateChangedListener {
        void onPageScrollStateChanged(int state);
    }

    private TatamiViewPagerAdapter adapter;
    private boolean isEnableSwipe = true;
    private static List<?> list = new ArrayList<>();
    private FragmentManager fragmentManager;
    private static TatamiViewPagerItemListener itemListener;
    private static TatamiViewPagerPageScrolledListener scrolledListener;
    private static TatamiViewPagerPageSelectedListener selectedListener;
    private static TatamiViewPagerPageScrollStateChangedListener scrollStateChangedListener;
    private static String fragmentClass;

    public TatamiViewPager(Context context) {
        this(context, null);
    }

    public TatamiViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TatamiViewPager);
        fragmentClass = a.getString(R.styleable.TatamiViewPager_fragmentClassName);
    }

    public void init(Activity activity, FragmentManager fm, List<?> l) {
        createAdapter(activity, fm, l);
    }

    public void init(Fragment fragment, FragmentManager fm, List<?> l) {
        createAdapter(fragment, fm, l);
    }

    private void createAdapter(Object obj, FragmentManager fm, List<?> l) {
        if(obj instanceof TatamiViewPagerItemListener)
            itemListener = (TatamiViewPagerItemListener) obj;
        if(obj instanceof TatamiViewPagerPageScrolledListener)
            scrolledListener = (TatamiViewPagerPageScrolledListener) obj;
        if(obj instanceof TatamiViewPagerPageSelectedListener)
            selectedListener = (TatamiViewPagerPageSelectedListener) obj;
        if(obj instanceof TatamiViewPagerPageScrollStateChangedListener)
            scrollStateChangedListener = (TatamiViewPagerPageScrollStateChangedListener) obj;
        
        fragmentManager = fm;
        list = l;

        createAdapter();
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(scrolledListener != null) scrolledListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if(selectedListener != null) selectedListener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(scrollStateChangedListener != null) scrollStateChangedListener.onPageScrollStateChanged(state);
            }

        });
    }

    private void createAdapter() {
        if (adapter == null)
            setAdapter(adapter = new TatamiViewPagerAdapter(getContext(), fragmentManager));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnableSwipe) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isEnableSwipe)
            return super.onInterceptTouchEvent(event);

        return false;
    }

    public void setEnableSwipe(boolean isEnableSwipe) {
        this.isEnableSwipe = isEnableSwipe;
    }

    private static class TatamiViewPagerAdapter extends FragmentPagerAdapter {

        private final Context context;

        private TatamiViewPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            if (itemListener != null)
                return itemListener.getViewPagerItem(list.get(position), position);
            else if(!TextUtils.isEmpty(fragmentClass))
                try {
                    Object obj = list.get(position);

                    Class<? extends TatamiFragment> clazz = (Class<? extends TatamiFragment>) Class.forName(fragmentClass);
                    Field[] fields = clazz.getDeclaredFields();

                    Bundle bundle = new Bundle();
                    for(Field field : fields) {
                        for(Annotation a : field.getAnnotations()) {
                            if(a instanceof ExtraSerializable){
                                bundle.putSerializable(field.getName(), (Serializable) obj);
                            } else if(a instanceof ExtraParcelable){
                                bundle.putParcelable(field.getName(), (Parcelable) obj);
                            } else if(a instanceof ExtraInt){
                                bundle.putInt(field.getName(), (Integer) obj);
                            } else if(a instanceof ExtraString){
                                bundle.putString(field.getName(), (String) obj);
                            } else if(a instanceof ExtraLong){
                                bundle.putLong(field.getName(), (Long) obj);
                            }
                        }
                    }

                    TatamiFragment instance = clazz.newInstance();
                    instance.setArguments(bundle);

                    return instance;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


    }

}
