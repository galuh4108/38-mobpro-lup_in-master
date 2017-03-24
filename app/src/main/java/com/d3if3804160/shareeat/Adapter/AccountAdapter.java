package com.d3if3804160.shareeat.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.d3if3804160.shareeat.R;

import java.util.ArrayList;

/**
 * Created by yasmine on 11/6/2016.
 */

public class AccountAdapter extends BaseAdapter {

    ArrayList<String> historyList;
    private final Context context;
    LayoutInflater inflater;
    ArrayList<String> pilih;


    public AccountAdapter(Context context, ArrayList<String> historyList) {

        this.historyList = historyList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        pilih = new ArrayList<>();
    }


    @Override
    public int getCount() {
        if (null == historyList)
            return 0;
        else
            return historyList.size();
    }

    @Override
    public String getItem(int position) {
        if (null == historyList) return null;
        else
            return historyList.get(position);
    }


    @Override
    public long getItemId(int position) {
        //    Hewan hewenObject = getItem(position);

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_history, parent, false);
        TextView text1 = (TextView) convertView.findViewById(R.id.name);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.pict);

        if (historyList != null) {
            text1.setText(historyList.get(position));
            imageView.setImageResource(R.drawable.armorkopi);
        }
        if (pilih.contains(getItem(position))) {
            convertView.setBackgroundColor(Color.LTGRAY);
        } else convertView.setBackgroundColor(Color.TRANSPARENT);
        return convertView;
    }

    public void setPilihan(String pilihan) {
        if (pilihan.equals("")) {
            pilih = new ArrayList<>();
        } else {
            if (pilih.contains(pilihan)) {
                pilih.remove(pilihan);
            } else pilih.add(pilihan);
        }
        notifyDataSetChanged();
    }

    public boolean showedit() {
        return (pilih.size() == 1);
    }

    public String getPilihan() {
        return pilih.toString();
    }

    public void deleteHewan(){
        for(int i = 0; i< pilih.size(); i++){
            historyList.remove(historyList.indexOf(pilih.get(i)));
        }
        notifyDataSetChanged();
    }


    public void suntingHewan(String nama, String ganti) {
        int position =historyList.indexOf(nama);
        historyList.set(position,ganti);
        notifyDataSetChanged();
    }

    public String getNama(){
        return getItem(historyList.indexOf(pilih.get(0)));
    }

}
