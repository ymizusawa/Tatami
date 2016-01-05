package jp.yoshi_misa_kae.tatami.presenter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import jp.yoshi_misa_kae.tatami.Tatami;
import jp.yoshi_misa_kae.tatami.subscribe.TatamiSubscribe;
import jp.yoshi_misa_kae.tatami.view.TatamiFragment;
import jp.yoshi_misa_kae.tatami.view.mvp.TatamiFragmentMvpView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TatamiFragmentPresenter implements Presenter<TatamiFragmentMvpView> {

    private TatamiFragmentMvpView mvpView;
    private Subscription subscription = null;
    private Tatami tatami = null;

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
        tatami = new Tatami(fragment, inflater, container);
        return tatami.getView();

//        return Tatami.setContentView(fragment, inflater,container,savedInstanceState);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
//        tatami.bindField();
//        tatami.bindEvent();
//
//        mvpView.callOnActivityCreated();
        
        subscription = TatamiSubscribe.onCreate(tatami)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Void>() {

                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(Void t) {
                    mvpView.callOnActivityCreated();
                }
            });
        TatamiSubscribe.onCreateEvent(tatami)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Void>() {

                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                }

                @Override
                public void onNext(Void t) {
                }
            });
    }

    public View getView() {
        return tatami.getView();
    }

}
