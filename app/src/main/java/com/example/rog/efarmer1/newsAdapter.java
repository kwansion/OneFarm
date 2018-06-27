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

import com.bumptech.glide.Glide;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class newsAdapter extends RecyclerView.Adapter<newsAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<newsAdapterList> productList;

    public newsAdapter(Context mCtx, List<newsAdapterList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public newsAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.news_adapter_list, null);
        return new newsAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull newsAdapter.HistoryViewHolder holder, int position) {
        newsAdapterList product = productList.get(position);
        //loading the image
        Glide.with(mCtx)
                .load(product.getpImg())
                .transition(withCrossFade())
                .into(holder.pDp);

        holder.desc.setText(product.getDesc());
        holder.siteUrl.setText(product.getUrl());
        holder.name.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView desc;
        TextView siteUrl;
        ImageView pDp;
        TextView name;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            desc = itemView.findViewById(R.id.desc);
            siteUrl = itemView.findViewById(R.id.id);
            pDp = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
        }
        @Override
        public void onClick(View v) {
            System.out.println("inside newsAdapter");
            String pId = siteUrl.getText().toString();
            Intent intent = new Intent(mCtx, news.class);
            intent.putExtra("id", pId);
            mCtx.startActivity(intent);
        }
    }
}
