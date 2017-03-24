package com.d3if3804160.shareeat.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.d3if3804160.shareeat.R;

import java.util.ArrayList;

/**
 * Created by Fajar on 11/10/2016.
 */

public class MenuMakanan_Adapter extends RecyclerView.Adapter<MenuMakanan_Adapter.ViewHolder> {

    private ArrayList<String> rvData;

    public MenuMakanan_Adapter(ArrayList<String> inputData) {
        rvData = inputData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public TextView namaMakanan;

        public ViewHolder(View v) {
            super(v);
            namaMakanan = (TextView) v.findViewById(R.id.nama_makanan);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_menu_makanan, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        final String name = rvData.get(position);
        holder.namaMakanan.setText(rvData.get(position));
    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return rvData.size();
    }
}
