/*
 * MyAwesomeApp project template
 *
 * Distributed under no licences and no warranty.
 */
package com.example.hwan.myapplication.util.logging;

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 01 - Sep - 2016
 */
@SuppressWarnings("unused")
public interface Logger {
    void v(String format, Object... args);

    void v(Throwable t, String format, Object... args);

    void v(Object obj);

    void d(String format, Object... args);

    void d(Throwable t, String format, Object... args);

    void d(Object obj);

    void i(String format, Object... args);

    void i(Throwable t, String format, Object... args);

    void i(Object obj);

    void w(String format, Object... args);

    void w(Throwable t, String format, Object... args);

    void w(Object obj);

    void e(String format, Object... args);

    void e(Throwable t, String format, Object... args);

    void e(Object obj);

    void log(int priority, String message, Throwable t);
}
