package com.example.rog.efarmer1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.os.Bundle;
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
import com.bumptech.glide.Glide;
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
import java.util.Timer;

import java.util.TimerTask;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class category extends Fragment {
    ImageButton vege, fruit, live, biomed, seafood, commodity;
    ImageView new1,new2,new3,new4;
    TextView t1,t2,t3,t4;
    ViewPager viewPager;
    ViewPageAdapter adapter;
    GraphView graph;
    String d1s,d2s,d3s,d4s,dd1s,dd2s,dd3s,dd4s;
    String[] pname;
    String[] pimg;
    Date d1, d2, d3, d4;
    List<newsAdapterList> productList;
    float dd1, dd2, dd3, dd4;
    String name;
    SimpleDateFormat dateParser;
    RecyclerView recyclerView;
    private String[] images = {
            "https://veonic.com/aigogo.co/e-Farmer/banner/banner1.jpg",
            "https://veonic.com/aigogo.co/e-Farmer/banner/banner2.jpg",
            "https://veonic.com/aigogo.co/e-Farmer/banner/banner3.jpg",
            "https://veonic.com/aigogo.co/e-Farmer/banner/banner4.jpg",
            "https://veonic.com/aigogo.co/e-Farmer/banner/banner5.jpg"
    };
    public static category newInstance(){
        category fragment = new category();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rv = inflater.inflate(R.layout.activity_category, container, false);
        vege = rv.findViewById(R.id.catButton1);
        fruit = rv.findViewById(R.id.catButton2);
        live = rv.findViewById(R.id.catButton3);
        biomed = rv.findViewById(R.id.catButton4);
        seafood = rv.findViewById(R.id.catButton5);
        commodity = rv.findViewById(R.id.catButton6);
        new1 = rv.findViewById(R.id.new1);
        new2 = rv.findViewById(R.id.new2);
        new3 = rv.findViewById(R.id.new3);
        new4 = rv.findViewById(R.id.new4);
        t1 = rv.findViewById(R.id.tnew1);
        t2 = rv.findViewById(R.id.tnew2);
        t3 = rv.findViewById(R.id.tnew3);
        t4= rv.findViewById(R.id.tnew4);
        recyclerView = rv.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        productList = new ArrayList<>();
        pname = new String[4];
        pimg = new String[4];
        graph = rv.findViewById(R.id.graph);
        dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        viewPager = rv.findViewById(R.id.viewPager);
        adapter = new ViewPageAdapter(getActivity(),images);
        viewPager.setAdapter(adapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        vege.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tag1.class);
                startActivity(intent);
            }
        });
        fruit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tag2.class);
                startActivity(intent);
            }
        });
        live.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tag3.class);
                startActivity(intent);
            }
        });
        biomed.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tag4.class);
                startActivity(intent);
            }
        });
        seafood.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tag5.class);
                startActivity(intent);
            }
        });
        commodity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tag6.class);
                startActivity(intent);
            }
        });

            getWhatNew();
            getGraphValue();
            loadNews();

        //MyTask mt = new MyTask();
        //mt.execute();

        return rv;
    }
    /*
    class MyTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog Asycdialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            Asycdialog.setMessage("Loading...");
            Asycdialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            getWhatNew();
            getGraphValue();
            loadNews();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            Asycdialog.dismiss();
        }
    }*/
    private void loadNews() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/displayNewsHomepage.php";
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
                                productList.add(new newsAdapterList(
                                        product.getString("name"),
                                        product.getString("pImg"),
                                        product.getString("siteUrl"),
                                        product.getString("desc")
                                ));

                            }

                            //creating adapter object and setting it to recyclerview
                            newsAdapter adapter = new newsAdapter(getContext(), productList);
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
    public void getWhatNew(){
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/whatNew.php";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                pname[i] = response1.getString("pname");
                                pimg[i] = response1.getString("thumb");
                                System.out.println("Name: " + pname[i]);
                            }
                            setWhatNew();
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
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }
    public void setWhatNew(){
            Glide.with(this)
                    .load(pimg[0])
                    .transition(withCrossFade())
                    .into(new1);
            t1.setText(pname[0]);
        Glide.with(this)
                .load(pimg[1])
                .transition(withCrossFade())
                .into(new2);

        t2.setText(pname[1]);
        Glide.with(this)
                .load(pimg[2])
                .transition(withCrossFade())
                .into(new3);

        t3.setText(pname[2]);
        Glide.with(this)
                .load(pimg[3])
                .transition(withCrossFade())
                .into(new4);

        t4.setText(pname[3]);
    }
    public void onResume() {
        super.onResume();
        getGraphValue();
    }
    public void getGraphValue(){
        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        String URL_PRODUCTS = "https://www.veonic.com/aigogo.co/e-Farmer/graphHomepage.php";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject response1 = array.getJSONObject(i);
                                name = response1.getString("name");
                                try {
                                    d1 = dateParser.parse(response1.getString("d1"));
                                    d2 = dateParser.parse(response1.getString("d2"));
                                    d3 = dateParser.parse(response1.getString("d3"));
                                    d4 = dateParser.parse(response1.getString("d4"));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                dd1s = response1.getString("p1");
                                dd2s = response1.getString("p2");
                                dd3s= response1.getString("p3");
                                dd4s= response1.getString("p4");

                                setGraph();
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
        Volley.newRequestQueue(getContext()).add(jsonObjectRequest);
    }
    public void setGraph(){
            dd1 = Float.valueOf(dd1s);
            dd2 = Float.valueOf(dd2s);
            dd3 = Float.valueOf(dd3s);
            dd4 = Float.valueOf(dd4s);
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Week");
        gridLabel.setVerticalAxisTitle("Price");
        graph.setTitle("Price of " + name);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, dd1),
                new DataPoint(d2, dd2),
                new DataPoint(d3, dd3),
                new DataPoint(d4, dd4)
        });

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d4.getTime());
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.addSeries(series);
    }
    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            if(getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Running thread");
                    if(viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    }else if(viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else if(viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    }else if(viewPager.getCurrentItem() == 3){
                        viewPager.setCurrentItem(4);
                    }else viewPager.setCurrentItem(0);
                }
            });
        }
    }
}
