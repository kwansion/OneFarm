package com.example.rog.efarmer1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class buying extends Fragment implements View.OnClickListener{
    Button like, account, post;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rv = inflater.inflate(R.layout.activity_buying, container, false);
        like = rv.findViewById(R.id.likes);
        account = rv.findViewById(R.id.account);
        post = rv.findViewById(R.id.post);
        post.setOnClickListener(this);
        like.setOnClickListener(this);
        account.setOnClickListener(this);
        return rv;
    }

    @Override
    public void onClick(View v) {
        if (v == like){
            Intent intent = new Intent(getContext(), like.class);// This intent will be initiated
            startActivity(intent);
        }
        if (v == account){
            Intent intent = new Intent (getContext(), edit.class);
            startActivity(intent);
        }
        if (v==post){
            Intent intent = new Intent (getContext(), post_detail.class);
            startActivity(intent);
        }
    }
}
