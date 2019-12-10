package com.fycedwin.oceanofootball.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
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
import com.fycedwin.oceanofootball.helper.DatabaseManager;
import com.fycedwin.oceanofootball.helper.SessionManager;
import com.google.firebase.database.DatabaseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private AppCompatAutoCompleteTextView inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private DatabaseManager db;
    private static SharedPreferences pref;
    private CheckBox remember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = (AppCompatAutoCompleteTextView) findViewById(R.id.editEmail);
        inputPassword=(EditText) findViewById(R.id.editPassword);
        Button btnLogin = (Button) findViewById(R.id.singInButton);
        remember= (CheckBox)findViewById(R.id.rememberMe);
        pref = getSharedPreferences(getString(R.string.key_pref), Context.MODE_PRIVATE);

        Bundle extras=getIntent().getExtras();

            if(extras!=null){

                String emaillol = extras.getString("Email");
                String passwordlol = extras.getString("Password");
                inputEmail.setText(emaillol);
                inputPassword.setText(passwordlol);
            }

            else {

                load();
            }

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        db = new DatabaseManager(getApplicationContext());
        session = new SessionManager(getApplicationContext());


        if (session.isLoggedIn()) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                if(!Validator()) {
                    //nanti pake fungsi di bawah
                checkLogin(email, password);

                }

            }

        });


    }


    private void checkLogin(final String email, final String password) {

        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");


                    if (!error) {

                        session.setLogin(true);

                            JSONObject user = jObj.getJSONObject("user");
                            String name = user.getString("name");
                            String phone = user.getString("phone");
                            String email = user.getString("email");
                            String address = user.getString("address");
                            try{
                               db.insert(name,phone,email,address);
                                HashMap<String, String> datamamen = db.getUserDetails();

                                String name_database = datamamen.get("name");
                                String email_database = datamamen.get("email");
                                String phone_datebase =datamamen.get("phone");
                                String address_database=datamamen.get("address");
                                Log.d(TAG, "name: " + name_database);
                                Log.d(TAG, "phone: " + phone_datebase);
                                Log.d(TAG, "email: " + email_database);
                                Log.d(TAG, "address: " + address_database);
                            }
                           catch (DatabaseException e){
                               Log.e(TAG, "Error Data base : " + e);
                           }

                            // Launch main activity


                        if (remember.isChecked()){
                            save();
                        }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                    }

                    else {
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());

                hideDialog();
                DialogError();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    public boolean Validator() {


        inputEmail.setError(null);
        inputPassword.setError(null);
        String emailValidator = inputEmail.getText().toString();
        String passwordValidator = inputPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (passwordValidator.isEmpty()) {
            inputPassword.setError("Confirm Password must be filed ");
            focusView = inputPassword;
            cancel = true;
        }
        if (emailValidator.isEmpty()) {
            inputEmail.setError("Email must be filed");
            focusView =inputEmail;
            cancel = true;
        } else if (!isEmailValid(emailValidator)) {
            inputEmail.setError("This email address is invalid");
            focusView = inputEmail;
            cancel = true;
        }
        if (cancel) {focusView.requestFocus();}
        return cancel;
    }

    public void signup(View view){
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

    public void forgetPassword(View v){
        startActivity(new Intent(getApplicationContext(),ForgetActivity.class));
    }

    private boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public void save (){
        String email = inputEmail.getText().toString();
        String password =inputPassword.getText().toString();
        try {
            SharedPreferences.Editor editor = pref.edit();

            editor.putString(getString(R.string.key_email),email);
            editor.putString(getString(R.string.key_password),password);
            editor.apply();
            editor.commit();
        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(), "SharedPreferences", Toast.LENGTH_SHORT).show();
        }

    }

    public void load(){
        try {

            String email = pref.getString(getString(R.string.key_email), "");
            String password = pref.getString(getString(R.string.key_password), "");
            inputEmail.setText(email);
            inputPassword.setText(password);

        }
        catch (Exception e){
            inputEmail.setText("");
            inputPassword.setText("");

            Toast.makeText(getApplicationContext(), "SharedPreferences Error ", Toast.LENGTH_SHORT).show();
        }

    }

    private void DialogError(){

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
