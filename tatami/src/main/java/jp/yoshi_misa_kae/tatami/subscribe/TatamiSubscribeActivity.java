package jp.yoshi_misa_kae.tatami.subscribe;

import jp.yoshi_misa_kae.tatami.Tatami;
import jp.yoshi_misa_kae.tatami.view.TatamiActivity;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/28.
 */
public class TatamiSubscribeActivity {

    public static Observable<Tatami> onCreate(final TatamiActivity activity) {
        return Observable.create(new Observable.OnSubscribe<Tatami>() {

            @Override
            public void call(Subscriber<? super Tatami> subscriber) {
                Tatami tatami = new Tatami(activity);
                tatami.inject();

                subscriber.onNext(tatami);

                subscriber.onCompleted();
            }

        });
    }
}
