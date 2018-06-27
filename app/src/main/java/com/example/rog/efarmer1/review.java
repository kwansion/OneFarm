package com.example.rog.efarmer1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class review extends AppCompatActivity {

    static String id;

    //a list to store all the products
    List<adapterList> productList;

    //the recyclerview
    RecyclerView recyclerView;
    SharedPreferences prefs;
    public boolean isLogin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recylcerView);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        isLogin = prefs.getBoolean("isLogin", false);
        if (isLogin) {
            id = prefs.getString("username", "none");
            isLogin = prefs.getBoolean("isLogin", false);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(review.this));

            //initializing the productlist
            productList = new ArrayList<>();
            System.out.println("At onCreate like");
            //this method will fetch and parse json
            //to display it in recyclerview
            loadProducts();
        }
        else{
            Intent intent = new Intent(review.this, MainActivity.class);// This intent will be initiated
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
        String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/myReview.php?id=" + id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new adapterList(
                                        product.getString("name"),
                                        product.getString("img"),
                                        product.getString("amt"),
                                        product.getString("location"),
                                        product.getInt("id")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            rvAdapter adapter = new rvAdapter(review.this, productList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
}
