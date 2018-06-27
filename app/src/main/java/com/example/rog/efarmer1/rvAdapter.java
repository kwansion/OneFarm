package com.example.rog.efarmer1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by ROG on 4/4/2018.
 */

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<adapterList> productList;

    public rvAdapter(Context mCtx, List<adapterList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public HistoryViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapter_list, null);
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull  HistoryViewHolder holder, int position) {
        adapterList product = productList.get(position);
        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .transition(withCrossFade())
                .into(holder.imageView);

        holder.desc.setText(product.getDesc());
        holder.eta.setText(product.getEta());
        holder.location.setText(product.getLocation());
        holder.id.setText(product.getId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView desc;
        ImageView imageView;
        TextView eta;
        TextView location;
        TextView id;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            desc = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.imageView);
            eta = itemView.findViewById(R.id.type);
            location = itemView.findViewById(R.id.location);
            id = itemView.findViewById(R.id.id);
        }

        @Override
        public void onClick(View v) {
            System.out.println("onClick position: " + getAdapterPosition()+ "Id is: " + id.getText().toString());
            String pId = id.getText().toString();
            Intent intent = new Intent(mCtx, product.class);
            intent.putExtra("id", pId);
            mCtx.startActivity(intent);
        }
    }
}
