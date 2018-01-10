package com.sonu.app.splash.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by amanshuraikwar on 19/12/17.
 */

public class FragmentUtils {

    public static void addFragmentToUi(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void replaceFragmentInUi(@NonNull FragmentManager fragmentManager,
                                           @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        // adding it to BackStack
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
