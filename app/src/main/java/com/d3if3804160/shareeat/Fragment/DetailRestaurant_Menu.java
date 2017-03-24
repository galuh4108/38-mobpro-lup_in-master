package com.d3if3804160.shareeat.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.d3if3804160.shareeat.DetailRestaurant;
import com.d3if3804160.shareeat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fajar on 11/10/2016.
 */

public class DetailRestaurant_Menu extends Fragment {
    private List<String> menu;
    ListView listView;
    ArrayAdapter menuAdapter;
    FirebaseDatabase database;
    String id;
    public DetailRestaurant_Menu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_detail_restaurant_menu, container, false);
        menu = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        listView = (ListView) view.findViewById(R.id.list_view);
        menuAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,menu);
        listView.setAdapter(menuAdapter);
        id = ((DetailRestaurant)getActivity()).getId();
        persiapanMenu();
        return view;
    }

    private void persiapanMenu() {


        DatabaseReference myRef = database.getReference().child("Menu").child(id);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    try {
                        String nama = (String) dataSnapshot.child("nama").getValue();
                        menu.add(nama);
                        menuAdapter.notifyDataSetChanged();

                    } catch (Exception ex) {
                        Log.e("MESSAGE", ex.getMessage());
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Gagal Terhubung, Periksa Konesi Anda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}