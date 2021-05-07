package com.example.p4projectdaniel;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

//bases on https://developers.google.com/maps/documentation/android-sdk/marker

public class kort_activity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;

    public final LatLng molok1 = new LatLng(57.025546, 9.959746);
    public final LatLng molok2 = new LatLng(57.026015, 9.959093);
    public final LatLng molok3 = new LatLng(57.026152, 9.959249);


    public Marker markerMolok1;
    public Marker markerMolok2;
    public Marker markerMolok3;

    private BitmapDescriptor trashCan;

    //based on https://stackoverflow.com/questions/42365658/custom-marker-in-google-maps-in-android-with-vector-asset-icon
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId, float zoom) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        int w = (int) (zoom * vectorDrawable.getIntrinsicWidth());
        int h = (int) (zoom * vectorDrawable.getIntrinsicHeight());
        vectorDrawable.setBounds(0, 0, w, h);
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kort_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        trashCan = bitmapDescriptorFromVector(this, R.drawable.ic_baseline_restore_from_trash_24, 2.0f);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        markerMolok1 = mMap.addMarker(new MarkerOptions().position(molok1).title("Hadsundvej 182A"));
        markerMolok1.setTag(0);
        markerMolok1.setIcon(trashCan);

        markerMolok2 = mMap.addMarker(new MarkerOptions().position(molok2).title("Hadsundvej 182B"));
        markerMolok2.setTag(1);
        markerMolok2.setIcon(trashCan);

        markerMolok3 = mMap.addMarker(new MarkerOptions().position(molok3).title("Hadsundvej 182C"));
        markerMolok3.setTag(1);
        markerMolok3.setIcon(trashCan);


        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(molok1));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this, marker.getTitle() +
                    " has been clicked " + clickCount + " times.", Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
