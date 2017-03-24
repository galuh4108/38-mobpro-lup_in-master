package com.d3if3804160.shareeat;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.d3if3804160.shareeat.Adapter.Restaurant_Adapter;
import com.d3if3804160.shareeat.Model.Tempat_Makan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deni Cahya Wintaka on 11/9/2016.
 */

public class TambahTempatdanMenu extends Fragment {
    private RecyclerView recyclerView;
    private Restaurant_Adapter adapter;
    private List<Tempat_Makan> RestauranList;
    FloatingActionButton floatingActionButton;
    FirebaseAuth auth;


    public TambahTempatdanMenu() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tambahtempatdanmenu, container, false);
        auth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        floatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);

        RestauranList = new ArrayList<>();
        adapter = new Restaurant_Adapter(getActivity(), RestauranList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new Restaurant_Adapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v , int position) {
                Intent intent = new Intent(getActivity(), TambahMenuActivity.class);
                intent.putExtra("ID",RestauranList.get(position).getId());
                startActivity(intent);
            }
        });


        tambahMakanan();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inflate the fragment
                startActivity(new Intent(getActivity(), TambahTempatMakanActivity.class));
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    /**
     * Adding few Restauran for testing
     */
    private void  tambahMakanan() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Tempat_Makan");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null ) {
                    String id = (String) dataSnapshot.child("user").getValue();
                    if(id.equals(auth.getCurrentUser().getUid())){
                        try {
                            Tempat_Makan tempatMakan = new Tempat_Makan(dataSnapshot.getKey(),
                                    (String) dataSnapshot.child("nama").getValue(),
                                    (String) dataSnapshot.child("alamat").getValue(),
                                    (String) dataSnapshot.child("url").getValue());
                            RestauranList.add(tempatMakan);
                            recyclerView.scrollToPosition(RestauranList.size() - 1);
                            adapter.notifyItemInserted(RestauranList.size() - 1);
                        } catch (Exception ex) {
                            Log.e("MESSAGE", ex.getMessage());
                        }
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

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}

