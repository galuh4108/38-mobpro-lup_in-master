package com.d3if3804160.shareeat.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.d3if3804160.shareeat.Database.BookmarkDB;
import com.d3if3804160.shareeat.Database.HistoryContract;
import com.d3if3804160.shareeat.Database.HistoryDB;
import com.d3if3804160.shareeat.DetailRestaurant;
import com.d3if3804160.shareeat.R;

/**
 * Created by Fajar on 11/10/2016.
 */

public class DetailRestaurant_Detail extends Fragment {
   HistoryDB dbHistory;
    BookmarkDB dbBookmark;
    String id;
    Cursor c;

    TextView nama, alamat, telepon, deskripsi;
    ImageView imageView;
    Switch bookmark;
    public DetailRestaurant_Detail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_restaurant_detail, container, false);
        dbHistory = new HistoryDB(getActivity());
        dbBookmark = new BookmarkDB(getActivity());
        bookmark = (Switch) view.findViewById(R.id.bookmark);
        nama= (TextView) view.findViewById(R.id.nama_tempat_makan);
        alamat= (TextView) view.findViewById(R.id.restaurant_alamat);
        telepon= (TextView) view.findViewById(R.id.restaurant_telp);
        imageView = (ImageView) view.findViewById(R.id.restaurant_foto);
        deskripsi= (TextView) view.findViewById(R.id.deskripsi_restaurant);
        //getID
        id = ((DetailRestaurant)getActivity()).getId();
        bookmark.setChecked(dbBookmark.cekBookmark());
        bookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dbBookmark.insertData(id, c.getString(c.getColumnIndex(HistoryContract.COLUMN_NAME)));
                    Toast.makeText(getActivity(), "Berhasil Simpan Bookmark", Toast.LENGTH_SHORT).show();
                } else {
                    dbBookmark.deleteData(id);
                    Toast.makeText(getActivity(), "Berhasil hapus Bookmark", Toast.LENGTH_SHORT).show();
                }
            }
        });
        c= dbHistory.getDataDetail(id);
        c.moveToFirst();
        nama.setText(c.getString(c.getColumnIndex(HistoryContract.COLUMN_NAME)));
        alamat.setText(c.getString(c.getColumnIndex(HistoryContract.COLUMN_ALAMAT)));
        telepon.setText(c.getString(c.getColumnIndex(HistoryContract.COLUMN_TELEPON)));
        deskripsi.setText(c.getString(c.getColumnIndex(HistoryContract.COLUMN_DESKRIPSI)));
        if (c.getString(c.getColumnIndex(HistoryContract.COLUMN_URL)) != null) {
            Glide.with(getActivity()).load(c.getString(c.getColumnIndex(HistoryContract.COLUMN_URL))).into(imageView);
        }
        return view;
      }
}