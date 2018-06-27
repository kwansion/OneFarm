package com.example.rog.efarmer1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class statistic extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    List<newsAdapterList> productList;
    Button fruit, vege, com, medicine, seafood, livestock;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.activity_statistic, container, false);
        fruit = rv.findViewById(R.id.fruit);
        vege = rv.findViewById(R.id.vegetable);
        com = rv.findViewById(R.id.commodity);
        medicine = rv.findViewById(R.id.medicine);
        seafood = rv.findViewById(R.id.seafood);
        livestock = rv.findViewById(R.id.livestock);

        fruit.setOnClickListener(this);
        vege.setOnClickListener(this);
        com.setOnClickListener(this);
        medicine.setOnClickListener(this);
        seafood.setOnClickListener(this);
        livestock.setOnClickListener(this);
        return rv;
    }

    @Override
    public void onClick(View v) {
        if (v == fruit){
            Intent i = new Intent(getActivity(), Fruit.class);
            startActivity(i);
        }
        else if (v == vege){
            Intent i = new Intent(getActivity(), Vegetable.class);
            startActivity(i);
        }
        else if (v== com){
            Intent i = new Intent(getActivity(), Commodity.class);
            startActivity(i);
        }
        else if (v ==medicine){
            Intent i = new Intent(getActivity(), Medicine.class);
            startActivity(i);
        }
        else if (v == seafood){
            Intent i = new Intent(getActivity(), Seafood.class);
            startActivity(i);
        }
        else if (v == livestock){
            Intent i = new Intent(getActivity(), Livestock.class);
            startActivity(i);
        }
    }
}
