package com.acemoney.matm.view;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.acemoney.matm.R;
import com.acemoney.matm.databinding.ActivityMicroatmBinding;
import com.acemoney.matm.model.api.APIInterface;
import com.acemoney.matm.model.api.ApiClient;
import com.acemoney.matm.transferData.TransferDataMain;
import com.acemoney.matm.utils.AceMatm;
import com.acemoney.matm.utils.CustomDialog;
import com.acemoney.matm.utils.MyLocationListener;
import com.fingpay.microatmsdk.MATMHistoryScreen;
import com.fingpay.microatmsdk.MicroAtmLoginScreen;
import com.fingpay.microatmsdk.utils.Constants;
import com.fingpay.microatmsdk.utils.Utils;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;
import retrofit2.Response;

public class Activity_MicroAtm extends AppCompatActivity  {
    //private Activity activity=this;
    private static final int REQUEST_READ_PHONE_STATE = 145;
    ActivityMicroatmBinding activityMicroatmBinding;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Context context;
    CustomDialog customDialog;
    String Email;
    String password, phone, email,amount="",remarks="jvhgvhvv";
    //    private FusedLocationProviderClient fusedLocationClient;
    double Latitude,Longitude;
    boolean status;
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{
            Manifest.permission.READ_PHONE_STATE
    };
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    private SharedPreferences permissionStatus;
    String catgSelection="Cash Withdraw";
    Activity activity=this;
    String deviceId;
    BluetoothAdapter mBluetoothAdapter;
    private static final int REQ_CODE = 134;
    AlertDialog.Builder builder, builder1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMicroatmBinding = ActivityMicroatmBinding.inflate(getLayoutInflater());
        setContentView(activityMicroatmBinding.getRoot());
        context=Activity_MicroAtm.this;
        builder1 = new AlertDialog.Builder(context);
        if(getIntent()!=null)
        {
            Email= getIntent().getStringExtra("email");
        }
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!isBluetoothEnabled()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT}, REQ_CODE);
                } else {
                    mBluetoothAdapter.enable();
                }
            }
            // Log.d("test","onResume");
            else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT}, REQ_CODE);
                } else {
                    showBluetoothAlert();
                }

            } else {
                showBluetoothAlert();
            }
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("email", Email);
        funTransferData(map, (Activity_MicroAtm) activity);
        checkLocationPermission();
        radioSelection();
            ActivityCompat.requestPermissions( Activity_MicroAtm.this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        activityMicroatmBinding.btnSUbmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status) {
                    switch (catgSelection) {
                        case "Cash Withdraw":
                            if (activityMicroatmBinding.edtAmount.getText().toString().equalsIgnoreCase("")) {
                                Toast.makeText(context, "Please Enter amount", Toast.LENGTH_SHORT).show();
                            } else if (!activityMicroatmBinding.edtAmount.getText().toString().equalsIgnoreCase("")&& Integer.parseInt(activityMicroatmBinding.edtAmount.getText().toString())<100) {
                                Toast.makeText(context, "Invalid amount,, Minimum withdrawal amount is Rs.100", Toast.LENGTH_SHORT).show();
                            }
                            else if(Integer.parseInt(activityMicroatmBinding.edtAmount.getText().toString().trim())%50!=0)
                            {
                                Toast.makeText(context, "Invalid amount,, Please enter multiples of 50", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                amount = activityMicroatmBinding.edtAmount.getText().toString();
                                System.out.println("CHECK DATA LOCATION ---   " + Latitude);
                                System.out.println("CHECK DATA LOCATION ---   " + Longitude);
                                show(Latitude, Longitude, email, password, phone, amount,
                                        remarks,
                                        0, 1);
                            }
                            break;
                        case "Balance":

                            System.out.println("CHECK DATA LOCATION ---   " + Latitude);
                            System.out.println("CHECK DATA LOCATION ---   " + Longitude);
                            show(Latitude, Longitude, email, password, phone, amount, remarks, 0, 3);
                            break;
                        case "MPOS":
                            amount = activityMicroatmBinding.edtAmount.getText().toString();
                            show(Latitude, Longitude, email, password, phone, amount, remarks, 0, 8);
                            break;
                        case "History":
                            historyData();
                            break;
                    }
                }
                else
                {
                    Toast.makeText(context, "User data not found", Toast.LENGTH_SHORT).show();
                }

            }
        });
        activityMicroatmBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void showBluetoothAlert() {
        Log.d("test", "test2");
        builder1.setMessage("Allow App to turn ON your bluetooth")
                .setCancelable(false)
                .setPositiveButton("Allow", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
                            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
                                mBluetoothAdapter.enable();
                                Log.d("test", "enabled");
                            }
                        } else {
                            mBluetoothAdapter.enable();
                        }
                        Toast.makeText(context, "Bluetooth is turned on", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        showBluetoothAlert();
                    }
                });
        AlertDialog alertBlutooth = builder1.create();
//        alert.setTitle(R.string.app_name);
        alertBlutooth.show();
        alertBlutooth.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.black));
        alertBlutooth.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.black));
    }

    private boolean isBluetoothEnabled() {
        Log.d("test", String.valueOf(mBluetoothAdapter.isEnabled()));
        return mBluetoothAdapter.isEnabled();

    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    public void funTransferData(HashMap<String, String> map, Activity_MicroAtm activity_microAtm)
    {
        context=activity_microAtm;
        customDialog=new CustomDialog(context);
        customDialog.show();
        System.out.println("TRANSFER DATA MAIN -------  "+map);
        APIInterface apiInterface = ApiClient.getRetrofit().create(APIInterface.class);
        retrofit2.Call<TransferDataMain> call = apiInterface.transferData(map);
        call.enqueue(new Callback<TransferDataMain>() {
            @Override
            public void onResponse(retrofit2.Call<TransferDataMain> call, Response<TransferDataMain> response) {
                customDialog.dismiss();
                try
                {
                    Log.e("RESPONSE -- >>>>>.  ", String.valueOf(response.body()));
                    if (response.body() != null && response.isSuccessful()) {

                        status=response.body().getSuccess();
                        if (status){
                            password=response.body().getData().getPassword();
                            email=response.body().getData().getEmail();
                            phone=response.body().getData().getPhone1();
                            System.out.println("TRANSFER DATA  -----  "+response.body().getData().getPassword());
                            System.out.println("TRANSFER DATA  -----  "+response.body().getData().getEmail());
                            System.out.println("TRANSFER DATA  -----  "+response.body().getData().getPhone1());
                        }else {
                            System.out.println("NEO BANK -----2 >>>> "+status);
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<TransferDataMain> call, Throwable t) {
                customDialog.dismiss();
                t.printStackTrace();
            }


        });

    }

    private void radioSelection() {
        activityMicroatmBinding.radioATM.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = activityMicroatmBinding.radioATM.getCheckedRadioButtonId();
                if (id == R.id.radiocashwithraw) {// Your code
                    activityMicroatmBinding.linearCash.setVisibility(View.VISIBLE);
                    catgSelection = "Cash Withdraw";
                } else if (id == R.id.radiobalance) {// Your code
                    activityMicroatmBinding.linearCash.setVisibility(View.GONE);
                    catgSelection = "Balance";
                } else if (id == R.id.radioHistory) {// Your code
                    activityMicroatmBinding.linearCash.setVisibility(View.GONE);
                    catgSelection = "History";
                } else if (id == R.id.radioPurchase) {// Your code
                    activityMicroatmBinding.linearCash.setVisibility(View.VISIBLE);
                    catgSelection = "MPOS";
                } else {// Your code
                    catgSelection = "Cash Withdraw";
                    activityMicroatmBinding.linearCash.setVisibility(View.GONE);
                }
            }
        });
    }

    public void show(Double latitude,Double longitude,String merchantid_passed,
                     String password_passed,String mobile_passed,String amount_passed,
                     String remarks_passed,Integer mainChoice ,Integer transactionOption) {
        System.out.println("ACENEO ---Test if  ");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED  ||ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED||ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Activity_MicroAtm.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_VIDEO,Manifest.permission.READ_MEDIA_AUDIO}, 2);
            }
            else{
                System.out.println("ACENEO ---Test else  ");
                try {
                    String deviceId;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        deviceId = Settings.Secure.getString(
                                context.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                    } else {
                        final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        if (mTelephony.getDeviceId() != null) {
                            deviceId = mTelephony.getDeviceId();
                        } else {
                            deviceId = Settings.Secure.getString(
                                    context.getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                        }
                    }

                    System.out.println("ACENEO ---Test 1  "+deviceId);

                    Intent intent = new Intent(getApplicationContext(), MicroAtmLoginScreen.class);
                    System.out.println("ACENEO ---BUG 1  ");
                    intent.putExtra(Constants.MERCHANT_USERID, merchantid_passed);
                    // this MERCHANT_USERID be given by FingPay depending on the merchant, only that value need to sent from App to SDK
                    System.out.println("ACENEO ---BUG 2  ");
                    intent.putExtra(Constants.MERCHANT_PASSWORD, password_passed);
                    // this MERCHANT_PASSWORD be given by FingPay depending on the merchant, only that value need to sent from App to SDK
                    System.out.println("ACENEO ---BUG 3  ");
                    intent.putExtra(Constants.AMOUNT, amount_passed);
                    System.out.println("ACENEO ---BUG 4  ");
                    intent.putExtra(Constants.REMARKS, remarks_passed);
                    System.out.println("ACENEO ---BUG 5  ");

                    intent.putExtra(Constants.MOBILE_NUMBER, mobile_passed);
                    // send a valid 10 digit mobile number from the app to SDK
                    System.out.println("ACENEO ---BUG 6  ");
                    intent.putExtra(Constants.AMOUNT_EDITABLE, false);
                    // send true if Amount can be edited in the SDK or send false then Amount cant be edited in the SDK
                    System.out.println("ACENEO ---BUG 7  ");
                    String s = "aceware" + (new Date().getTime());
                    intent.putExtra(Constants.TXN_ID, s);
                    //Utils.logD(s);
                    // some dummy value is given in TXN_ID for now but some proper value should come from App to SDK
                    System.out.println("ACENEO ---BUG 8  ");
                    intent.putExtra(Constants.SUPER_MERCHANTID, "920");
                    // this SUPER_MERCHANT_ID be given by FingPay to you, only that value need to sent from App to SDK
                    System.out.println("ACENEO ---BUG 9  ");
                    intent.putExtra(Constants.IMEI, deviceId);
                    System.out.println("ACENEO ---BUG 10  ");
                    double lat = 17.4442015, lng = 78.4808421;  // get current location and send these values
//                                double lat = Latitude, lng =Longitude;
                    intent.putExtra(Constants.LATITUDE, lat);
                    System.out.println("ACENEO ---BUG 11  ");
                    intent.putExtra(Constants.LONGITUDE, lng);
                    System.out.println("ACENEO ---BUG 12  ");

                    switch (transactionOption) {
                        case 1:
                            System.out.println("ACENEO ---Test 1 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.CASH_WITHDRAWAL);
                            Log.e("Type",""+Constants.CASH_WITHDRAWAL);
                            System.out.println("ACENEO ---BUG 13  ");
                            break;

                        case 2:
                            System.out.println("ACENEO ---Test 2 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.CASH_DEPOSIT);
                            System.out.println("ACENEO ---BUG 14  ");
                            break;

                        case 3:
                            System.out.println("ACENEO ---Test 3 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.BALANCE_ENQUIRY);
                            Log.e("Type",""+Constants.BALANCE_ENQUIRY);
                            System.out.println("ACENEO ---BUG 15  ");
                            break;

                        case 4:
                            System.out.println("ACENEO ---Test 4 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.MINI_STATEMENT);
                            System.out.println("ACENEO ---BUG 16  ");
                            break;

                        case 5:
                            System.out.println("ACENEO ---Test 5 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.PIN_RESET);
                            System.out.println("ACENEO ---BUG 17  ");
                            break;

                        case 6:
                            System.out.println("ACENEO ---Test 6 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.CHANGE_PIN);
                            System.out.println("ACENEO ---BUG 18  ");
                            break;

                        case 7:
                            System.out.println("ACENEO ---Test 7 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.CARD_ACTIVATION);
                            System.out.println("ACENEO ---BUG 19  ");
                            break;
                        case 8:
                            System.out.println("ACENEO ---Test 7 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.PURCHASE);
                            System.out.println("ACENEO ---BUG 19  ");
                            break;
                    }

                    intent.putExtra(Constants.MICROATM_MANUFACTURER, Constants.MoreFun);
                    Log.d("inaddPropertydata",intent.getExtras().toString());
                    startActivityForResult(intent,2);
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "error",Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }

            }
        }
        else {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Activity_MicroAtm.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
            else{
                System.out.println("ACENEO ---Test else  ");
                try {
                    String deviceId;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        deviceId = Settings.Secure.getString(
                                context.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                    } else {
                        final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        if (mTelephony.getDeviceId() != null) {
                            deviceId = mTelephony.getDeviceId();
                        } else {
                            deviceId = Settings.Secure.getString(
                                    context.getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                        }
                    }

                    System.out.println("ACENEO ---Test 1  "+deviceId);

                    Intent intent = new Intent(getApplicationContext(), MicroAtmLoginScreen.class);
                    System.out.println("ACENEO ---BUG 1  ");
                    intent.putExtra(Constants.MERCHANT_USERID, merchantid_passed);
                    // this MERCHANT_USERID be given by FingPay depending on the merchant, only that value need to sent from App to SDK
                    System.out.println("ACENEO ---BUG 2  ");
                    intent.putExtra(Constants.MERCHANT_PASSWORD, password_passed);
                    // this MERCHANT_PASSWORD be given by FingPay depending on the merchant, only that value need to sent from App to SDK
                    System.out.println("ACENEO ---BUG 3  ");
                    intent.putExtra(Constants.AMOUNT, amount_passed);
                    System.out.println("ACENEO ---BUG 4  ");
                    intent.putExtra(Constants.REMARKS, remarks_passed);
                    System.out.println("ACENEO ---BUG 5  ");

                    intent.putExtra(Constants.MOBILE_NUMBER, mobile_passed);
                    // send a valid 10 digit mobile number from the app to SDK
                    System.out.println("ACENEO ---BUG 6  ");
                    intent.putExtra(Constants.AMOUNT_EDITABLE, false);
                    // send true if Amount can be edited in the SDK or send false then Amount cant be edited in the SDK
                    System.out.println("ACENEO ---BUG 7  ");
                    String s = "aceware" + (new Date().getTime());
                    intent.putExtra(Constants.TXN_ID, s);
                    //Utils.logD(s);
                    // some dummy value is given in TXN_ID for now but some proper value should come from App to SDK
                    System.out.println("ACENEO ---BUG 8  ");
                    intent.putExtra(Constants.SUPER_MERCHANTID, "920");
                    // this SUPER_MERCHANT_ID be given by FingPay to you, only that value need to sent from App to SDK
                    System.out.println("ACENEO ---BUG 9  ");
                    intent.putExtra(Constants.IMEI, deviceId);
                    System.out.println("ACENEO ---BUG 10  ");
                    double lat = 17.4442015, lng = 78.4808421;  // get current location and send these values
//                                double lat = Latitude, lng =Longitude;
                    intent.putExtra(Constants.LATITUDE, lat);
                    System.out.println("ACENEO ---BUG 11  ");
                    intent.putExtra(Constants.LONGITUDE, lng);
                    System.out.println("ACENEO ---BUG 12  ");

                    switch (transactionOption) {
                        case 1:
                            System.out.println("ACENEO ---Test 1 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.CASH_WITHDRAWAL);
                            Log.e("Type",""+Constants.CASH_WITHDRAWAL);
                            System.out.println("ACENEO ---BUG 13  ");
                            break;

                        case 2:
                            System.out.println("ACENEO ---Test 2 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.CASH_DEPOSIT);
                            System.out.println("ACENEO ---BUG 14  ");
                            break;

                        case 3:
                            System.out.println("ACENEO ---Test 3 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.BALANCE_ENQUIRY);
                            Log.e("Type",""+Constants.BALANCE_ENQUIRY);
                            System.out.println("ACENEO ---BUG 15  ");
                            break;

                        case 4:
                            System.out.println("ACENEO ---Test 4 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.MINI_STATEMENT);
                            System.out.println("ACENEO ---BUG 16  ");
                            break;

                        case 5:
                            System.out.println("ACENEO ---Test 5 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.PIN_RESET);
                            System.out.println("ACENEO ---BUG 17  ");
                            break;

                        case 6:
                            System.out.println("ACENEO ---Test 6 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.CHANGE_PIN);
                            System.out.println("ACENEO ---BUG 18  ");
                            break;

                        case 7:
                            System.out.println("ACENEO ---Test 7 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.CARD_ACTIVATION);
                            System.out.println("ACENEO ---BUG 19  ");
                            break;
                        case 8:
                            System.out.println("ACENEO ---Test 7 -----  ");
                            intent.putExtra(Constants.TYPE, Constants.PURCHASE);
                            System.out.println("ACENEO ---BUG 19  ");
                            break;
                    }

                    intent.putExtra(Constants.MICROATM_MANUFACTURER, Constants.MoreFun);
                    Log.d("inaddPropertydata",intent.getExtras().toString());
                    startActivityForResult(intent,2);
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "error",Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }

            }
        }


    }
    public String getDeviceIdInString()
    {
        String deviceId = "";
        deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        return deviceId;
    }
    public void historyData(){
        deviceId=getDeviceIdInString();

        String mId = email;
        String pswd =password;
        System.out.println("ACENEO ---Test 2  "+mId);
        System.out.println("ACENEO ---Test 2  "+pswd);
        System.out.println("ACENEO ---Test 2  "+deviceId);
        //Utils.dissmissKeyboard(merchIdEt);
        Intent intent1 = new Intent(getApplicationContext(), MATMHistoryScreen.class);
        intent1.putExtra(Constants.MERCHANT_USERID, mId);
        intent1.putExtra(Constants.MERCHANT_PASSWORD, pswd);
        intent1.putExtra(Constants.SUPER_MERCHANTID, "920");
        intent1.putExtra(Constants.IMEI, deviceId);
        startActivityForResult(intent1, 2);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!=null) {
            try {
                boolean status = data.getBooleanExtra(Constants.TRANS_STATUS, false);
                String response = data.getStringExtra(Constants.MESSAGE);
//            Toast.makeText(activity,response,Toast.LENGTH_LONG).show();
                double transAmount = data.getDoubleExtra(Constants.TRANS_AMOUNT, 0);
                double balAmount = data.getDoubleExtra(Constants.BALANCE_AMOUNT, 0);
                String bankRrn = data.getStringExtra(Constants.RRN);
                String transType = data.getStringExtra(Constants.TRANS_TYPE);
                int type = data.getIntExtra(Constants.TYPE, Constants.CASH_WITHDRAWAL);
                String cardNum = data.getStringExtra(Constants.CARD_NUM);
                String bankName = data.getStringExtra(Constants.BANK_NAME);
                String cardType = data.getStringExtra(Constants.CARD_TYPE);
                String terminalId = data.getStringExtra(Constants.TERMINAL_ID);
                String fpId = data.getStringExtra(Constants.FP_TRANS_ID);
                String transId = data.getStringExtra(Constants.TRANS_ID);
                String response_code=data.getStringExtra(Constants.RESPONSE_CODE);
                System.out.println("Acemneo Bank DATA-------   "+data.getDoubleExtra(Constants.BALANCE_AMOUNT, 0));
                System.out.println("Acemneo Bank status-------   "+status);
                System.out.println("Acemneo Bank response-------   "+response);
                System.out.println("Acemneo Bank response-------   "+response_code);
                System.out.println("Acemneo Bank transAmount-------   "+transAmount);
                System.out.println("Acemneo Bank balAmount-------   "+balAmount);
                System.out.println("Acemneo Bank bankRrn-------   "+bankRrn);
                System.out.println("Acemneo Bank transType-------   "+transType);
                System.out.println("Acemneo Bank type-------   "+type);
                System.out.println("Acemneo Bank cardNum-------   "+cardNum);
                System.out.println("Acemneo Bank bankName-------   "+bankName);
                System.out.println("Acemneo Bank cardType-------   "+cardType);
                System.out.println("Acemneo Bank terminalId-------   "+terminalId);
                System.out.println("Acemneo Bank fpId-------   "+fpId);
                System.out.println("Acemneo Bank transId-------   "+transId);
                String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

                JsonObject jsonData=new JsonObject();
                jsonData.addProperty("fpId", fpId);
                jsonData.addProperty("cardType", cardType);
                jsonData.addProperty("bankName", bankName);
                jsonData.addProperty("type", type);
                jsonData.addProperty("cardNum", cardNum);
                jsonData.addProperty("transType", transType);
                jsonData.addProperty("balAmount", balAmount);
                jsonData.addProperty("status", status);
                jsonData.addProperty("transId", transId);
                jsonData.addProperty("transAmount", transAmount);
                jsonData.addProperty("terminalId", terminalId);
                jsonData.addProperty("response", response);
                jsonData.addProperty("response_code",response_code);
                jsonData.addProperty("bankRrn", bankRrn);
                jsonData.addProperty("txndate", currentDate);
                AceMatm aceMatm= AceMatm.getInstance(activity,Email);
                aceMatm.sendData(jsonData);

            }
            catch (Exception ae)
            {
                throw ae;
            }



        }
    }
    

    public void getLocation(){
        double latitude, longitude;
        LocationManager mlocManager;
        LocationListener mlocListener;
        mlocManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                 mlocListener);
        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            latitude = MyLocationListener.latitude;
            longitude = MyLocationListener.longitude;

            System.out.println("ACENEO ---- "+latitude);
            System.out.println("ACENEO ----2 "+longitude);
            Latitude= (latitude);
            Longitude= (longitude);
//            Latitude= (10.509106);
//            Longitude= (76.096370);
        } else {
            Toast.makeText(context, "GPS is currently off...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        getLocation();
                    }

                } else {
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            boolean allgranted = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (!allgranted) {
                if (Utils.isValidArrayList((ArrayList<?>) getUngrantedPermissions())) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_MicroAtm.this);
                    builder.setTitle(getString(R.string.need_permissions));
                    builder.setMessage(getString(R.string.device_permission));
                    builder.setPositiveButton(getString(R.string.grant), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ActivityCompat.requestPermissions(Activity_MicroAtm.this,
                                    permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.unable_toget_permission), Toast.LENGTH_LONG).show();
                }
            }
        }
        if (requestCode == REQ_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    showBluetoothAlert();
                } else {
                    Toast.makeText(activity, "Please enable bluetooth permissions to function this app", Toast.LENGTH_SHORT).show();
//                showBluetoothAlert();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT}, REQ_CODE);
//                }


                }
            }
        }
//        if(requestCode==REQUEST_READ_PHONE_STATE)
//        {
//            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                deviceId=getDeviceId();
//            }
//        }
    }
    private List<String> getUngrantedPermissions() {
        List<String> permissions = new ArrayList<>();

        for (String s : permissionsRequired) {
            if (ContextCompat.checkSelfPermission(context, s) != PackageManager.PERMISSION_GRANTED)
                permissions.add(s);
        }
        return permissions;
    }




    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.need_permissions)
                        .setMessage("Please allow the permissions")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
    }
}