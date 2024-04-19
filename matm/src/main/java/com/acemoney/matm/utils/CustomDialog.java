package com.acemoney.matm.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;


public class CustomDialog extends ProgressDialog {
    private Context mContext;

    public CustomDialog(Context context) {
        super(context);
        mContext=context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setIndeterminate(true);
        //setIcon(R.mipmap.);
        setMessage("Please wait...");
        setCancelable(false);
//        setContentView(R.layout.custom_dialog);
    }
    public void show() {
//        if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
//            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        else
//            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.show();
    }

    public void dismiss() {
        super.dismiss();
//        ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }
}
