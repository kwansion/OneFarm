package com.example.rog.efarmer1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class page extends Fragment {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13;
    ImageView iv1, iv2,iv3,iv4,iv5,iv6,iv7,iv8;
    public static android.support.v4.app.Fragment newInstance(){
        page fragment = new page();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rv = inflater.inflate(R.layout.activity_page, container, false);
        t1 = rv.findViewById(R.id.q1);
        t2 = rv.findViewById(R.id.q2);
        t3 = rv.findViewById(R.id.q3);
        t4 = rv.findViewById(R.id.q4);
        t5 = rv.findViewById(R.id.name1);
        t6 = rv.findViewById(R.id.name2);
        t7 = rv.findViewById(R.id.name3);
        t8 = rv.findViewById(R.id.name4);
        t9 = rv.findViewById(R.id.vege);
        t10 = rv.findViewById(R.id.fruit);
        t11= rv.findViewById(R.id.animal);
        t13 = rv.findViewById(R.id.mp);

        iv1 = rv.findViewById(R.id.v1);
        iv2 = rv.findViewById(R.id.v2);
        iv3 = rv.findViewById(R.id.v3);
        iv5 = rv.findViewById(R.id.imageView7);
        iv6 = rv.findViewById(R.id.imageView8);
        iv7 = rv.findViewById(R.id.imageView9);
        iv8 = rv.findViewById(R.id.imageView10);

        iv1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tag1.class);
                startActivity(intent);
            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tag2.class);
                startActivity(intent);
            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), tag3.class);
                startActivity(intent);
            }
        });
        return rv;
    }
}
