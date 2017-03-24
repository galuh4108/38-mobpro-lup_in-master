package com.d3if3804160.shareeat.Adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.d3if3804160.shareeat.Model.Tempat_Makan;
import com.d3if3804160.shareeat.R;

import java.util.List;

/**
 * Created by Deni Cahya Wintaka on 10/15/2016.
 */

public class Restaurant_Adapter extends RecyclerView.Adapter<Restaurant_Adapter.MyViewHolder> {

    private Context mContext;
    OnItemClickListener mItemClickListener;
    private List<Tempat_Makan> tempatMakanList;



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        public TextView title, count,jarak;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            jarak = (TextView) view.findViewById(R.id.jarak);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }


    public Restaurant_Adapter(Context mContext, List<Tempat_Makan> tempatMakanList) {
        this.mContext = mContext;
        this.tempatMakanList = tempatMakanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restauran_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Tempat_Makan tempatMakan = tempatMakanList.get(position);
        holder.title.setText(tempatMakan.getNama());
        holder.count.setText(tempatMakan.getAlamat());
        if (tempatMakan.getJarak() != 0.0){
            holder.jarak.setText(String.format("%.2f", (tempatMakan.getJarak()))+" KM");
        }
        // loading album cover using Glide library
        if (tempatMakan.getUrl() != null){
            String latEiffelTower = "48.858235";
            String lngEiffelTower = "2.294571";
            String url = "http://maps.google.com/maps/api/staticmap?center=" + "48.858235" + "," + "2.294571" + "&zoom=15&size=200x200&sensor=false";
            Glide.with(mContext).load(tempatMakan.getUrl()).into(holder.thumbnail);
        }


    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return tempatMakanList.size();
    }
}
