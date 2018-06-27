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

public class discoverAdapter extends RecyclerView.Adapter<discoverAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<adapterListDiscover> productList;

    public discoverAdapter(Context mCtx, List<adapterListDiscover> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public discoverAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.discover_adapter_list, null);
        return new discoverAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull discoverAdapter.HistoryViewHolder holder, int position) {
        adapterListDiscover product = productList.get(position);
        //loading the image
        Glide.with(mCtx)
                .load(product.getImage())
                .transition(withCrossFade())
                .into(holder.imageView);
        Glide.with(mCtx)
                .load(product.getpImg())
                .transition(withCrossFade())
                .into(holder.pDp);

        holder.desc.setText(product.getDesc());
        holder.pName.setText(product.getpName());
        holder.date.setText(product.getDate());
        holder.id.setText(product.getId());
        if (!product.getNoCmt().equals("0")) {
            holder.noOfComment.setVisibility(View.VISIBLE);
            holder.noOfComment.setText(product.getNoCmt() + " Comments");
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView desc;
        ImageView imageView;
        TextView pName;
        ImageView pDp;
        TextView date;
        TextView id;
        TextView noOfComment;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            noOfComment = itemView.findViewById(R.id.noOfComments);
            desc = itemView.findViewById(R.id.desc);
            imageView = itemView.findViewById(R.id.imageView5);
            pName = itemView.findViewById(R.id.name);
            pDp = itemView.findViewById(R.id.dp);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);
            noOfComment.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick(View v) {
            System.out.println("onClick position: " + getAdapterPosition()+ "Id is: " + id.getText().toString());
            String pId = id.getText().toString();
            Intent intent = new Intent(mCtx, comment.class);
            intent.putExtra("id", pId);
            mCtx.startActivity(intent);
        }
    }
}
