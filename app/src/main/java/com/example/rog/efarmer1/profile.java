package com.example.rog.efarmer1;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class profile extends Fragment {
    private ViewPager viewPager;
    private SectionPageAdapter mSectionPageAdapter;
    private Toolbar toolbar;
    private TextView name;
    static String email;
    String name1, img1;
    SharedPreferences prefs;
    public boolean isLogin;
    public static profile newInstance(){
        profile fragment = new profile();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rv = inflater.inflate(R.layout.activity_profile, container, false);
        name = rv.findViewById(R.id.textView);
        mSectionPageAdapter = new SectionPageAdapter(getChildFragmentManager());

        viewPager = rv.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = rv.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        System.out.println("profile");
        loadUser();
        return rv;
    }

    private void loadUser() {

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
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }
    private void loadFromSite(){
        name.setText(name1);
    }

    private void setupViewPager(ViewPager vp){
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
        adapter.addFragment(new buying(), "Buying");
        adapter.addFragment(new selling(), "Selling");
        vp.setAdapter(adapter);
    }
}
