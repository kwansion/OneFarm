package com.example.rog.efarmer1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class graphAdapter extends RecyclerView.Adapter<graphAdapter.HistoryViewHolder> {
    private Context mCtx;
    private List<graphAdapterList> productList;
    ImageView up,down;
    GraphView graph;
    SimpleDateFormat dateParser;
    public graphAdapter(Context mCtx, List<graphAdapterList> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    @NonNull
    public graphAdapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.statistic_adapter, null);
        dateParser = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        graph = view.findViewById(R.id.graph);
        up = view.findViewById(R.id.up);
        down = view.findViewById(R.id.down);
        return new graphAdapter.HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull graphAdapter.HistoryViewHolder holder, int position) {
        graphAdapterList product = productList.get(position);
        String d1,d2,d3,d4,p1,p2,p3,p4, inc, name;

        double price1, price2;
        //loading the image
        d1 = product.getd1();
        d2 = product.getd2();
        d3 = product.getd3();
        d4 = product.getd4();
        p1 = product.getp1();
        p2 = product.getp2();
        p3 = product.getp3();
        p4 = product.getp4();
        price1 = Double.parseDouble(product.getPrice());
        price2 = Double.parseDouble(product.getPrice2());
        inc = Double.toString((price1-price2));
        name = product.getName();
        setGraph(d1,d2,d3,d4,p1,p2,p3,p4, name);
        holder.price.setText(product.getPrice());
        holder.pName.setText(product.getName());
        holder.inc.setText("RM"+inc);
        if ((price1-price2)>0)
            up.setVisibility(View.VISIBLE);
        if ((price1-price2)<0)
            down.setVisibility(View.VISIBLE);
    }
    public void setGraph(String d1s, String d2s, String d3s, String d4s, String dd1s, String dd2s, String dd3s, String dd4s, String name){
        Float dd1,dd2,dd3,dd4;
        Date d1=null, d2=null, d3=null, d4=null;
        dd1 = Float.valueOf(dd1s);
        dd2 = Float.valueOf(dd2s);
        dd3 = Float.valueOf(dd3s);
        dd4 = Float.valueOf(dd4s);
        try {
            d1 = dateParser.parse(d1s);
            d2 = dateParser.parse(d2s);
            d3 = dateParser.parse(d3s);
            d4 = dateParser.parse(d4s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Week");
        gridLabel.setVerticalAxisTitle("Price");
        graph.setTitle("Price of " + name);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(d1, dd1),
                new DataPoint(d2, dd2),
                new DataPoint(d3, dd3),
                new DataPoint(d4, dd4)
        });

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(mCtx));
        graph.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d4.getTime());
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        graph.getGridLabelRenderer().setHumanRounding(false);
        graph.addSeries(series);
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView pName;
        TextView price;
        TextView inc;
        public HistoryViewHolder(View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            inc = itemView.findViewById(R.id.inc);
        }
    }
}
