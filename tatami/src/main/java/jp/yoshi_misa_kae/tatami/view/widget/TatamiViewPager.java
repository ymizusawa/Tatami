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
import java.util.Arrays;
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

    public interface TatamiViewPagerPageTitleListener {
        CharSequence getPageTitle(int position);
    }

    private TatamiViewPagerAdapter adapter;
    private boolean isEnableSwipe = true;
    private List<?> list = new ArrayList<>();
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

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TatamiViewPager);
        String fragmentClass = a.getString(R.styleable.TatamiViewPager_fragmentClassName);
        String fragmentClasses = a.getString(R.styleable.TatamiViewPager_fragmentClassNames);
        String pageTitles = a.getString(R.styleable.TatamiViewPager_pageTitles);

        fragmentList = new ArrayList<>();
        if (!TextUtils.isEmpty(fragmentClass)) {
            fragmentList.add(fragmentClass);
        } else if (!TextUtils.isEmpty(fragmentClasses)) {
            String[] fragmentClassSplit = fragmentClasses.split(",");
            fragmentList = Arrays.asList(fragmentClassSplit);
        }

        pageTitleList = new ArrayList<>();
        if (!TextUtils.isEmpty(pageTitles)) {
            String[] pageTitlesSplit = pageTitles.split(",");
            pageTitleList = Arrays.asList(pageTitlesSplit);
        }
    }

    public void init(Activity activity, FragmentManager fm, List<?> l) {
        createAdapter(activity, fm, l);
    }

    public void init(Fragment fragment, FragmentManager fm, List<?> l) {
        createAdapter(fragment, fm, l);
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

    private void createAdapter(Object obj, FragmentManager fm, List<?> l) {
        if (obj instanceof TatamiViewPagerItemListener)
            itemListener = (TatamiViewPagerItemListener) obj;
        if (obj instanceof TatamiViewPagerPageScrolledListener)
            scrolledListener = (TatamiViewPagerPageScrolledListener) obj;
        if (obj instanceof TatamiViewPagerPageSelectedListener)
            selectedListener = (TatamiViewPagerPageSelectedListener) obj;
        if (obj instanceof TatamiViewPagerPageScrollStateChangedListener)
            scrollStateChangedListener = (TatamiViewPagerPageScrollStateChangedListener) obj;
        if (obj instanceof TatamiViewPagerPageTitleListener)
            pageTitleListener = (TatamiViewPagerPageTitleListener) obj;

        fragmentManager = fm;
        list = l;

        createAdapter();
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

    private void createAdapter() {
        if (adapter == null) {
            adapter = new TatamiViewPagerAdapter(getContext(), fragmentManager);
            setAdapter(adapter);
        }
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

    public FragmentPagerAdapter getAdapter() {
        return adapter;
    }

    public class TatamiViewPagerAdapter extends FragmentPagerAdapter {

        private final Context context;

        private TatamiViewPagerAdapter(Context context, FragmentManager fm) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            int value = 0;
            if (list == null || list.size() == 0)
                value = fragmentList.size();
            else
                value = list.size();

            return value;
        }

        @Override
        public Fragment getItem(int position) {
            if (itemListener != null)
                return itemListener.getViewPagerItem(list.get(position), position);
            else if (fragmentList.size() != 0) {
                try {
                    Bundle bundle = new Bundle();
                    String className = fragmentList.get(fragmentList.size() == 1 ? 0 : position);

                    Class<? extends TatamiFragment> clazz = (Class<? extends TatamiFragment>) Class.forName(className);

                    if (list != null) {
                        Object obj = list.get(position);
                        Field[] fields = clazz.getDeclaredFields();

                        for (Field field : fields) {
                            for (Annotation a : field.getAnnotations()) {
                                if (a instanceof ExtraSerializable) {
                                    bundle.putSerializable(field.getName(), (Serializable) obj);
                                } else if (a instanceof ExtraParcelable) {
                                    bundle.putParcelable(field.getName(), (Parcelable) obj);
                                } else if (a instanceof ExtraInt) {
                                    bundle.putInt(field.getName(), (Integer) obj);
                                } else if (a instanceof ExtraString) {
                                    bundle.putString(field.getName(), (String) obj);
                                } else if (a instanceof ExtraLong) {
                                    bundle.putLong(field.getName(), (Long) obj);
                                }
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
            }

            return null;
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
