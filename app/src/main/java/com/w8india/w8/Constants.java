package com.w8india.w8;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class Constants {
    private static boolean result = true;
    public static boolean isOnline(Context context) {

        NetworkHelper.checkNetworkInfo(context, type -> result = type);

        return result;
    }

    public static void showSnack(View root, String snacktitle){
        Snackbar snackbar = Snackbar.make(root, snacktitle, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static class DialogUtils{
        public static ProgressDialog showprgdialog(Context context){
            ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            return progressDialog;
        }
    }
    public static void it(Context context, String user){
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        Bundle params = new Bundle();

        params.putString("who", user);
        mFirebaseAnalytics.logEvent(user, params);

    }
//    public static boolean checkConnection(Context context) {
//        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        if (connMgr != null) {
//            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
//
//            if (activeNetworkInfo != null) { // connected to the internet
//                // connected to the mobile provider's data plan
//                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                    // connected to wifi
//                    return true;
//                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
//            }
//        }
//        return false;
//    }

}
