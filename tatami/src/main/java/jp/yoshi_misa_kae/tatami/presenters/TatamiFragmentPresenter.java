package jp.yoshi_misa_kae.tatami.presenters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import jp.yoshi_misa_kae.tatami.views.fragment.TatamiFragment;

/**
 * Created by ymizusawa on 2016/08/08.
 */
public class TatamiFragmentPresenter extends TatamiCommonPresenter {

    private TatamiFragment fragment;

    public void setTatamiFragment(TatamiFragment fragment) {
        this.fragment = fragment;
    }

    public TatamiFragment getTatamiFragment() {
        return fragment;
    }

    public void onTatamiCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    }

    public void onTatamiActivityCreated(Bundle savedInstanceState) {
    }

    public void onTatamiDestroyView() {
    }

    @Override
    public void onTatamiDestroy() {
        super.onTatamiDestroy();

        fragment = null;
    }

}
