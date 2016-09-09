/*
 * MyAwesomeApp project template
 *
 * Distributed under no licences and no warranty.
 */
package com.example.hwan.myapplication.util.logging;

import android.support.annotation.VisibleForTesting;

import com.example.hwan.myapplication.util.Transformer;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 01 - Sep - 2016
 */
public class LogFactory {
    public static final Transformer<String, Logger> DEFAULT_ANDROID_LOGGER = new Transformer<String, Logger>() {
        @Override
        public Logger transform(String input) {
            return new AndroidLoggerForDebug(input);
        }
    };

    private static final ConcurrentMap<String, SoftReference<Logger>> LOGGERS
            = new ConcurrentHashMap<>();
    private static Transformer<String, Logger> instanceFactory = DEFAULT_ANDROID_LOGGER;

    public static Logger getLogger(String tagName) {
        if (LOGGERS.get(tagName) == null) {
            return newLogger(tagName);
        } else {
            SoftReference<Logger> ref = LOGGERS.get(tagName);
            Logger l = ref.get();
            if (l != null) {
                return l;
            } else {
                return newLogger(tagName);
            }
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

        LOGGERS.put(tagName, new SoftReference<>(log));
        return log;
    }
}
