package com.example.rog.efarmer1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class product extends AppCompatActivity implements View.OnClickListener{
    TextView name, location, amount, desc, sellerName, price;
    ImageView pp, imgV;
    static String email;
    Button contact, like;
    ImageButton left, right;
    SharedPreferences prefs;
    boolean isLogin;
    String sn = "",id = "", location1= "", amount1= "", contact1= "", desc1= "", name1= "", time1= "", pp1="", currentImg="", priceS="";
    ArrayList<String> img;
    Toolbar mActionBarToolbar;
    int globI = 0;
    int cur = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        amount = findViewById(R.id.amount);
        desc = findViewById(R.id.desc);
        pp = findViewById(R.id.sellerPP);
        left = findViewById(R.id.leftButton);
        right = findViewById(R.id.rightButton);
        sellerName = findViewById(R.id.sellerName);
        like = findViewById(R.id.likes);
        price = findViewById(R.id.price);
        contact = findViewById(R.id.contact);
        imgV = findViewById(R.id.img);
        mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        isLogin = prefs.getBoolean("isLogin", false);
        img = new ArrayList<String>();
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        System.out.println("Id is: " + id);
        if (isLogin) {
            email = prefs.getString("username", "none");
            like.setVisibility(View.VISIBLE);
        }
        else{
            like.setVisibility(View.INVISIBLE);
        }
        contact.setOnClickListener(this);
        like.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        loadProducts();


    }
    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        System.out.println("Enter loadproducts");
        String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/product_detail.php?id=" + id + "&tag=detail";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                name1 = response1.getString("productName");
                                desc1 = response1.getString("desc");
                                location1 = response1.getString("loc");
                                contact1 = response1.getString("cont");
                                amount1 = response1.getString("amt");
                                time1 = response1.getString("time");
                                pp1 = response1.getString("pp");
                                sn = response1.getString("sName");
                                priceS = response1.getString("price");
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
                .load(pp1)
                .transition(withCrossFade())
                .into(pp);
        name.setText(name1);
        amount.setText(amount1);
        location.setText(location1);
        desc.setText(desc1);
        sellerName.setText(sn);
        price.setText(priceS);
        getSupportActionBar().setTitle(name1);
        loadImg();
    }
    private void loadImg(){
        System.out.println("Enter loadproImg");
        String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/product_detail.php?id=" + id + "&tag=d";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                img.add(response1.getString("img"));
                                globI++;
                            }
                            currentImg = img.get(0);
                            System.out.println("current img is: " + currentImg);
                            loadImgProd();
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
    public void loadImgProd(){
        Glide.with(this)
                .load(currentImg)
                .transition(withCrossFade())
                .into(imgV);
    }
    @Override
    public void onClick(View v) {
        if (v == right){
            if (cur<globI) {
                cur++;
                if (cur <globI )
                    currentImg = img.get(cur);
                else {
                    cur--;
                    currentImg = img.get(cur);

                }
            }
            else{
                currentImg = img.get(globI-1);
            }
            loadImgProd();
        }
        if (v == left){
            if (cur == 0){
                currentImg = img.get(cur);
            }
            else {
                cur--;
                currentImg = img.get(cur);
            }
            loadImgProd();
        }
        if (v == contact){
            Log.i("Send SMS", "");
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+contact1));
            System.out.println("contact is : "  + contact1);
            //smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("sms_body"  , "Hello is the " + name1 + " still available for sell?");
            startActivity(smsIntent);
        }
        if (v == like) {
            if (isLogin) {
                String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/like.php?id=" + id + "&email=" + email;
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                Volley.newRequestQueue(this).add(jsonObjectRequest);
            }
            else{
                Toast.makeText(getApplicationContext(), "Please login first", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
