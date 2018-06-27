package com.example.rog.efarmer1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class post_detail extends Activity implements  View.OnClickListener {

    private static final String SERVER_ADDRESS = "https://veonic.com/aigogo.co/e-Farmer/";
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int RESULT_LOAD_IMAGE = 1;

    Button chooseImage;
    Button newPost;
    EditText editPost;
    ImageView ivPhoto;
    ImageView ivPhoto1;
    TextView name;
    Bitmap bitmap;
    Bitmap image;
    Boolean imagepresent = false;
    com.android.volley.RequestQueue requestQueue;
    ProgressDialog progressDialog;

    String post;
    String encodedImage = "";
    static String email,name1,img1;

    SharedPreferences prefs;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        email = prefs.getString("username","none");
        setContentView(R.layout.activity_post_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        startAll();
        requestQueue = Volley.newRequestQueue(post_detail.this);
    }

    public void startAll(){
        ivPhoto1 = findViewById(R.id.profilePic);
        name = findViewById(R.id.username);
        getUserInfo();
        chooseImage = findViewById(R.id.btnImage);
        editPost = findViewById(R.id.etPost);
        ivPhoto = findViewById(R.id.ivPhoto);
        newPost = findViewById(R.id.btnPost);
        chooseImage.setOnClickListener(this);
        newPost.setOnClickListener(this);
        requestQueue = Volley.newRequestQueue(post_detail.this);
        progressDialog = new ProgressDialog(post_detail.this);

        editPost.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(editPost);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }
    public void getUserInfo(){
        String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/loadUser.php?id=" + email;
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
                                img1 = response1.getString("img");
                                setUser();
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
    private void setUser(){
        Glide.with(this)
                .load(img1)
                .transition(withCrossFade())
                .into(ivPhoto1);
        name.setText(name1);
    }
    private boolean checkValidation () {
        boolean ret = true;

        if (!Validation.hasText(editPost)) ret = false;
        return ret;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnImage:
                selectImage();
                break;

            case R.id.btnPost:
                if(imagepresent) {
                    if (checkValidation()) {
                        Bitmap image = ((BitmapDrawable) ivPhoto.getDrawable()).getBitmap();
                        convertImage(image);
                        addPost();
                    }else Toast.makeText(post_detail.this, "Error", Toast.LENGTH_LONG).show();
                }else if(!imagepresent){
                    if (checkValidation()){
                        noImage();
                        ivPhoto.setImageResource(0);
                        //Toast.makeText(Main2Activity.this, "No PIC", Toast.LENGTH_LONG).show();
                        addPost();
                    }else Toast.makeText(post_detail.this, "Error", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    public void selectImage() {

        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(post_detail.this);
        builder.setTitle("Add photos from:");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (items[which].equals("Camera")) {

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    imagepresent = true;

                } else if (items[which].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent.createChooser(intent, "Select file"), RESULT_LOAD_IMAGE);
                    imagepresent = true;

                } else if (items[which].equals("Cancel")) {

                    dialog.dismiss();

                }
            }
        });
        builder.show();
    }

    public void addPost(){

        post = editPost.getText().toString();
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Posting...");
        progressDialog.show();

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_ADDRESS + "addPost.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing Echo Response Message Coming From Server.
                        Toast.makeText(post_detail.this, ServerResponse, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(post_detail.this, "Error in connecting", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The first argument should be same as your MySQL database table columns.
                params.put("postDesc", post);
                params.put("Email", email);
                params.put("postImg", encodedImage);

                return params;
            }

        };
        // Creating RequestQueue.
        requestQueue = Volley.newRequestQueue(post_detail.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                ivPhoto.setVisibility(View.VISIBLE);
                Bundle extras = data.getExtras();
                final Bitmap imageBitmap = (Bitmap) extras.get("data");
                ivPhoto.setImageBitmap(imageBitmap);
                bitmap = imageBitmap;
                return;
            }else if(requestCode == RESULT_LOAD_IMAGE){
                ivPhoto.setVisibility(View.VISIBLE);
                Uri selectedImage = data.getData();
                ivPhoto.setImageURI(selectedImage);
                return;
            }
        }
    }

    private void convertImage(Bitmap image){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

    }

    private void noImage(){
        encodedImage = "empty";
    }

}
