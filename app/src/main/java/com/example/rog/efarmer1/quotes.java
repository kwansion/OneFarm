package com.example.rog.efarmer1;

import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class quotes extends Fragment {
    private ViewPager viewPager;
    private SectionPageAdapter mSectionPageAdapter;
    static String email;
    String name1, img1;
    SharedPreferences prefs;
    public static quotes newInstance(){
        quotes fragment = new quotes();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rv = inflater.inflate(R.layout.activity_quotes, container, false);
        mSectionPageAdapter = new SectionPageAdapter(getChildFragmentManager());

        viewPager = rv.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = rv.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return rv;
    }

    private void setupViewPager(ViewPager vp){
        SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
        adapter.addFragment(new newsfeed(), "Newsfeed");
        adapter.addFragment(new statistic(), "Statistic");
        vp.setAdapter(adapter);
    }
}
