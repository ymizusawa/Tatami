package jp.yoshi_misa_kae.tatami.view.widget;

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

    private TatamiViewPagerAdapter adapter;
    private boolean isEnableSwipe = true;
    private static List<?> list = new ArrayList<>();
    private FragmentManager fragmentManager;
    private static TatamiViewPagerListener listener;

    public TatamiViewPager(Context context) {
        this(context, null);
    }

    public TatamiViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        createAdapter();
    }

    private void createAdapter() {
        if(fragmentManager != null) {
            if(adapter == null) {
                adapter = new TatamiViewPagerAdapter(fragmentManager);
                setAdapter(adapter);
            }
        }
    }

    public void setSupportFragmentManager(FragmentManager fm) {
        fragmentManager = fm;

        createAdapter();
    }

    public interface TatamiViewPagerListener {
        Fragment getViewPagerItem(Object obj, int position);
    }

    public void setViewPagerListener(TatamiViewPagerListener l) {
        listener = l;
    }

    public void setList(List<?> list) {
        this.list = list;

        if(adapter != null)
        adapter.notifyDataSetChanged();
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
        if (isEnableSwipe) {
            return super.onInterceptTouchEvent(event);
        }

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
            if(listener != null)
                return listener.getViewPagerItem(list.get(position), position);

            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


    }
    
}
