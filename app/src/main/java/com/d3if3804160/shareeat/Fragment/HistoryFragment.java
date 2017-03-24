package com.d3if3804160.shareeat.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.d3if3804160.shareeat.Adapter.HistoryAdapter;
import com.d3if3804160.shareeat.Database.HistoryDB;
import com.d3if3804160.shareeat.Model.Tempat_Makan;
import com.d3if3804160.shareeat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasmine on 11/6/2016.
 */

public class HistoryFragment extends Fragment {

    HistoryDB db;
    private HistoryAdapter adapter;
    private RecyclerView recyclerView;
    private List<Tempat_Makan> RestauranList;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        db = new HistoryDB(getActivity());
        RestauranList = new ArrayList<>();
        adapter = new HistoryAdapter(getContext(), R.layout.list_history, db.getData(), 0);

        ListView listView = (ListView) view.findViewById(R.id.daftarHistory);

        listView.setAdapter(adapter);
        //listView.setEmptyView(view.findViewById(R.id.kosong));   //jika tidak ada data, akan muncul teks


        adapter.swapCursor(db.getData());
        listView.setAdapter(adapter);
        return view;

    }
}

