package jp.yoshi_misa_kae.tatami.recyclerview;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import jp.yoshi_misa_kae.tatami.annotations.view.BindView;
import jp.yoshi_misa_kae.tatami.views.fragment.TatamiFragment;

/**
 * Created by Yoshitaka Mizusawa on 2016/09/10.
 */
public abstract class TatamiRecyclerViewFragment<T> extends TatamiFragment {

    @BindView
    protected TatamiRecyclerView<T> recyclerView;

    @Override
    protected void onTatamiCreateView(View view, LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onTatamiCreateView(view, inflater, container, savedInstanceState);

        recyclerView.setHasFixedSize(true);
    }

    protected View createView(@LayoutRes int layoutId, ViewGroup parent) {
        return getTatamiActivity().getLayoutInflater().inflate(layoutId, parent, false);
    }
    
//    public void setList(List result) {
//        recyclerView.setList(result);
//    }

}
