package com.example.lenovo.proyectoaplicacionesmoviles.Catalogo;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.bumptech.glide.request.RequestOptions;
import com.example.lenovo.proyectoaplicacionesmoviles.MainActivity;
import com.example.lenovo.proyectoaplicacionesmoviles.R;

import static android.app.Activity.*;

/**
 * Created by lenovo on 21-05-2018.
 */

public class CatalogoVistaFragment extends Fragment {


    private int PICK_IMAGE_REQUEST = 1;
    ImageView imagePreview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        FloatingActionButton addImage = getActivity().findViewById(R.id.AddImage);
        //imagePreview = getActivity().findViewById(R.id.imagePreview);

        View view = inflater.inflate(R.layout.fragment_catalogo_vista, container, false);
        view.findViewById(R.id.AddImage).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
                else {
                    startGallery();
                }
            }

        });

        return view;
}
    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == 1000) {
                Uri returnUri = data.getData();

                try {
                    Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                    ImageView imagePreview = getActivity().findViewById(R.id.imagePreview);
                    imagePreview.setImageBitmap(bitmapImage);

                }catch (IOException e) {
                        e.printStackTrace();
                    }

            }

        }
        Uri returnUri = data.getData();
        Glide.with(this)
                .load(returnUri)
                .apply(new RequestOptions().override(1280, 1280).placeholder(R.drawable.ef_image_placeholder).error(R.drawable.delete_icon))
                .into(imagePreview);





        }



}