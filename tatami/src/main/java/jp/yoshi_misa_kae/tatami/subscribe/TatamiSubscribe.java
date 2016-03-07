package jp.yoshi_misa_kae.tatami.subscribe;

import jp.yoshi_misa_kae.tatami.Tatami;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Yoshitaka Mizusawa on 2015/12/31.
 */
public class TatamiSubscribe {

    public static Observable<Void> onCreateEvent(final Tatami tatami) {
        return bindField(tatami);
//        return Observable.zip(bindField(tatami), onBindEvent(tatami), new Func2<Void, Void, Void>() {
//            @Override
//            public Void call(Void aVoid, Void aVoid2) {
//                return null;
//            }
//        });
    }

    public static Observable<Void> bindField(final Tatami tatami) {
        return
                Observable.create(
                        new Observable.OnSubscribe<Void>() {
                            @Override
                            public void call(Subscriber<? super Void> subscriber) {
                                tatami.bindField();

                                subscriber.onNext(null);
                                subscriber.onCompleted();
                            }
                        });

    }

    public static Observable<Void> onBindEvent(final Tatami tatami) {
        return
                Observable.create(
                        new Observable.OnSubscribe<Void>() {
                            @Override
                            public void call(Subscriber<? super Void> subscriber) {
                                tatami.bindEvent();

                                subscriber.onNext(null);
                                subscriber.onCompleted();
                            }
                        });
    }

    public static Observable<Void> onDestroy(final Tatami tatami) {
        return Observable.create(
                new Observable.OnSubscribe<Void>() {
                    @Override
                    public void call(Subscriber<? super Void> subscriber) {
                        tatami.destroy();

                        subscriber.onNext(null);
                        subscriber.onCompleted();
                    }
                });
    }

}
