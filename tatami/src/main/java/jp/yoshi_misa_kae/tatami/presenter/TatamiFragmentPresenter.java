package jp.yoshi_misa_kae.tatami.presenter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import jp.yoshi_misa_kae.tatami.Tatami;
import jp.yoshi_misa_kae.tatami.view.TatamiFragment;
import jp.yoshi_misa_kae.tatami.view.mvp.TatamiFragmentMvpView;

public class TatamiFragmentPresenter implements Presenter<TatamiFragmentMvpView> {

    private TatamiFragmentMvpView mvpView;

    @Override
    public void attachView(TatamiFragmentMvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TatamiFragment fragment = ((TatamiFragment) mvpView.getFragment());
        return Tatami.setContentView(fragment, inflater,container,savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        TatamiFragment fragment = ((TatamiFragment) mvpView.getFragment());

        Tatami.injectField(fragment);
        Tatami.injectEvent(fragment);
    }

}
