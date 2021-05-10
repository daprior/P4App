package com.example.p4projectdaniel;

import androidx.annotation.DrawableRes;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    public final LatLng lokation1 = new LatLng(57.0485,  9.9286);
    public final LatLng lokation2 = new LatLng(57.0455, 9.9151);
    public final LatLng lokation3 = new LatLng(57.0421,  9.9422);


    public Marker markerlokation1;
    public Marker markerlokation2;
    public Marker markerlokation3;

    private BitmapDescriptor Loop;

    //based on https://stackoverflow.com/questions/42365658/custom-marker-in-google-maps-in-android-with-vector-asset-icon
    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId, float zoom) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        int w = (int)(zoom*vectorDrawable.getIntrinsicWidth());
        int h = (int)(zoom*vectorDrawable.getIntrinsicHeight());
        vectorDrawable.setBounds(0, 0, w, h);
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Loop = bitmapDescriptorFromVector(this,R.drawable.ic_baseline_restore_from_trash_24,2.0f);
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

        markerlokation1 = mMap.addMarker(new MarkerOptions().position(lokation1).title("Aalborg Havnefront"));
        markerlokation1.setTag(0);
        markerlokation1.setIcon(Loop);

        markerlokation2 = mMap.addMarker(new MarkerOptions().position(lokation2).title("Aalborg Centrum"));
        markerlokation2.setTag(1);
        markerlokation2.setIcon(Loop);

        markerlokation3 = mMap.addMarker(new MarkerOptions().position(lokation3).title("Aalborg Øst"));
        markerlokation3.setTag(1);
        markerlokation3.setIcon(Loop);



        // Set a listener for marker click.
        mMap.setOnMarkerClickListener( this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lokation1));
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
                    " Er valgt ", Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}