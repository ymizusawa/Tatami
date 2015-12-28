package jp.yoshi_misa_kae.tatami.subscribe;

import jp.yoshi_misa_kae.tatami.Tatami;
import jp.yoshi_misa_kae.tatami.view.TatamiActivity;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/28.
 */
public class TatamiSubscribeActivity {

    public static Observable<Void> onCreate(final TatamiActivity activity) {
        return Observable.create(new Observable.OnSubscribe<Void>() {

            @Override
            public void call(Subscriber<? super Void> subscriber) {
                Tatami.setContentView(activity);
                Tatami.injectField(activity);
                Tatami.injectEvent(activity);
                Tatami.injectExtra(activity);

                subscriber.onCompleted();
            }

        });
    }
}
