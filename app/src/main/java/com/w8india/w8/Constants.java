package com.w8india.w8;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class Constants {
    public static  boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;

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

}
