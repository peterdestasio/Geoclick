package com.hanson.geoclick;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hanson.geoclick.Helper.DBHelper;
import com.hanson.geoclick.Helper.ImageHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {

    //helper
    ImageHelper imageHelper;

    //Variable of Camera
    private static final int PICK_FROM_CAMERA = 0;

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
                    BitmapDrawable d = (BitmapDrawable)((ImageView) findViewById(R.id.imageView_takecam)).getDrawable();
                    Bitmap bitmap = d.getBitmap();
                    Bitmap thBitmap = imageHelper.getThubmail(bitmap);
                    byte[] makeMainImg = imageHelper.getByteArrayFromBitmap(bitmap);
                    byte[] makeThumbnail = imageHelper.getByteArrayFromBitmap(thBitmap);

                    dbHelper.Picture_Insert(country.getText().toString(), city.getText().toString(),
                            lat.getText().toString(),lon.getText().toString(), makeThumbnail, makeMainImg);
                }
                else
                {
                    Toast.makeText(v.getContext(), "Please, pick your picture!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                lat.setText(String.valueOf(location.getLatitude()));
                lon.setText(String.valueOf(location.getLongitude()));


                Geocoder geocoder;
                List<Address> addresses;

                geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                try{
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                    if (!addresses.isEmpty()){
                        city.setText(addresses.get(0).getLocality());
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


    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //from camera
            if (requestCode == PICK_FROM_CAMERA) {
                Bundle extra = data.getExtras();
                if (extra != null) {
                    //Toast.makeText(this.getContext(), "ok", Toast.LENGTH_SHORT).show();
                    Bitmap photo = (Bitmap) extra.getParcelable("data");
                    //Bitmap rphoto = Bitmap.createScaledBitmap(photo, 350, 200, true);
                    //Toast.makeText(this, "ok",Toast.LENGTH_SHORT).show();

                    photoImageView.setImageBitmap(imageHelper.resizeBitmapImage(photo, 1024));


                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
                        }
                        return;
                    }
                    locationManager.requestSingleUpdate( "gps", locationListener, null );
                    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                    //locationManager.removeUpdates(this.locationListener);
                }
            }
        }
        //mPhotoImageView.setImageURI(data.getData());
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
}
