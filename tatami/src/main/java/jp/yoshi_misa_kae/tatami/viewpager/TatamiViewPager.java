package jp.yoshi_misa_kae.tatami.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.yoshi_misa_kae.tatami.R;

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

    public interface TatamiViewPagerPageTitleListener {
        CharSequence getPageTitle(int position);
    }

    private TatamiViewPagerAdapter adapter;
    private boolean isEnableSwipe = true;
    private FragmentManager fragmentManager;
    private TatamiViewPagerItemListener itemListener;
    private TatamiViewPagerPageScrolledListener scrolledListener;
    private TatamiViewPagerPageSelectedListener selectedListener;
    private TatamiViewPagerPageScrollStateChangedListener scrollStateChangedListener;
    private TatamiViewPagerPageTitleListener pageTitleListener;
    private List<String> fragmentList = new ArrayList<>();
    private List<String> pageTitleList = new ArrayList<>();

    public TatamiViewPager(Context context) {
        this(context, null);
    }

    public TatamiViewPager(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFragmentManager(FragmentManager fm) {
        fragmentManager = fm;
//        createAdapter(list);
    }

    public void setTatamiViewPagerItemListener(TatamiViewPagerItemListener listener) {
        itemListener = listener;
    }

    public void setTatamiViewPagerPageScrolledListener(TatamiViewPagerPageScrolledListener listener) {
        scrolledListener = listener;
    }

    public void setTatamiViewPagerPageSelectedListener(TatamiViewPagerPageSelectedListener listener) {
        selectedListener = listener;
    }

    public void setTatamiViewPagerPageScrollStateChangedListener(TatamiViewPagerPageScrollStateChangedListener listener) {
        scrollStateChangedListener = listener;
    }

    public void setTatamiViewPagerPageTitleListener(TatamiViewPagerPageTitleListener listener) {
        pageTitleListener = listener;
    }

    public void setList(List<?> list) {
        if(adapter != null)
            adapter.setList(list);
        else
            createAdapter(list);
    }

    private void createAdapter(List<?> list) {
        if (adapter == null) {
            adapter = new TatamiViewPagerAdapter(getContext(), fragmentManager, list);
            setAdapter(adapter);
        }

        addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (scrolledListener != null)
                    scrolledListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                if (selectedListener != null) selectedListener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (scrollStateChangedListener != null)
                    scrollStateChangedListener.onPageScrollStateChanged(state);
            }

        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isEnableSwipe && super.onTouchEvent(event);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isEnableSwipe && super.onInterceptTouchEvent(event);
    }

    public void setEnableSwipe(boolean isEnableSwipe) {
        this.isEnableSwipe = isEnableSwipe;
    }

    public FragmentPagerAdapter getAdapter() {
        return adapter;
    }

    public class TatamiViewPagerAdapter extends FragmentPagerAdapter {

        private final Context context;
        private List<?> list = new ArrayList<>();

        private TatamiViewPagerAdapter(Context context, FragmentManager fm, List<?> list) {
            super(fm);
            this.context = context;
            this.list = list;
        }

        public void setList(List<?> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if(fragmentList != null && fragmentList.size() != 0) return fragmentList.size();
            if (list != null && list.size() != 0) return list.size();
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            return itemListener.getViewPagerItem(list.get(position), position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (pageTitleListener != null)
                return pageTitleListener.getPageTitle(position);
            else if (pageTitleList.size() != 0)
                return pageTitleList.get(pageTitleList.size() == 1 ? 0 : position);

            return super.getPageTitle(position);
        }


        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public Fragment findFragmentByPosition(ViewPager viewPager, int position) {
            return (Fragment) instantiateItem(viewPager, position);
        }
    }

}
