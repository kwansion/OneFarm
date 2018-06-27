package com.example.rog.efarmer1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class register extends Activity implements View.OnClickListener{

    private static int RESULT_LOAD_IMAGE = 1;
    private static final String SERVER_ADDRESS = "https://veonic.com/aigogo.co/e-Farmer/";

    public ImageView ivProfilePic;
    public EditText etName;
    public EditText etFarmName;
    public EditText etFarmAddress;
    public EditText etContactNum;
    public EditText etEmail;
    public EditText etPassword;
    public EditText etConfirmPass;
    public Button btnSubmit;

    Bitmap image;
    String name;
    String fname;
    String faddress;
    String contactnum;
    String email;
    String password;
    String encodedImage = "";
    com.android.volley.RequestQueue requestQueue;
    ProgressDialog progressDialog;

    Boolean imagepresent = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        registerViews();
        requestQueue = Volley.newRequestQueue(register.this);
    }

    public void registerViews(){

        ivProfilePic = findViewById(R.id.ivProfilePic);
        etName = findViewById(R.id.etName);
        etFarmName = findViewById(R.id.etFarmName);
        etFarmAddress = findViewById(R.id.etFarmAddress);
        etContactNum = findViewById(R.id.etContactNum);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPass = findViewById(R.id.etConfirmPass);
        btnSubmit = findViewById(R.id.btnSubmit);
        requestQueue = Volley.newRequestQueue(register.this);
        progressDialog = new ProgressDialog(register.this);

        etName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etName);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etFarmName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etFarmName);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etFarmAddress.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etFarmAddress);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etContactNum.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etContactNum);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(etEmail, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etPassword);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        etConfirmPass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etConfirmPass);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        ivProfilePic.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(etName)) ret = false;
        if (!Validation.hasText(etFarmName)) ret = false;
        if (!Validation.hasText(etFarmAddress)) ret = false;
        if (!Validation.hasText(etContactNum)) ret = false;
        if (!Validation.isEmailAddress(etEmail, true)) ret = false;
        if (!Validation.hasText(etPassword)) ret = false;
        if (!Validation.hasText(etConfirmPass)) ret = false;
        return ret;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ivProfilePic:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                imagepresent = true;
                break;

            case R.id.btnSubmit:
                if(imagepresent){
                    if (checkValidation()){
                        String pass1 = etConfirmPass.getText().toString();
                        String pass2 = etPassword.getText().toString();
                        Bitmap image = ((BitmapDrawable) ivProfilePic.getDrawable()).getBitmap();

                        if(!pass1.equals(pass2)) {
                            Toast.makeText(register.this, "Password confirmation not match! Please try again.", Toast.LENGTH_SHORT).show();
                        }else{
                            convertImage(image);
                            UserRegistration();
                            //ivProfilePic.setImageResource(0);
                            //etName.setText("");
                            //etFarmName.setText("");
                            //etFarmAddress.setText("");
                            //etContactNum.setText("");
                            //etEmail.setText("");
                            //etPassword.setText("");
                            //etConfirmPass.setText("");
                        }
                    }}
                else if(!imagepresent){
                    if (checkValidation()){
                        String pass1 = etConfirmPass.getText().toString();
                        String pass2 = etPassword.getText().toString();


                        if(!pass1.equals(pass2)) {
                            Toast.makeText(register.this, "Password confirmation not match! Please try again.", Toast.LENGTH_SHORT).show();
                        }else{

                            noImage(image);
                            UserRegistration();
                            ivProfilePic.setImageResource(0);
                            //etName.setText("");
                            //etFarmName.setText("");
                            //etFarmAddress.setText("");
                            //etContactNum.setText("");
                            //etEmail.setText("");
                            //etPassword.setText("");
                            //etConfirmPass.setText("");

                        }
                    }}
                break;
        }
    }

    public void UserRegistration(){

        name = etName.getText().toString();
        fname = etFarmName.getText().toString();
        faddress =  etFarmAddress.getText().toString();
        contactnum = etContactNum.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();


        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS + "SaveInfo.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(register.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(register.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("image", encodedImage);
                params.put("name", name);
                params.put("fname", fname);
                params.put("faddress", faddress);
                params.put("contactnum", contactnum);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        // Creating RequestQueue.
        requestQueue = Volley.newRequestQueue(register.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null){
            Uri selectedImage = data.getData();
            ivProfilePic.setImageURI(selectedImage);
        }
    }

    private void convertImage(Bitmap image){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

    }

    //If profile picture is absent
    private void noImage(Bitmap image){

        encodedImage = "Default Picture";
    }
}
