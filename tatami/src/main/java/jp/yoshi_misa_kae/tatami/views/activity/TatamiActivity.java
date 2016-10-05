package jp.yoshi_misa_kae.tatami.views.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;

import jp.yoshi_misa_kae.tatami.TatamiBindViewBindView;
import jp.yoshi_misa_kae.tatami.TatamiPermission;
import jp.yoshi_misa_kae.tatami.presenters.TatamiActivityPresenter;

/**
 * Created by ymizusawa on 2016/08/08.
 */
public abstract class TatamiActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_PERMISSION = 3;

    private TatamiActivityPresenter tatamiPresenter;
    private TatamiBindViewBindView tatamiBindView;
    private TatamiPermission tatamiPermission;

    public abstract TatamiActivityPresenter getTatamiPresenter();

    @CallSuper
    protected void onTatamiCreate(@Nullable Bundle savedInstanceState) {
        TransitionSet ts = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ts = new TransitionSet();
            ts.addTransition(new Fade());
            ts.addTransition(new Slide());
            getWindow().setExitTransition(ts);
            getWindow().setEnterTransition(ts);
        }
    }
    protected void onTatamiStart() {}
    protected void onTatamiRestart() {}
    protected void onTatamiResume() {}
    protected void onTatamiPause() {}
    protected void onTatamiStop() {}
    protected void onTatamiDestroy() {}
    public void shouldShowRequestPermissionRationale() {}
    public void nonPermissionSettings() {}
    public void allPermissionSettings() {}

    @Deprecated
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tatamiBindView = new TatamiBindViewBindView();
        tatamiBindView.bindView(this);

        tatamiPresenter = getTatamiPresenter();
        tatamiPresenter.setTatamiActivity(this);
        tatamiPresenter.onTatamiCreate(savedInstanceState);

        onTatamiCreate(savedInstanceState);
    }

    @Deprecated
    @Override
    protected void onStart() {
        super.onStart();

        if (tatamiPermission == null)
            tatamiPermission = new TatamiPermission();
        tatamiPermission.checkPermission(this);

        tatamiPresenter.onTatamiStart();
        onTatamiStart();
    }

    @Deprecated
    @Override
    protected void onRestart() {
        super.onRestart();

        tatamiPresenter.onTatamiRestart();
        onTatamiRestart();
    }

    @Deprecated
    @Override
    protected void onResume() {
        super.onResume();

        tatamiPresenter.onTatamiResume();
        onTatamiResume();
    }

    @Deprecated
    @Override
    protected void onPause() {
        super.onPause();

        tatamiPresenter.onTatamiPause();
        onTatamiPause();
    }

    @Deprecated
    @Override
    protected void onStop() {
        super.onStop();

        tatamiPresenter.onTatamiStop();
        onTatamiStop();
    }

    @Deprecated
    @Override
    protected void onDestroy() {
        super.onDestroy();

        tatamiPresenter.onTatamiDestroy();
        onTatamiDestroy();

        tatamiPresenter = null;
    }

    protected void requestPermissions() {
        tatamiPermission.requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION)
            tatamiPermission.onRequestPermissionsResult(permissions, grantResults);
        else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        tatamiPresenter.onActivityResult(requestCode, resultCode, data);
    }

}
