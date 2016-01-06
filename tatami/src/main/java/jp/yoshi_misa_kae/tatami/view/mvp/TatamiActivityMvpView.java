package jp.yoshi_misa_kae.tatami.view.mvp;

public interface TatamiActivityMvpView extends MvpView {

    void callOnCreate();
    void callOnResume();
    void callOnPause();
    void callOnStop();
    void callOnDestroy();

}
