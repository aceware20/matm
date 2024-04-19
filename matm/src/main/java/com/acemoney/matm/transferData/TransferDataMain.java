
package com.acemoney.matm.transferData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransferDataMain {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public TransferDataMain() {
    }

    /**
     * 
     * @param data
     * @param success
     */
    public TransferDataMain(Boolean success, Data data) {
        super();
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
