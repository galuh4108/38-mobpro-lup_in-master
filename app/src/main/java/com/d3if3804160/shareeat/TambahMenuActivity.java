package com.d3if3804160.shareeat;


import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TambahMenuActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private List<String> menu;
    ListView listView;
    String id;
    ArrayAdapter menuAdapter;
    FloatingActionButton floatingActionButton;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_menu);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = FirebaseDatabase.getInstance();
        floatingActionButton = (FloatingActionButton)  findViewById(R.id.fab);
        //get Bundle
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("ID")!= null)
        {
            id=bundle.getString("ID");
            System.out.println(id);
        }

        menu = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_view);
        menuAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,menu);
        listView.setAdapter(menuAdapter);
        persiapanMenu();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogAdd();
            }
        });
    }

    private void createDialogAdd(){
        DatabaseReference ref = database.getReference();
        DatabaseReference postsRef = ref.child("Menu").child(id);
        final DatabaseReference newPostRef = postsRef.push();
        View v = getLayoutInflater().inflate(R.layout.alertdialog, null);
        final EditText editText = (EditText)v.findViewById(R.id.editAlert);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data")
                .setView(v)
                .setPositiveButton("Tambah", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, String> menu = new HashMap<String, String>();
                        if (!editText.getText().toString().isEmpty()){
                            menu.put("nama", editText.getText().toString().toUpperCase());
                            newPostRef.setValue(menu);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Batal", null)
                .show();

    }

    private void  persiapanMenu() {


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
                Toast.makeText(getApplicationContext(), "Gagal Terhubung, Periksa Konesi Anda", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
