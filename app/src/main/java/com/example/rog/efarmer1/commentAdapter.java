package com.example.rog.efarmer1;

import android.content.Context;
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

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<commentAdapterList> productList;

    public commentAdapter(Context mCtx, List<commentAdapterList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public commentAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.comment, null);
        return new commentAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull commentAdapter.HistoryViewHolder holder, int position) {
        commentAdapterList product = productList.get(position);
        //loading the image
        Glide.with(mCtx)
                .load(product.getpImg())
                .transition(withCrossFade())
                .into(holder.pDp);

        holder.desc.setText(product.getDesc());
        holder.pName.setText(product.getName());
        holder.date.setText(product.getDate());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView desc;
        TextView pName;
        ImageView pDp;
        TextView date;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.cmt);
            pName = itemView.findViewById(R.id.name);
            pDp = itemView.findViewById(R.id.dp);
            date = itemView.findViewById(R.id.date);

        }
    }
}
