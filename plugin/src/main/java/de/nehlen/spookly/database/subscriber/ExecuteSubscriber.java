package de.nehlen.spookly.database.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class ExecuteSubscriber implements Subscriber {
    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(1);
    }

    @Override
    public void onNext(Object object) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
