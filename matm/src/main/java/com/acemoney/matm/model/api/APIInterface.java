package com.acemoney.matm.model.api;



import com.acemoney.matm.transferData.TransferDataMain;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
public interface APIInterface {

    @FormUrlEncoded
    @POST("apiService/getdetailsSDK")
    Call<TransferDataMain> transferData(@FieldMap HashMap<String, String> params);


}
