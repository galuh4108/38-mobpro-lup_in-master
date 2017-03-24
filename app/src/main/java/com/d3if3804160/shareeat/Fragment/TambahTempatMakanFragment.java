package com.d3if3804160.shareeat.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.d3if3804160.shareeat.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by Deni Cahya Wintaka on 10/18/2016.
 */
public class TambahTempatMakanFragment extends Fragment {

    String nama, alamat, telepon, deskripsi;
    EditText namaTxt, alamatTxt, telponTxt, deskripsiTxt;
    Spinner  areaList;
    ImageView imageView;
    byte[] byteArray;


    public TambahTempatMakanFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tambah_tempat_makan, container, false);



        namaTxt = (EditText) rootView.findViewById(R.id.nama);
        alamatTxt = (EditText) rootView.findViewById(R.id.alamat);
        telponTxt = (EditText) rootView.findViewById(R.id.telepon);
        deskripsiTxt = (EditText) rootView.findViewById(R.id.deskripsi);
        imageView =(ImageView) rootView.findViewById(R.id.imageButton);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            createDialogFoto();

            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }

    private void createDialogFoto(){
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilih Foto")
                .setMessage("Ambil Foto Dari Mana ? ")
                .setPositiveButton("Kamera", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      dispatchTakePictureIntent();
                    }
                })
                .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       loadImagefromGallery();
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    public void loadImagefromGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 2);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK ) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();
        } else if (requestCode == 2 && resultCode == getActivity().RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap =BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();

        }
    }


    void BundletoMaps(){
        // Create a storage reference from our app
        Map_Picker map_picker = new Map_Picker ();
        Bundle args = new Bundle();
        args.putString("NAMA", nama);
        args.putString("ALAMAT", alamat);
        args.putString("TELEPON", telepon);
        args.putString("DESKRIPSI", deskripsi);
        args.putByteArray("IMAGEDATA", byteArray);
        map_picker.setArguments(args);

    //Inflate the fragment
        String backStateName = getFragmentManager().getClass().getName();

        getFragmentManager().beginTransaction().
                replace(R.id.frame, map_picker)
                .addToBackStack(backStateName)
                .commit();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.tambah_restaurant, menu);
        MenuItem item = menu.findItem(R.id.simpan).setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.next:
                nama= namaTxt.getText().toString();
                alamat= alamatTxt.getText().toString();
                telepon= telponTxt.getText().toString();
                deskripsi= deskripsiTxt.getText().toString();
                if (!TextUtils.isEmpty(nama) && !TextUtils.isEmpty(alamat)  && !TextUtils.isEmpty(deskripsi) ){
                    BundletoMaps();
                }
                else if(TextUtils.isEmpty(nama)){
                    namaTxt.setError("Nama Harus Diisi");
                }
                else if(TextUtils.isEmpty(alamat) ){
                    alamatTxt.setError("Alamat Harus Diisi");
                }
                else if(TextUtils.isEmpty(deskripsi)){
                    deskripsiTxt.setError("Deskripsi Harus Diisi");
                }
                return true;
            case R.id.batal:
                getActivity().finish();
                break;
            default:
                break;
        }

        return false;
    }



}