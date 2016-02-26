package jp.yoshi_misa_kae.tatami.presenter;

public interface Presenter<V> {

    void attachView(V mvpView);

    void detachView();

}
