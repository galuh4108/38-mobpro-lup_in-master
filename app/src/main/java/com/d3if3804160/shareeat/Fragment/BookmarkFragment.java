package com.d3if3804160.shareeat.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.d3if3804160.shareeat.Adapter.BookmarkAdapter;
import com.d3if3804160.shareeat.Database.BookmarkDB;
import com.d3if3804160.shareeat.R;

/**
 * Created by yasmine on 11/6/2016.
 */

public class BookmarkFragment extends Fragment {

    BookmarkDB db;
    BookmarkAdapter adapter;

    public BookmarkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        db = new BookmarkDB(getActivity());
        adapter = new BookmarkAdapter(getContext(), R.layout.list_history, db.getData(), 0);

        ListView listView = (ListView) view.findViewById(R.id.daftarHistory);


        listView.setAdapter(adapter);
        //listView.setEmptyView(view.findViewById(R.id.kosong));   //jika tidak ada data, akan muncul teks


        adapter.swapCursor(db.getData());
        listView.setAdapter(adapter);
        return view;
    }
}
