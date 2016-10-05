package jp.yoshi_misa_kae.tatami.recyclerview;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import jp.yoshi_misa_kae.tatami.annotations.view.BindView;
import jp.yoshi_misa_kae.tatami.views.activity.TatamiActivity;

public abstract class TatamiRecyclerViewActivity<T> extends TatamiActivity {

    @BindView
    protected TatamiRecyclerView<T> recyclerView;

    @Override
    protected void onTatamiCreate(@Nullable Bundle savedInstanceState) {
        super.onTatamiCreate(savedInstanceState);

        recyclerView.setHasFixedSize(true);
    }

    protected View createView(@LayoutRes int layoutId, ViewGroup parent) {
        return getLayoutInflater().inflate(layoutId, parent, false);
    }


//    public void setList(List result) {
//        recyclerView.setTatamiRecyclerViewListener(this);
//        recyclerView.setList(result);
//    }

}
