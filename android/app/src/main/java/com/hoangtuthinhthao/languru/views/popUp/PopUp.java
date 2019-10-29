package com.hoangtuthinhthao.languru.views.popUp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hoangtuthinhthao.languru.R;
import com.hoangtuthinhthao.languru.controllers.api.ApiService;
import com.hoangtuthinhthao.languru.controllers.authentication.AuthHelpers;
import com.hoangtuthinhthao.languru.models.User;

public class PopUp {
    /**
     * This function open a popup for user to register a new account
     *  @param dialog
     * @param context
     * @param progressDialog
     * @param apiAuthInterface
     */
    public static void openRegisterPopup(final Dialog dialog, final Context context, final ProgressDialog progressDialog, final ApiService apiAuthInterface) {

        dialog.setContentView(R.layout.popup_register);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView closeBtn = dialog.findViewById(R.id.btnClose);
        final EditText email = dialog.findViewById(R.id.txtEmail);
        final EditText firstName = dialog.findViewById(R.id.txtFirstName);
        final EditText lastName = dialog.findViewById(R.id.txtLastName);
        final EditText txtpassword = dialog.findViewById(R.id.txtPassword);
        final EditText confirmPassword = dialog.findViewById(R.id.txtConfirmPassword);
        final Button btnSignUp = dialog.findViewById(R.id.btnRegister);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = firstName.getText().toString().trim();
                String lName = lastName.getText().toString().trim();
                String emailString = email.getText().toString().trim();
                String pw = txtpassword.getText().toString().trim();
                String pw1 = confirmPassword.getText().toString().trim();
                if (TextUtils.isEmpty(emailString)) {
                    Toast.makeText(context, "Please fill up email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fName)) {
                    Toast.makeText(context, "Please fill up first name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(lName)) {
                    Toast.makeText(context, "Please fill up last name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pw)) {
                    Toast.makeText(context, "Please fill up password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(pw1)) {
                    Toast.makeText(context, "Please fill up confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pw.equals(pw1)) {
                    Toast.makeText(context, "Password doesn't match. Try Again", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Register...");
                progressDialog.show();
                User user = new User(fName, lName, emailString , pw);
                //Register new user
                AuthHelpers.signupUser(context,apiAuthInterface, user);

            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
