package jp.yoshi_misa_kae.tatami.presenter;

import android.content.ComponentName;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import jp.yoshi_misa_kae.tatami.Tatami;
import jp.yoshi_misa_kae.tatami.annotations.view.ActivityInfo;
import jp.yoshi_misa_kae.tatami.subscribe.TatamiSubscribe;
import jp.yoshi_misa_kae.tatami.view.TatamiActivity;
import jp.yoshi_misa_kae.tatami.view.mvp.TatamiActivityMvpView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TatamiActivityPresenter implements Presenter<TatamiActivityMvpView> {

    private TatamiActivityMvpView mvpView;
    private Subscription subscription = null;
    private Tatami tatami = null;

    @Override
    public void attachView(TatamiActivityMvpView mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void detachView() {
        this.mvpView = null;

        if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
    }

    public void onCreate(Bundle savedInstanceState) {
        TatamiActivity activity = ((TatamiActivity) mvpView.getContext());
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tatami = new Tatami(activity);
        subscription = TatamiSubscribe.onCreate(tatami)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Tatami>() {

                        @Override
                        public void onCompleted() {
                            mvpView.callOnCreate();
                            Log.v("Tatami", "TatamiSubscribeActivity.onCreate onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(Tatami t) {
                            tatami = t;
                        }
                });
    }

    public void onDestroy() {
        subscription = TatamiSubscribe.onDestroy(tatami)
                .subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> {
                    new Observer<Void>() {

                        @Override
                        public void onCompleted() {
                            mvpView.callOnDestroy();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(Void t) {
                        }
                    };
                });
    }
    public void createToolbar(Toolbar tb) {
        if (tb == null)
            return;

        TatamiActivity activity = ((TatamiActivity) mvpView.getContext());
        activity.setSupportActionBar(tb);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null)
            return;

        ActivityInfo a = activity.getClass().getAnnotation(ActivityInfo.class);
        setTitle(a, actionBar);
        setSubTitle(a, actionBar);

        ComponentName cn = activity.getCallingActivity();
        if (cn != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setElevation(0f);
    }

    private void setSubTitle(ActivityInfo a, ActionBar actionBar) {
        String title = a.subTitleStr();
        if (!TextUtils.isEmpty(title)) {
            actionBar.setSubtitle(title);
            return;
        }

        int titleId = a.subTitleId();
        if(titleId != -1) {
            actionBar.setSubtitle(titleId);
        }
    }

    private void setTitle(ActivityInfo a, ActionBar actionBar) {
        String title = a.titleStr();
        if (!TextUtils.isEmpty(title)) {
            actionBar.setTitle(title);
            return;
        }

        int titleId = a.titleId();
        if(titleId != -1) {
            actionBar.setTitle(titleId);
        }
    }

}
