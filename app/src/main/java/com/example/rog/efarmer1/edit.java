package com.example.rog.efarmer1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class edit extends AppCompatActivity implements View.OnClickListener{

    ImageView dp;
    ImageButton takePic;
    Button changePass;
    Button logout, login, signUp;
    String result, tag;
    static String email;
    SharedPreferences sharedpreferences;
    homepage hp;
    TextView name, farmAddress, farmName, contactNum, Name, emailAddress, titleBox;
    String name1, farmA1, farmN1, contact1, email1, img1, titleName = "";
    SharedPreferences prefs;
    boolean isLogin;
    final static String UPLOAD_URL = "https://www.veonic.com/aigogo.co/e-Farmer/updateInfo.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        isLogin = prefs.getBoolean("isLogin", false);
        if (isLogin) {
            setContentView(R.layout.activity_edit);
            name = findViewById(R.id.name);
            farmAddress = findViewById(R.id.FarmAddress);
            farmName = findViewById(R.id.farmName);
            contactNum = findViewById(R.id.contactNum);
            Name = findViewById(R.id.emailName);
            dp = findViewById(R.id.pic);
            takePic = findViewById(R.id.imageButton);
            changePass = findViewById(R.id.changePassword);
            logout = findViewById(R.id.logout);
            emailAddress = findViewById(R.id.emailAddress);
            farmName.setOnClickListener(this);
            farmAddress.setOnClickListener(this);
            contactNum.setOnClickListener(this);
            changePass.setOnClickListener(this);
            Name.setOnClickListener(this);

            takePic.setOnClickListener(this);
            logout.setOnClickListener(this);
            logout.setVisibility(View.VISIBLE);
            email = prefs.getString("username","none");
            changePass.setVisibility(View.INVISIBLE);
            takePic.setVisibility(View.INVISIBLE);
            System.out.println("username is: " + email);
            loadProducts();
        }
        else{
            Intent intent = new Intent(edit.this, MainActivity.class);// This intent will be initiated
            startActivity(intent);
        }
    }
    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/profile.php?id=" + email;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                name1 = response1.getString("name");
                                contact1 = response1.getString("contact");
                                email1 = response1.getString("email");
                                farmA1 = response1.getString("farmA");
                                farmN1 = response1.getString("farmN");
                                img1 = response1.getString("img");
                                loadFromSite();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    private void loadFromSite(){
        Glide.with(this)
                .load(img1)
                .transition(withCrossFade())
                .into(dp);

        name.setText(name1);
        farmAddress.setText(farmA1);
        farmName.setText(farmN1);
        contactNum.setText(contact1);
        Name.setText(name1);
        emailAddress.setText(email1);
    }

    @Override
    public void onClick(View v) {
        if (v == logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure to logout?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Do nothing
                    dialog.dismiss();
                }
            });

        }
        if (v == farmAddress){
            if (isLogin) {
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.custombox, null);
                titleName = "New Farm Name";
                titleBox = promptsView.findViewById(R.id.title);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                titleBox.setText(titleName);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.input);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        result = userInput.getText().toString();
                                        tag = "farmAddress";
                                        goLogin();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
            else{
                Intent intent = new Intent(edit.this, MainActivity.class);// This intent will be initiated
                startActivity(intent);
            }
        }
        if (v == farmName){
            if (isLogin) {
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.custombox, null);
                titleName = "New Farm Name";
                titleBox = promptsView.findViewById(R.id.title);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                titleBox.setText(titleName);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.input);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        result = userInput.getText().toString();
                                        tag = "farmName";
                                        goLogin();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
            else{
                Intent intent = new Intent(edit.this, MainActivity.class);// This intent will be initiated
                startActivity(intent);
            }
        }
        if (v == contactNum){
            if (isLogin) {
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.custombox, null);
                titleBox = promptsView.findViewById(R.id.title);
                titleName = "New Contact Number";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                titleBox.setText(titleName);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.input);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        result = userInput.getText().toString();
                                        tag = "contact";
                                        goLogin();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
            else{
                Intent intent = new Intent(edit.this, MainActivity.class);// This intent will be initiated
                startActivity(intent);
            }
        }
        if (v == Name){
            if (isLogin) {
                LayoutInflater li = LayoutInflater.from(this);
                View promptsView = li.inflate(R.layout.custombox, null);
                titleBox = promptsView.findViewById(R.id.title);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);
                titleName = "New Name";
                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                titleBox.setText(titleName);
                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.input);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        result = userInput.getText().toString();
                                        tag = "name";
                                        goLogin();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
            else{
                Intent intent = new Intent(edit.this, MainActivity.class);// This intent will be initiated
                startActivity(intent);
            }
        }
        if (v == changePass){

        }
        if (v == takePic){

        }
        if (v == logout){
            sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            finish();
            Toast.makeText(getApplicationContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(edit.this, homepage.class);// This intent will be initiated
            startActivity(intent);
        }
    }
    private void goLogin() {
        class UploadImage extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(edit.this, "Updating profile", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {//This part involves the php codes
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equalsIgnoreCase("success")) {// IF in php, the data was found AND IF the echo produced is "Correct", then...
                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_LONG).show();
                    loadProducts();
                } else
                    Toast.makeText(getApplicationContext(), "Cannot update", Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();

                data.put("newRes", result);
                data.put("newTag", tag);
                data.put("email" , email);
                String result = rh.sendPostRequest(UPLOAD_URL, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute();
    }
}
