package jp.yoshi_misa_kae.tatami.recyclerview;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import jp.yoshi_misa_kae.tatami.R;

/**
 * Created by ymizusawa on 2015/12/18.
 */
public class TatamiRecyclerView<T> extends RecyclerView {

    private TatamiRecyclerViewListener<T> listener;
    private TatamiRecyclerViewAdapter adapter = null;

    public TatamiRecyclerView(Context context) {
        this(context, null);
    }

    public TatamiRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TatamiRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode())
            init(context);
//        else
//            setHasFixedSize(true);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.tatami_recycler_view, this);
    }

    public void setTatamiRecyclerViewListener(TatamiRecyclerViewListener<T> listener, List<T> list) {
        this.listener = listener;
        adapter = new TatamiRecyclerViewAdapter<T>(listener, list);
        setAdapter(adapter);
    }
    
    @Override
    public Adapter getAdapter() {
        return adapter;
    }

//    public void setList(List<T> list) {
//        if (adapter != null)
//            adapter.setList(list);
////            adapter.notifyDataSetChanged();
//    }

    public void add(T data) {
        adapter.add(data);
    }

    public void add(int position, T data) {
        adapter.add(position, data);
    }

    public void addAll(List<T> data) {
        adapter.addAll(data);
    }

    public void remove(T data) {
        adapter.remove(data);
    }

    public void remove(int position) {
        adapter.remove(position);
        removeViewAt(position);
    }

    private static class TatamiRecyclerViewAdapter<T> extends RecyclerView.Adapter<TatamiViewHolder> {

        private List<T> list;

        private TatamiRecyclerViewListener<T> listener;

        TatamiRecyclerViewAdapter(TatamiRecyclerViewListener<T> listener, List<T> list) {
            this.listener = listener;
            this.list = list;
//            setHasStableIds(true);
        }

        @Override
        public TatamiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return listener.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(TatamiViewHolder viewHolder, int position) {
            viewHolder.setListener(listener);
            viewHolder.setData(list.get(position), position);
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public void add(T data) {
//            list.add(data);
//            notifyItemInserted(list.size() - 1);
        }

        public void add(int position, T data) {
//            list.add(position, data);
//            notifyItemInserted(position);
        }

        public void addAll(List<T> data) {
            list = data;
//            notifyDataSetChanged();
        }

        public void remove(int position) {
//            list.remove(position);
            notifyItemRemoved(position);
        }

        public void remove(T data) {
//            int position = list.indexOf(data);
//            list.remove(data);
//            notifyItemRemoved(position);
        }

    }

    public static abstract class TatamiViewHolder<T> extends RecyclerView.ViewHolder {

        private TatamiRecyclerViewListener<T> listener;

        public TatamiViewHolder(View itemView) {
            super(itemView);
        }

        public void setListener(TatamiRecyclerViewListener<T> listener) {
            this.listener = listener;
        }

        @CallSuper
        public void setData(final T data, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
//                    int position = getLayoutPosition();
                if(listener != null) listener.itemClick(position, data);
                }

            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
//                    int position = getLayoutPosition();
                    if(listener != null) return listener.itemLongClick(position, data);
                    else return false;
                }
            });

        }

    }

}
