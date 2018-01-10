package com.sonu.app.splash.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;

/**
 * Created by amanshuraikwar on 30/12/17.
 */

public class ConnectionUtil {

    public static boolean isConnected(Context context) {

        try {

            final ConnectivityManager connectivityManager =
                    (ConnectivityManager)
                            context.getSystemService(Context.CONNECTIVITY_SERVICE);

            final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            return activeNetworkInfo != null && activeNetworkInfo.isConnected();

        } catch (NullPointerException e) {

            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerNetworkCallback(Context context,
                                               ConnectivityManager.NetworkCallback callback) {

        try {
            final ConnectivityManager connectivityManager
                    = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            connectivityManager.registerNetworkCallback(
                    new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
                    callback);

            return true;
        } catch (NullPointerException e) {

            e.printStackTrace();
            return false;
        }
    }

    public static boolean unregisterNetworkCallback(Context context,
                                                    ConnectivityManager.NetworkCallback callback) {

        try {

            final ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.unregisterNetworkCallback(callback);

            return true;
        } catch (NullPointerException e) {

              e.printStackTrace();
            return false;
        }
    }
}
