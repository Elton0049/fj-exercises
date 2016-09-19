/*
 * MyAwesomeApp project template
 *
 * Distributed under no licences and no warranty.
 */
package com.example.hwan.myapplication.util.logging;

import android.support.annotation.VisibleForTesting;
import android.support.v4.util.LruCache;

import com.example.hwan.myapplication.util.Transformer;

import java.lang.ref.SoftReference;

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 01 - Sep - 2016
 */
public class LogFactory {
    public static final Transformer<String, Logger> DEFAULT_ANDROID_LOGGER = new Transformer<String, Logger>() {
        @Override
        public Logger transform(String loggerName) {
            return new AndroidLoggerForDebug(loggerName);
        }
    };

    private static final int MAXIMUM_LOGGERS_SIZE = 20;

    private static final LruCache<String, SoftReference<Logger>> LOGGERS
            = new LruCache<>(MAXIMUM_LOGGERS_SIZE);
    private static Transformer<String, Logger> instanceFactory = DEFAULT_ANDROID_LOGGER;

    public static Logger getLogger(String tagName) {
        synchronized (LOGGERS) {
            if (LOGGERS.get(tagName) == null) {
                return newLogger(tagName);
            }
        }

        SoftReference<Logger> ref;
        synchronized (LOGGERS) {
            ref = LOGGERS.get(tagName);
        }

        Logger l = ref.get();
        if (l != null) {
            return l;
        } else {
            return newLogger(tagName);
        }
    }

    @VisibleForTesting
    public static Transformer<String, Logger> getInstanceFactory() {
        return LogFactory.instanceFactory;
    }

    @VisibleForTesting
    public static void setLoggerFactory(Transformer<String, Logger> loggerInstanceFactory) {
        if (null == loggerInstanceFactory) {
            throw new IllegalArgumentException("InstanceFactory must not be null");
        }
        LogFactory.instanceFactory = loggerInstanceFactory;
    }

    private static Logger newLogger(String tagName) {
        Logger log;
        try {
            log = instanceFactory.transform(tagName);
        } catch (Exception e) {
            throw new RuntimeException("Exception occured in given instanceFactory: " + instanceFactory, e);
        }

        synchronized (LOGGERS) {
            LOGGERS.put(tagName, new SoftReference<>(log));
        }

        return log;
    }
}
