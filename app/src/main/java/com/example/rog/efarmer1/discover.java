package com.example.rog.efarmer1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

public class discover extends Fragment {
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/discover.php";

    //a list to store all the products
    List<adapterListDiscover> productList;
    ImageView commenterDp;
    TextView comment;
    ImageButton cmt;
    public static discover newInstance(){
        discover fragment = new discover();
        return fragment;
    }
    //the recyclerview
    RecyclerView recyclerView;
    discoverAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.activity_discover, container, false);
        //getting the recyclerview from xml
        recyclerView = rv.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        System.out.println("discover");
        loadProducts();
        return rv;
    }
    @Override
    public void onStop(){
        super.onStop();
        productList.clear();
    }
    @Override
    public void onResume(){
        super.onResume();
        System.out.println("discover onResume");
        productList.clear();
        loadProducts();
    }
    private void loadProducts() {
        System.out.println("discover loadproduct");
        productList.clear();
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            productList.clear();
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new adapterListDiscover(
                                        product.getString("pDate"),
                                        product.getString("img"),
                                        product.getString("pImg"),
                                        product.getString("name"),
                                        product.getString("desc"),
                                        product.getInt("id"),
                                        product.getInt("noOfComment")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            adapter = new discoverAdapter(getContext(), productList);
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
        Volley.newRequestQueue(getContext()).add(stringRequest);
    }
}
