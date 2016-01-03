package jp.yoshi_misa_kae.tatami.subscribe;

import android.util.Log;

import jp.yoshi_misa_kae.tatami.Tatami;
import rx.Observable;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/31.
 */
public class TatamiSubscribe {

    public static Observable<Tatami> onCreate(Tatami tatami) {
        return Observable.zip(Observable.create(subscriber -> {
            tatami.bindField();

            Log.v("Tatami", "observableField");
            subscriber.onNext(true);
            subscriber.onCompleted();
        }), Observable.create(subscriber -> {
            tatami.bindEvent();

            Log.v("Tatami", "observableEvent");
            subscriber.onNext(true);
            subscriber.onCompleted();
        }), (response1, response2) -> {
            Log.v("Tatami", "observable response1 : " + response1 + " response2 : " + response2);
            return !((Boolean) response1 && (Boolean) response2) ? null : tatami;
        });
    }

    public static Observable<Void> onDestroy(Tatami tatami) {
        return Observable.create(subscriber -> {
            tatami.destroy();

            subscriber.onCompleted();
        });

    }

}
