/*
 * MyAwesomeApp project template
 *
 * Distributed under no licences and no warranty.
 */
package com.example.hwan.myapplication.rx;

import com.example.hwan.myapplication.BuildConfig;
import com.example.hwan.myapplication.util.logging.LogFactory;
import com.example.hwan.myapplication.util.logging.Logger;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 22 - Aug - 2016
 */
public class SubscriberBuilder<T> {
    private boolean isBuilt = false;

    boolean isThrowNPE = false;
    Action1<Void> onCompletedAction;
    Action1<Throwable> onErrorAction;
    Action1<T> onNextAction;
    private final StackTraceElement callerTrace;

    public SubscriberBuilder() {
        if (BuildConfig.DEBUG) {
            callerTrace = Thread.currentThread().getStackTrace()[3];
        } else {
            callerTrace = null;
        }
    }

    public SubscriberBuilder<T> onCompleted(Action1<Void> onCompletedAction) {
        assertNotBuilt();
        this.onCompletedAction = onCompletedAction;
        return this;
    }

    public SubscriberBuilder<T> onError(Action1<Throwable> onErrorAction) {
        assertNotBuilt();
        this.onErrorAction = onErrorAction;
        return this;
    }

    public SubscriberBuilder<T> onNext(Action1<T> onNextAction) {
        assertNotBuilt();
        this.onNextAction = onNextAction;
        return this;
    }

    public SubscriberBuilder<T> penaltyNullAction(boolean flag) {
        this.isThrowNPE = flag;
        return this;
    }

    private void assertNotBuilt() {
        if (isBuilt) {
            throw new IllegalStateException("This SubscriberBuilder already built a Subscriber and must not be reused.");
        }
    }

    public Subscriber<T> build() {
        isBuilt = true;

        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                if (null == onCompletedAction) {
                    processNull("onCompleted");
                    return;
                }
                onCompletedAction.call(null);
            }

            @Override
            public void onError(Throwable e) {
                if (null == onErrorAction) {
                    processNull("onError");
                    return;
                }
                onErrorAction.call(e);
            }

            @Override
            public void onNext(T t) {
                if (null == onNextAction) {
                    processNull("onNext");
                    return;
                }
                onNextAction.call(t);
            }

            private void processNull(String methodName) {
                String msg = String.format("%s implementation required but no action was assigned via %s#%s().",
                        methodName, SubscriberBuilder.class.getSimpleName(), methodName);

                if (BuildConfig.DEBUG) {
                    Logger log = LogFactory.getLogger(SubscriberBuilder.class.getSimpleName());
                    log.w(msg);
                    log.w(String.format("Call stack: %s", callerTrace));
                }

                if (isThrowNPE) {
                    throw new NullPointerException(msg);
                }
            }
        };
    }
}
