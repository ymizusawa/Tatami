package jp.yoshi_misa_kae.tatami.view.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

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

    public TatamiViewPager(Context context) {
        this(context, null);
    }

    public TatamiViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void createAdapter(Activity activity, FragmentManager fm, List<?> l) {
        if(activity instanceof TatamiViewPagerItemListener)
            itemListener = (TatamiViewPagerItemListener) activity;
        if(activity instanceof TatamiViewPagerPageScrolledListener)
            scrolledListener = (TatamiViewPagerPageScrolledListener) activity;
        if(activity instanceof TatamiViewPagerPageSelectedListener)
            selectedListener = (TatamiViewPagerPageSelectedListener) activity;
        if(activity instanceof TatamiViewPagerPageScrollStateChangedListener)
            scrollStateChangedListener = (TatamiViewPagerPageScrollStateChangedListener) activity;

        createAdapter(fm, l);
    }

    public void createAdapter(Fragment fragment, FragmentManager fm, List<?> l) {
        if(fragment instanceof TatamiViewPagerItemListener)
            itemListener = (TatamiViewPagerItemListener) fragment;
        if(fragment instanceof TatamiViewPagerPageScrolledListener)
            scrolledListener = (TatamiViewPagerPageScrolledListener) fragment;
        if(fragment instanceof TatamiViewPagerPageSelectedListener)
            selectedListener = (TatamiViewPagerPageSelectedListener) fragment;
        if(fragment instanceof TatamiViewPagerPageScrollStateChangedListener)
            scrollStateChangedListener = (TatamiViewPagerPageScrollStateChangedListener) fragment;

        createAdapter(fm, l);
    }

    private void createAdapter(FragmentManager fm, List<?> l) {
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
            setAdapter(adapter = new TatamiViewPagerAdapter(fragmentManager));
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

        private TatamiViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            if (itemListener != null)
                return itemListener.getViewPagerItem(list.get(position), position);

            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


    }

}
