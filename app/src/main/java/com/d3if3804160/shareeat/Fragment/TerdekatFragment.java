package com.d3if3804160.shareeat.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.d3if3804160.shareeat.Adapter.Restaurant_Adapter;
import com.d3if3804160.shareeat.Database.HistoryDB;
import com.d3if3804160.shareeat.DetailRestaurant;
import com.d3if3804160.shareeat.Model.Tempat_Makan;
import com.d3if3804160.shareeat.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deni Cahya Wintaka on 10/18/2016.
 */
public class TerdekatFragment extends Fragment {

    float[] jarak = new float[1];
    private RecyclerView recyclerView;
    //
    private Restaurant_Adapter adapter;
    private List<Tempat_Makan> RestauranList;
    HistoryDB db;
    ProgressBar progressBar;
    double radius=1;

    public TerdekatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new HistoryDB(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_tempat_makan, container, false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        RestauranList = new ArrayList<>();
        adapter = new Restaurant_Adapter(getActivity(), RestauranList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener(new Restaurant_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v , int position) {
                db.deleteData(RestauranList.get(position).getId());
                db.insertData(RestauranList.get(position).getId(),
                        RestauranList.get(position).getNama(),
                        RestauranList.get(position).getAlamat(),
                        RestauranList.get(position).getUrl(),
                        RestauranList.get(position).getTelepon(),
                        RestauranList.get(position).getDeskripsi()
                );
                Intent intent = new Intent(getActivity(), DetailRestaurant.class);
                intent.putExtra("ID", RestauranList.get(position).getId());
                startActivity(intent);
            }
        });

      /*  FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_tambah);
       /fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
               Toast.makeText(getContext(), "sasasasasas",Toast.LENGTH_SHORT).show();
            }
        });
*/
        prepareRestauran();

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
    private void prepareRestauran() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        final GeoFire geoFire = new GeoFire(myRef.child("location"));
        final GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(-6.971986,107.633760), radius);
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, final GeoLocation location) {
                progressBar.setVisibility(View.GONE);
                myRef.child("Tempat_Makan").child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                            try{
                                Tempat_Makan tempatMakan = new Tempat_Makan(dataSnapshot.getKey(),
                                        (String) dataSnapshot.child("nama").getValue(),
                                        (String) dataSnapshot.child("alamat").getValue(),
                                        (String) dataSnapshot.child("url").getValue(),
                                        (String) dataSnapshot.child("telepon").getValue(),
                                        (String) dataSnapshot.child("deskripsi").getValue()
                                );
                                Location.distanceBetween(-6.971986,107.633760,location.latitude,location.longitude,jarak);
                                tempatMakan.setJarak(jarak[0]/1000);
                                RestauranList.add(tempatMakan);
                                geoQuery.setRadius(radius+1);
                                adapter.notifyDataSetChanged();

                            } catch (Exception ex) {
                                Log.e("MESSAGE", ex.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onKeyExited(String key) {
                System.out.println(String.format("Key %s is no longer in the search area", key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("All initial data has been loaded and events have been fired!");
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                System.err.println("There was an error with this query: " + error);
            }
        });

/*
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    try{
                        progressBar.setVisibility(View.GONE);
                        String nama = (String) dataSnapshot.child("nama").getValue();
                        Tempat_Makan restaurant = new Tempat_Makan(nama, "Dago", covers[1]);
                        RestauranList.add(restaurant);
                        recyclerView.scrollToPosition(RestauranList.size() - 1);
                        adapter.notifyItemInserted(RestauranList.size() - 1);
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
*/
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