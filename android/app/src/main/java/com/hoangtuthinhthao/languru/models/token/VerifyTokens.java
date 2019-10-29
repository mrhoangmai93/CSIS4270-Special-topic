
package com.hoangtuthinhthao.languru.models.token;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyTokens {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("reset_password")
    @Expose
    private String resetPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(String resetPassword) {
        this.resetPassword = resetPassword;
    }

}
