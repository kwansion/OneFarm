package com.example.rog.efarmer1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class selling extends Fragment implements View.OnClickListener {
    Button sell, review;
    public boolean isLogin;
    SharedPreferences prefs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.activity_selling, container, false);
        sell = rv.findViewById(R.id.addProduct);
        review = rv.findViewById(R.id.reviewProduct);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        isLogin = prefs.getBoolean("isLogin", false);
        sell.setOnClickListener(this);
        review.setOnClickListener(this);
        return rv;
    }

    @Override
    public void onClick(View v) {
        if (v == sell){
            if (isLogin) {
                Intent intent = new Intent(getContext(), new_product.class);// This intent will be initiated
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(getContext(), MainActivity.class);// This intent will be initiated
                startActivity(intent);
            }
        }
        if (v == review){
            Intent intent = new Intent(getContext(), review.class);// This intent will be initiated
            startActivity(intent);
        }
    }
}
