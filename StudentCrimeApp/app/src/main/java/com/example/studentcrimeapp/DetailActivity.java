package com.example.studentcrimeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.studentcrimeapp.databinding.ActivityDetailBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {

    private static final int PERMISSION_CAM = 1;
    private static final int INTENT_CAM = 2;

    private Uri savePicturePath = null;
    private boolean permissionGranted;

    ActivityDetailBinding binding;

    Intent intent;
    DBHandler dbHandler;

    int crime_id;
    int position;
    String title;
    boolean solved;
    String date;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view_ = binding.getRoot();

        setContentView(view_);

        dbHandler = new DBHandler(this);

        intent = getIntent();
        crime_id = intent.getIntExtra("id", 0);
        position = intent.getIntExtra("position", 0);
        title = intent.getStringExtra("title");
        solved = intent.getBooleanExtra("solved", false);
        date = intent.getStringExtra("date");
        image = intent.getStringExtra("image");

        binding.editTextTitle.setText(title);
        binding.checkBox.setChecked(solved);
        binding.dateTime.setText(date);
        binding.imageView.setImageBitmap(BitmapFactory.decodeFile(image));

        binding.imageButton.setOnClickListener(v -> {
            Dexter.withContext(this).withPermission(Manifest.permission.CAMERA)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, INTENT_CAM);
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                            showRationaleDialog();
                        }
                    }).onSameThread().check();
        });

        binding.editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dbHandler.updateCrime(crime_id, s.toString(), null, solved ? 1 : 0, image);
                title = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            solved = binding.checkBox.isChecked();
            dbHandler.updateCrime(crime_id, title, null, solved ? 1 : 0, image);
        });

        binding.dateTime.setOnClickListener(v -> {
            final Calendar currentDate = Calendar.getInstance();
            Calendar date_c = Calendar.getInstance();
            new DatePickerDialog(DetailActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
                date_c.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(DetailActivity.this, (view1, hourOfDay, minute) -> {
                    date_c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    date_c.set(Calendar.MINUTE, minute);
                    binding.dateTime.setText(date_c.getTime().toString());
                    dbHandler.updateCrime(crime_id, title, date_c.getTime(), solved ? 1 : 0, image);
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

        });

        binding.delete.setOnClickListener(v -> {
            dbHandler.deleteCrime(title);
            dbHandler.getCrimes();
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (data != null) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                binding.imageView.setImageBitmap(thumbnail);
                savePicturePath = savePicture(thumbnail);
                image = savePicturePath.toString();
                dbHandler.updateCrime(crime_id, title, null, solved ? 1 : 0, image);
            }
        }
    }

    private void showRationaleDialog(){
        new AlertDialog.Builder(this)
                .setMessage("Access camera permissions")
                .setPositiveButton("Ask me", (dialog, which) -> {
                    try{
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    } catch (ActivityNotFoundException e){
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private Uri savePicture(Bitmap bitmap){
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File file = wrapper.getDir("myGallery", Context.MODE_PRIVATE);
        file = new File(file, UUID.randomUUID().toString() + ".jpg");

        try {
            OutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return Uri.parse(file.getAbsolutePath());
    }
}