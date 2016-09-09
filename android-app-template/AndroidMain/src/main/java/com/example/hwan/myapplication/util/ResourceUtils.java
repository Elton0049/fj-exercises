/*
 * MyAwesomeApp project template
 *
 * Distributed under no licences and no warranty.
 */
package com.example.hwan.myapplication.util;

import android.content.res.Resources;
import android.os.Build;

import com.example.hwan.myapplication.MyApplication;

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 9 - Sep - 2016
 */
public class ResourceUtils {
    // Resources#getColor(int) is Deprecated in API Level 23
    @SuppressWarnings("deprecation")
    public static int getColor(int resId) {
        Resources res = MyApplication.getInstance().getResources();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return res.getColor(resId);
        } else {
            return res.getColor(resId, null);
        }
    }
}
