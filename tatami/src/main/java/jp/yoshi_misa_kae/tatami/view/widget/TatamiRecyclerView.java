package jp.yoshi_misa_kae.tatami.view.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import jp.yoshi_misa_kae.tatami.R;

/**
 * Created by ymizusawa on 2015/12/18.
 */
public class TatamiRecyclerView extends RecyclerView {

    private final LinearLayoutManager mLayoutManager;

    private static int layoutId;
    private static String vhClass;

    private TatamiRecyclerViewAdapter adapter = null;
    private static TatamiRecyclerViewCreateViewListener createViewListener = null;
    private static TatamiRecyclerViewBindViewListener bindViewListener = null;
    private static TatamiRecyclerViewItemClickListener clickListener = null;
    private static TatamiRecyclerViewItemLongClickListener longClickListener = null;
    private static List<?> list = new ArrayList<>();

    public TatamiRecyclerView(Context context) {
        this(context, null);
    }

    public TatamiRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.rowLayout);
    }

    public TatamiRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TatamiRecyclerView);
        layoutId = a.getResourceId(R.styleable.TatamiRecyclerView_rowLayout, 0);
        vhClass = a.getString(R.styleable.TatamiRecyclerView_viewHolderClassName);

        setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        setLayoutManager(mLayoutManager);

        addItemDecoration(new DividerItemDecoration(context, R.drawable.divider));

        adapter = new TatamiRecyclerViewAdapter(context);
        setAdapter(adapter);
    }

    private static class DividerItemDecoration extends RecyclerView.ItemDecoration {

        private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

        private Drawable mDivider;

        public DividerItemDecoration(Context context) {
            final TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
            mDivider = styledAttributes.getDrawable(0);
            styledAttributes.recycle();
        }

        public DividerItemDecoration(Context context, int resId) {
            mDivider = ContextCompat.getDrawable(context, resId);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public interface TatamiRecyclerViewCreateViewListener {
        TatamiViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    }

    public interface TatamiRecyclerViewBindViewListener {
        void onBindViewHolder(TatamiViewHolder viewHolder, int position, Object value);
    }

    public interface TatamiRecyclerViewItemClickListener {
        void onItemClick(int position, Object value);
    }

    public interface TatamiRecyclerViewItemLongClickListener {
        void onItemLongClick(int position, Object value);
    }

    public void setTatamiRecyclerViewListener(Activity activity) {
        if (activity instanceof TatamiRecyclerViewCreateViewListener)
            createViewListener = (TatamiRecyclerViewCreateViewListener) activity;
        if (activity instanceof TatamiRecyclerViewBindViewListener)
            bindViewListener = (TatamiRecyclerViewBindViewListener) activity;
        if (activity instanceof TatamiRecyclerViewItemClickListener)
            clickListener = (TatamiRecyclerViewItemClickListener) activity;
        if (activity instanceof TatamiRecyclerViewItemLongClickListener)
            longClickListener = (TatamiRecyclerViewItemLongClickListener) activity;
    }

    public void setTatamiRecyclerViewListener(Fragment fragment) {
        TatamiRecyclerViewCreateViewListener cvl = (TatamiRecyclerViewCreateViewListener) fragment;
        if (cvl != null) createViewListener = cvl;

        TatamiRecyclerViewBindViewListener bvl = (TatamiRecyclerViewBindViewListener) fragment;
        if (bvl != null) bindViewListener = bvl;

        TatamiRecyclerViewItemClickListener cl = (TatamiRecyclerViewItemClickListener) fragment;
        if (cl != null) clickListener = cl;

        TatamiRecyclerViewItemLongClickListener lcl = (TatamiRecyclerViewItemLongClickListener) fragment;
        if (lcl != null) longClickListener = lcl;
    }

    public void setTatamiRecyclerViewCreateViewListener(TatamiRecyclerViewCreateViewListener l) {
        createViewListener = l;
    }

    public void setTatamiRecyclerViewBindViewListener(TatamiRecyclerViewBindViewListener l) {
        bindViewListener = l;
    }

    public void setTatamiRecyclerViewItemClickListener(TatamiRecyclerViewItemClickListener l) {
        clickListener = l;
    }

    public void setTatamiRecyclerViewItemLongClickListener(TatamiRecyclerViewItemLongClickListener l) {
        longClickListener = l;
    }

    public void setList(List<?> list) {
        this.list = list;

        adapter.notifyDataSetChanged();
    }

    private static class TatamiRecyclerViewAdapter extends RecyclerView.Adapter<TatamiViewHolder> {

        private final Context context;

        public TatamiRecyclerViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public TatamiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (createViewListener != null)
                return createViewListener.onCreateViewHolder(parent, viewType);
            else {
                if (!TextUtils.isEmpty(vhClass)) {
                    try {
                        View layout = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

                        Class<? extends TatamiViewHolder> clazz = (Class<? extends TatamiViewHolder>) Class.forName(vhClass);

                        Class<?>[] types = {View.class};
                        Constructor<? extends TatamiViewHolder> constructor = clazz.getConstructor(types);
                        return constructor.newInstance(layout);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        public void onBindViewHolder(TatamiViewHolder viewHolder, int position) {
            if (bindViewListener != null)
                bindViewListener.onBindViewHolder(viewHolder, position, list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    public static class TatamiViewHolder extends RecyclerView.ViewHolder {

        public TatamiViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int position = getLayoutPosition();
                    if (clickListener != null)
                        clickListener.onItemClick(position, list.get(position));
                }

            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    int position = getLayoutPosition();
                    if (longClickListener != null)
                        longClickListener.onItemLongClick(position, list.get(position));

                    return false;
                }

            });
        }

    }

}
