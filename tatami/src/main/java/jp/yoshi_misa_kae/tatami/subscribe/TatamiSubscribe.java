package jp.yoshi_misa_kae.tatami.subscribe;

import android.util.Log;
import jp.yoshi_misa_kae.tatami.Tatami;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func2;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/31.
 */
public class TatamiSubscribe {

    public static Observable<Void> onCreate(final Tatami tatami) {
        return
//        Observable.zip(
            Observable.create(
            new Observable.OnSubscribe<Void>() {
                    @Override
                    public void call(Subscriber<? super Void> subscriber) {
                        tatami.bindField();

                        Log.v("Tatami", "observableField");
                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    }});
//                    , Observable.create(
//
//                , new Func2<Boolean, Boolean, Tatami>() {
//
//                @Override
//                public Tatami call(Boolean response1, 
//                                   Boolean response2) {
//                    Log.v("Tatami", "observable response1 : " + response1 + " response2 : " + response2);
//                    return !((Boolean) response1 && (Boolean) response2) ? null : tatami;
//                }
//            });
    }

    public static Observable<Void> onCreateEvent(final Tatami tatami) {
        return
            Observable.create(
        new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                tatami.bindEvent();

                Log.v("Tatami", "observableEvent");
                subscriber.onNext(null);
                subscriber.onCompleted();
            }});
    }
    
    public static Observable<Void> onDestroy(final Tatami tatami) {
        return Observable.create(
            new Observable.OnSubscribe<Void>() {
                @Override
                public void call(Subscriber<? super Void> subscriber) {

                    tatami.destroy();

                    subscriber.onCompleted();
                }
            });

    }

}
