package com.example.rog.efarmer1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String UPLOAD_URL = "https://www.veonic.com/aigogo.co/e-Farmer/login.php";
    public static final String UPLOAD_USR = "email";
    public static final String UPLOAD_PWD = "password";
    private EditText email;
    private EditText password;
    private Button login;
    private Button signUp;
    String getUsr, getPwd;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String usernamePref = "username";
    public static final String passwordPref = "password";
    public static final boolean isLogin = true;
    SharedPreferences sharedpreferences;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        email = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        login = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpButton);
        signUp.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    private void goLogin() {
        class UploadImage extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Signing In", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {//This part involves the php codes
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equalsIgnoreCase("Correct")) {// IF in php, the data was found AND IF the echo produced is "Correct", then...
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString(usernamePref, getUsr);
                    editor.putString(passwordPref, getPwd);
                    editor.putBoolean("isLogin", true);
                    editor.commit();
                    System.out.println("Save to preferences");
                    finish();//The operation from php will be ended (finish()) and..
                    Intent intent = new Intent(MainActivity.this, homepage.class);// This intent will be initiated
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();

                data.put(UPLOAD_USR, getUsr);
                data.put(UPLOAD_PWD, getPwd);
                String result = rh.sendPostRequest(UPLOAD_URL, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute();
    }
    @Override
    public void onClick(View view) {
        if (view == login) {
            int gotUsername = 0;
            int gotPassword = 0;
            if (view == login) {
                getPwd = password.getText().toString();
                getUsr = email.getText().toString();
                if (getUsr.length() > 0) {
                    gotUsername = 1;

                }
                if (getPwd.length() > 0) {
                    gotPassword = 1;
                }

                /* Proceed to login */
                if (gotUsername == 1 && gotPassword == 1) {
                    goLogin();
                }

                /* print error message */
                else if (gotUsername == 0 && gotPassword == 1) {
                    Toast.makeText(getApplicationContext(), "Username cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (gotUsername == 1 && gotPassword == 0) {
                    Toast.makeText(getApplicationContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (gotUsername == 0 && gotPassword == 0) {
                    Toast.makeText(getApplicationContext(), "Username and Password cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (view == signUp) {
            Intent intent = new Intent(MainActivity.this, register.class);// This intent will be initiated
            startActivity(intent);
        }
    }
}

