package com.example.rog.efarmer1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class comment extends AppCompatActivity implements View.OnClickListener {
    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not wor
    com.android.volley.RequestQueue requestQueue;
    //a list to store all the products
    List<commentAdapterList> productList;
    ImageView commenterDp;
    TextView comment;
    ImageButton send;
    EditText postCmt;
    //the recyclerview
    SharedPreferences prefs;
    String id;
    String email;
    String cmt;
    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //getting the recyclerview from xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commentrv);
        postCmt = findViewById(R.id.postCmt);
        send = findViewById(R.id.send);
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productList = new ArrayList<>();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");
        requestQueue = Volley.newRequestQueue(comment.this);
        send.setOnClickListener(this);
        //this method will fetch and parse json
        //to display it in recyclerview
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
        String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/displayComment.php?id=" + id;
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
                                productList.add(new commentAdapterList(
                                        product.getString("pDate"),
                                        product.getString("pImg"),
                                        product.getString("name"),
                                        product.getString("cmt")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            commentAdapter adapter = new commentAdapter(comment.this, productList);
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

    @Override
    public void onClick(View v) {
        if(v==send){
            email = prefs.getString("username","none");
            postComment();
        }
    }
    public void postComment(){
        cmt = postCmt.getText().toString();
        String UPLOAD_URL = "https://www.veonic.com/aigogo.co/e-Farmer/sendComment.php";

        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {
                        if (ServerResponse.equals("success")){
                            postCmt.setText("");
                            productList.clear();
                            loadProducts();
                        }
                        else
                            Toast.makeText(comment.this, "No internet connection", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Showing error message if something goes wrong.
                        Toast.makeText(comment.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                // The firs argument should be same sa your MySQL database table columns.
                params.put("cmt", cmt);
                params.put("email", email);
                params.put("id", id);
                return params;
            }
        };
        // Creating RequestQueue.
        requestQueue = Volley.newRequestQueue(comment.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}
