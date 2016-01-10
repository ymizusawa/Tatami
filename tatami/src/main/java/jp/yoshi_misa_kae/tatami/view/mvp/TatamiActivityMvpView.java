package jp.yoshi_misa_kae.tatami.view.mvp;

import android.os.Bundle;

public interface TatamiActivityMvpView extends MvpView {

    void callOnCreate(Bundle savedInstanceState);
    void callOnResume();
    void callOnPause();
    void callOnStop();
    void callOnDestroy();

}
