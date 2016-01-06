package jp.yoshi_misa_kae.tatami.view.mvp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

public interface TatamiFragmentMvpView extends MvpView {

    Fragment getFragment();
    void callOnCreateView(View view);
    void callOnActivityCreated();
    void callOnResume();
    void callOnPause();
    void callOnStop();
    void callOnDestroyView();

}
