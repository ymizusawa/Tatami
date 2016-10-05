package jp.yoshi_misa_kae.tatami.recyclerview;

import android.view.ViewGroup;

public interface TatamiRecyclerViewListener<T> {
    TatamiRecyclerView.TatamiViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    void itemClick(int position, T obj);
    boolean itemLongClick(int position, T obj);
}
