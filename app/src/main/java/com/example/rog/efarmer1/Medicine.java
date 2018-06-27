package com.example.rog.efarmer1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Medicine extends AppCompatActivity {

    GraphView graph;
    String d1s,d2s,d3s,d4s,dd1s,dd2s,dd3s,dd4s;
    Date d1, d2, d3, d4;
    float dd1, dd2, dd3, dd4;
    String name;
    RecyclerView recyclerView;
    List<graphAdapterList> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine);

        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        getGraphValue("Ginseng");
    }
    public void basil (View v) {

        getGraphValue("Basil");
    }
    public void mata_kucing (View v) {
       getGraphValue("Mata Kucing");
    }
    public void tea (View v) {
      getGraphValue("Tea");
    }
    public void ginseng (View v) {
        getGraphValue("Ginseng");
    }
    public void ulam_raja (View v) {
       getGraphValue("Ulam Raja");
    }

    public void getGraphValue(String tag){
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        String URL_PRODUCTS = "http://veonic.com/aigogo.co/e-Farmer/graph/Medicine.php?id=" + tag;
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
                            graphAdapter adapter = new graphAdapter(Medicine.this, productList);
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
        Volley.newRequestQueue(Medicine.this).add(jsonObjectRequest);
    }
}
