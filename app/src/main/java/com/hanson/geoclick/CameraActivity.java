package com.hanson.geoclick;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Helper.ImageHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    //view data
    private static final String DATA_LAT = "Lat";
    private static final String DATA_LON = "lon";
    private static final String DATA_CITY = "city";
    private static final String DATA_COUNTRY = "country";
    private static final String DATA_PIC = "picture";

    //helper
    ImageHelper imageHelper;
    Bitmap icon;

    //Variable of Camera
    private static final int PICK_FROM_CAMERA = 1;


    //Geo Location
    private LocationManager locationManager;
    private LocationListener locationListener;

    ImageView photoImageView;
    TextView lat;
    TextView lon;
    TextView country;
    TextView city;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


            imageHelper = new ImageHelper();

            //Show backbutton
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            photoImageView = (ImageView) findViewById(R.id.imageView_takecam);
            lat = (TextView) findViewById(R.id.textView_lat);
            lon = (TextView) findViewById(R.id.textView_long);
            city = (TextView) findViewById(R.id.textView_city);
            country = (TextView) findViewById(R.id.textView_country);
            save = (Button) findViewById(R.id.button_save);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //Connect with databases
                    DBHelper dbHelper = new DBHelper(getBaseContext(), "Picture.db", null, 1);

                    if(photoImageView.getDrawable() != null && city.getText() != "" && country.getText() != "" &&
                            lat.getText() != "" && lon.getText() != "")
                    {
//                        BitmapDrawable d = (BitmapDrawable)((ImageView) findViewById(R.id.imageView_takecam)).getDrawable();
//                        Bitmap thBitmap = d.getBitmap();
                        setIcon();
                        byte[] makeThumbnail = imageHelper.getByteArrayFromBitmap(icon);


                        dbHelper.Picture_Insert(country.getText().toString(), city.getText().toString(),
                                lat.getText().toString(),lon.getText().toString(), makeThumbnail, mCurrentPhotoPath);
                        Toast.makeText(v.getContext(), "Completed saving picture ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(v.getContext(), "Please, pick your picture!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    finish();
                }
            });

        if (savedInstanceState == null)
        {
            //For Geo Location
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    lat.setText(String.valueOf(location.getLatitude()));
                    lon.setText(String.valueOf(location.getLongitude()));

                    Geocoder geocoder;
                    List<Address> addresses;

                    //Get current Geo location
                    geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                    try{
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        if (!addresses.isEmpty()){
                            //Get city from address
                            city.setText(addresses.get(0).getLocality());
                            //Get country from address
                            country.setText(addresses.get(0).getCountryName());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            };

            doTakePhotoAction();
        }
        else {
            mCurrentPhotoPath = savedInstanceState.getString(DATA_PIC);
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            photoImageView.setImageBitmap(bitmap);
            lat.setText(savedInstanceState.getString(DATA_LAT));
            lon.setText(savedInstanceState.getString(DATA_LON));
            city.setText(savedInstanceState.getString(DATA_CITY));
            country.setText(savedInstanceState.getString(DATA_COUNTRY));
        }


    }

    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            File photoFile = null;
            try {
                photoFile = createImageFile();
            }catch (IOException ex){

            }

            //Continue only if the file was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "com.hanson.geoclick.android.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, PICK_FROM_CAMERA);
            }
        }

    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        int orientation = newConfig.orientation;
//
//        if (orientation == 1){
//            setContentView(R.layout.activity_camera);
//        }
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //from camera
            if (requestCode == PICK_FROM_CAMERA) {

                //Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
                setPic(); //size control function

                //photoImageView.setImageBitmap(bitmap);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
                    }
                    return;
                }

                //When took a picture, just one time It need Geo location that is taken picture location.
                locationManager.requestSingleUpdate( "gps", locationListener, null );
            }

        }
        else if (resultCode == RESULT_CANCELED) {
            finish();
        }
    }


    //full screen capture
    String mCurrentPhotoPath;

    private File createImageFile() throws IOException{
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" ;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        //Save a file: path for us with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic(){
        //Get the dimensions of the View
        int targetW = photoImageView.getWidth();//photoImageView.getDrawable().getIntrinsicWidth();
        int targetH = photoImageView.getHeight(); //.getDrawable().getIntrinsicHeight();


        //Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        //Determine how much to scale down the image
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
        			scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }
        //Decode the image file into a bitmapsized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        //try this!!!!
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        //Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        photoImageView.setImageBitmap(bitmap);
    }

    private void setIcon(){
        //Get the dimensions of the View
        int targetW = 200;//photoImageView.getDrawable().getIntrinsicWidth();
        int targetH = 200; //.getDrawable().getIntrinsicHeight();


        //Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        //Determine how much to scale down the image
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }
        //Decode the image file into a bitmapsized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        icon = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
//
//        SharedPreferences.Editor ed = mPrefs.edit();
//        ed.putString("Lat", String.valueOf(lat.getText()));
//        ed.putString("lon", String.valueOf(lon.getText()));
//        ed.putString("city", String.valueOf(city.getText()));
//        ed.putString("country",String.valueOf(country.getText()));
//        ed.putString("path", String.valueOf(mCurrentPhotoPath));
//        ed.commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(DATA_LAT, String.valueOf(lat.getText()));
        outState.putString(DATA_LON, String.valueOf(lon.getText()));
        outState.putString(DATA_CITY, String.valueOf(city.getText()));
        outState.putString(DATA_COUNTRY, String.valueOf(country.getText()));
        outState.putString(DATA_PIC, mCurrentPhotoPath);
    }
}
