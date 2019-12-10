package com.fycedwin.oceanofootball.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fycedwin.oceanofootball.R;
import com.fycedwin.oceanofootball.app.AppConfig;
import com.fycedwin.oceanofootball.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity{
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText inputName,inputPhone,inputEmail,inputAddress,inputPassword,inputConfirm;
    private CheckBox agreeButton;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        Button btnRegister = (Button) findViewById(R.id.signUpButton);
        inputName = (EditText) findViewById(R.id.nameInput);
        inputPhone = (EditText) findViewById(R.id.inputPhoneNumber);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputAddress = (EditText) findViewById(R.id.inputAddress);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputConfirm = (EditText) findViewById(R.id.inputConfirmPassword);
        agreeButton= (CheckBox)findViewById(R.id.agreeBox);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (agreeButton.isChecked()) {
                    String name = inputName.getText().toString().trim();
                    String email = inputEmail.getText().toString().trim();
                    String password = inputPassword.getText().toString().trim();
                     String phone = inputPhone.getText().toString().trim();
                    String address = inputAddress.getText().toString().trim();

                    if (!Validator()) {
                        //registrasion on
                        try {
                            registerUser(name,phone ,email,address, password);
                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, "Error" + e, Toast.LENGTH_LONG).show();
                        }
                    }

                }
                else {

                    Snackbar snackbar = Snackbar.make(view, "Must to agree", Snackbar.LENGTH_LONG)
                            .setAction("Action", null);
                    View sbView = snackbar.getView();

                    sbView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bg_login));
                    snackbar.show();
                }
            }
        });


    }



    private void registerUser(final String name, final String phone,final String email,
                              final String address,final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        progressDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST, AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Bundle dataBundel= new Bundle();
                        dataBundel.putString("Email",email);
                        dataBundel.putString("Password",password);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtras(dataBundel);
                        startActivity(intent);
                        finish();
                    }

                    else {


                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                hideDialog();
                DialogError();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("phone",phone);
                params.put("email", email);
                params.put("address",address);
                params.put("password", password);

                return params;
            }

        };


        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    public boolean Validator() {


        inputName.setError(null);
        inputPhone.setError(null);
        inputEmail.setError(null);
        inputAddress.setError(null);
        inputPassword.setError(null);
        inputConfirm.setError(null);

        String nameValidator = inputName.getText().toString();
        String phoneValidator = inputPhone.getText().toString();
        String emailValidator = inputEmail.getText().toString();
        String addressValidator = inputAddress.getText().toString();
        String passwordValidator = inputPassword.getText().toString();
        String confirmValidator = inputConfirm.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (confirmValidator.isEmpty()) {
            inputConfirm.setError("Confirm Password must be filed ");
            focusView = inputConfirm;
            cancel = true;
        } else if (!confirmValidator.equals(passwordValidator)) {
            inputConfirm.setError("Confirm and Password not same ");
            focusView = inputConfirm;
            cancel = true;
        }

        if (passwordValidator.isEmpty()) {
            inputPassword.setError("Confirm Password must be filed ");
            focusView = inputPassword;
            cancel = true;
        } else if (passwordValidator.length() <= 4) {
            inputPassword.setError("This password is too short");
            focusView = inputPassword;
            cancel = true;
        }

        if (addressValidator.isEmpty()) {
            inputAddress.setError("Address must be filed");
            focusView = inputAddress;
            cancel = true;
        }

        if (emailValidator.isEmpty()) {
            inputEmail.setError("Email must be filed");
            focusView = inputEmail;
            cancel = true;
        } else if (!isEmailValid(emailValidator)) {
            inputEmail.setError("This email address is invalid");
            focusView = inputEmail;
            cancel = true;
        }

        if (phoneValidator.isEmpty()) {
            inputPhone.setError("Phone must be filed");
            focusView = inputPhone;
            cancel = true;
        } else if (phoneValidator.length() < 10) {
            inputPhone.setError("Number can not be less than 11");
            focusView = inputPhone;
            cancel = true;
        } else if (phoneValidator.length() > 13) {
            inputPhone.setError("Number can not be more than 13 digits");
            focusView = inputPhone;
            cancel = true;

        }


        if (nameValidator.isEmpty()) {
            inputName.setError("Name must be Filed");
            focusView = inputName;
            cancel = true;
        }


        if (cancel) {focusView.requestFocus();}
        return cancel;
    }

    private boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void DialogError(){
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage("Cannot Connecting . Try again later").setTitle("Warring");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
    }
}
