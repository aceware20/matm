package com.acemoney.matm.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.acemoney.matm.view.Activity_MicroAtm;
import com.google.gson.JsonObject;

public class AceMatm {
    private static AceMatm instance;

    private Context context;
    private String agentEmail;
    private ResponseListener responseListener;

    // Private constructor to prevent external instantiation
    private AceMatm(@NonNull Context context, @NonNull String agentEmail) {
        this.context = context.getApplicationContext();
        this.agentEmail = agentEmail;
    }

    // Singleton instance getter method
    public static AceMatm getInstance(Context context, String agentEmail) {
        if (instance == null) {
            instance = new AceMatm(context, agentEmail);
        }
        return instance;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void setUI(boolean startMicroAtmActivity) {
        if (context == null) {
            Log.e("AceMatm", "Context is null. Unable to start activity.");
            return;
        }
        if (startMicroAtmActivity) {
            Intent intent = new Intent(context, Activity_MicroAtm.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("email", agentEmail);
            context.startActivity(intent);
        } else {
            // Handle other cases if needed
        }
    }

    public void sendData(JsonObject jsonObject) {
        if (responseListener != null) {
            responseListener.onResult(jsonObject);
        } else {
            Log.d("AceMatm", "ResponseListener is null. Data not sent.");
        }
    }

    public interface ResponseListener {
        void onResult(JsonObject jsonData);
    }
}