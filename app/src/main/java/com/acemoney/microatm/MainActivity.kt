package com.acemoney.microatm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.acemoney.matm.utils.AceMatm;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MainActivity extends AppCompatActivity implements AceMatm.ResponseListener {
    private AceMatm aceMatm;
    private String Agent_email;
    private Activity activity=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Agent_email="sruthys240@gmail.com";
        AceMatm aceMatm= AceMatm.getInstance(activity, Agent_email);
        aceMatm.setUI(true);
        aceMatm.setResponseListener(this);
    }


    @Override
    public void onResult(JsonObject jsonData) {
        Log.d("dataFROM",new Gson().toJson(jsonData));
    }
}