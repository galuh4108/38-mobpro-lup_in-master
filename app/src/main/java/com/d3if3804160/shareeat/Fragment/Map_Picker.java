package com.d3if3804160.shareeat.Fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.d3if3804160.shareeat.Model.Tempat_Makan;
import com.d3if3804160.shareeat.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * Created by Deni Cahya Wintaka on 11/3/2016.
 */

public class Map_Picker extends Fragment {
    String nama, alamat, telepon, deskripsi, area, url;
    MapView mMapView;
    byte[] uri;
    private GoogleMap googleMap;
    Location location;
    Marker mCurrLocationMarker;
    FirebaseDatabase database;
    DatabaseReference ref;
    double lintang, bujur;
    FirebaseAuth auth;

    public Map_Picker() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        auth = FirebaseAuth.getInstance();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.maps_picker, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);


        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.setMyLocationEnabled(true);

                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        location = googleMap.getMyLocation();
                        if (location != null) {

                            if (mCurrLocationMarker != null) {
                                mCurrLocationMarker.remove();
                            }

                            //Place current location marker
                            lintang = location.getLatitude();
                            bujur = location.getLongitude();
                            LatLng latLng = new LatLng(lintang, bujur);
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.title("Deni Cahya Wintaka");
                            markerOptions.position(latLng);
                            markerOptions.draggable(true);
                            mCurrLocationMarker = googleMap.addMarker(markerOptions);

                            //move map camera
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            googleMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                        }
                        return false;
                    }

                });


                // For dropping a marker at a point on the Map


                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {

                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {

                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        // TODO Auto-generated method stub
                        LatLng dragPosition = marker.getPosition();
                        lintang = dragPosition.latitude;
                        bujur= dragPosition.longitude;
                        Log.i("info", "on drag end :" + lintang + " dragLong :" + bujur);
                        Toast.makeText(getContext(), "Berhasil Memindahkan Lokasi", Toast.LENGTH_LONG).show();
                    }
                });
                // For zooming automatically to the location of the marker
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    void SimpanTempatMakan(){
        DatabaseReference postsRef = ref.child("Tempat_Makan");
        final DatabaseReference newPostRef = postsRef.push();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            nama= bundle.getString("NAMA");
            alamat= bundle.getString("ALAMAT");
            telepon= bundle.getString("TELEPON");
            deskripsi= bundle.getString("DESKRIPSI");
            area = bundle.getString("AREA");
            uri= bundle.getByteArray("IMAGEDATA");
        }
        final String postId = newPostRef.getKey();



        if (uri != null){
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://shareeat-ca470.appspot.com");
            StorageReference mountainsRef = storageRef.child(postId+".jpg");
            UploadTask uploadTask = mountainsRef.putBytes(uri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    url = taskSnapshot.getDownloadUrl().toString();
                    newPostRef.setValue(new Tempat_Makan(null,nama, alamat, url,telepon, deskripsi, auth.getCurrentUser().getUid()));
                    GeoFire geoFire = new GeoFire(ref.child("location"));
                    geoFire.setLocation(postId, new GeoLocation(lintang,bujur));
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Berhasil Menambah Tempat Makan", Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            newPostRef.setValue(new Tempat_Makan(null,nama, alamat, null,telepon, deskripsi, auth.getCurrentUser().getUid()));
            GeoFire geoFire = new GeoFire(ref.child("location"));
            geoFire.setLocation(postId, new GeoLocation(lintang,bujur));
            getActivity().finish();
            Toast.makeText(getActivity(), "Berhasil Menambah Tempat Makan", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tambah_restaurant, menu);
        MenuItem item = menu.findItem(R.id.next).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.simpan:
                SimpanTempatMakan();
                return false;
            case R.id.batal:
                getActivity().finish();
                break;
            default:
                break;
        }

        return false;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}