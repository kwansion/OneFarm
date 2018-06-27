package com.example.rog.efarmer1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Fruit extends AppCompatActivity {

    GraphView graph;
    String name;
    ImageButton a1,a2,a3,a4,a5;
    SimpleDateFormat dateParser;
    RecyclerView recyclerView;
    List<graphAdapterList> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.fruit);
        }catch (Exception e){
            Log.e("Fruit", "onCreateView", e);
            throw e;
        }
        dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        a4 = findViewById(R.id.a4);
        a5 = findViewById(R.id.a5);
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        getGraphValue("Apple");
    }
    public void apple (View v) {
        getGraphValue("Apple");

    }
    public void banana (View v) {
        getGraphValue("Banana");

    }
    public void watermelon (View v) {
        getGraphValue("Watermelon");
    }
    public void lemon (View v) {
        getGraphValue("Lemon");
    }
    public void grapes (View v) {
            getGraphValue("Grapes");
    }

    public void getGraphValue(String tag){
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        String URL_PRODUCTS = "http://veonic.com/aigogo.co/e-Farmer/graph/Fruit.php?id=" + tag;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            productList.clear();
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                productList.add(new graphAdapterList(
                                product.getString("price"),
                                product.getString("price2"),
                                product.getString("name"),
                                product.getString("d1"),
                                product.getString("d2"),
                                product.getString("d3"),
                                product.getString("d4"),
                                product.getString("p1"),
                                product.getString("p2"),
                                product.getString("p3"),
                                product.getString("p4")
                                ));
                            }
                            graphAdapter adapter = new graphAdapter(Fruit.this, productList);
                            recyclerView.setAdapter(adapter);
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
        Volley.newRequestQueue(Fruit.this).add(jsonObjectRequest);
    }
}
