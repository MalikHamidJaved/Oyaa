package com.verbosetech.yoohoo.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.verbosetech.yoohoo.R;
import com.verbosetech.yoohoo.adapters.PlaceArrayAdapter;



import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsFregment extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String LOG_TAG = "maps";
    private GoogleMap mMap;
    TextView back;
    public static Double lat;
    public static Double lng;
    public static String address;
    Button done;
    TextView textView;
    private GoogleApiClient googleApiClient;
    RelativeLayout searchContainer;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    AutoCompleteTextView atvLocation;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_fregment);
        searchContainer=findViewById(R.id.search);
        atvLocation=findViewById(R.id.location_autoCompleteTextView);
        lat=0.0;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this,GOOGLE_API_CLIENT_ID, MapsFregment.this)
                .addConnectionCallbacks(this)
                .build();
        lng=0.0;
        address="";
        textView=(TextView)findViewById(R.id.tv_click_msg);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            askForPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,100);

        }else{

            askForPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,100);
           // Toast.makeText(this, "Location Permission required", Toast.LENGTH_SHORT).show();
            //onDoneClick();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        back = (TextView) findViewById(R.id.tv_back);
        done=(Button)findViewById(R.id.done);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                onBackPressed();

            }
        });
        mapFragment.getMapAsync(this);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDoneClick();
            }
        });
        atvLocation.setThreshold(3);
        atvLocation.setOnItemClickListener(mAutocompleteClickListener);
        atvLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(i);
                final String placeId = String.valueOf(item.placeId);
                Log.i("Mapsactivity", "Selected: " + item.description);
                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
                Log.i("Mapsactivity", "Fetching details for ID: " + item.placeId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, null);
        atvLocation.setAdapter(mPlaceArrayAdapter);
    }
    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i("Mapsactivity", "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i("Mapsactivity", "Fetching details for ID: " + item.placeId);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
            MapsFregment.lat=place.getLatLng().latitude;
            MapsFregment.lng=place.getLatLng().longitude;
            MapsFregment.address=place.getAddress().toString();
            LatLng sydney = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
            mMap.addMarker(new MarkerOptions().position(sydney).title(place.getAddress().toString()) );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));

        }
    };
    public void onDoneClick(){
     onBackPressed();
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
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MapsFregment.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsFregment.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MapsFregment.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MapsFregment.this, new String[]{permission}, requestCode);
            }
        } else {
           // Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (getIntent().hasExtra("lat") ){
        if(getIntent().getExtras().get("lat")!=null){
            textView.setVisibility(View.INVISIBLE);
            MapsFregment.lat=Double.parseDouble(getIntent().getExtras().get("lat").toString());
            MapsFregment.lng=Double.parseDouble(getIntent().getExtras().get("lng").toString());
            LatLng sydney = new LatLng(MapsFregment.lat,MapsFregment.lng);
            //if(MapsFregment.lat!=null||MapsFregment.lat==0.0)
            //MapsFregment.address=getAddress(MapsFregment.lat,MapsFregment.lng);
            mMap.addMarker(new MarkerOptions().position(sydney).title("User address"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
            searchContainer.setVisibility(View.GONE);
        }
        }
        else{
            LatLng sydney = new LatLng(31.5204,74.3587);
            //if(MapsFregment.lat!=null||MapsFregment.lat==0.0)
            //MapsFregment.address=getAddress(MapsFregment.lat,MapsFregment.lng);
           // mMap.addMarker(new MarkerOptions().position(sydney).title("User address"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,10));
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    textView.setVisibility(View.VISIBLE);
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Your Selected Position")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    MapsFregment.lat=latLng.latitude;
                    MapsFregment.lng=latLng.longitude;
                    MapsFregment.address=getAddress(MapsFregment.lat,MapsFregment.lng);
                    Toast.makeText(MapsFregment.this, MapsFregment.address.replace("null,"," "), Toast.LENGTH_SHORT).show();

                }
            });
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            askForPermission(android.Manifest.permission.ACCESS_FINE_LOCATION,100);

        }else{
            mMap.setMyLocationEnabled(true);
            if (googleApiClient == null) {
                googleApiClient = new GoogleApiClient.Builder(this)
                        .addApi(LocationServices.API).addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(MapsFregment.this).build();
                googleApiClient.connect();
                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(30 * 1000);
                locationRequest.setFastestInterval(5 * 1000);
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest);

                // **************************
                builder.setAlwaysShow(true); // this is the key ingredient
                // **************************

                PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                        .checkLocationSettings(googleApiClient, builder.build());
                result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                    @Override
                    public void onResult(LocationSettingsResult result) {
                        final Status status = result.getStatus();
                        final LocationSettingsStates state = result
                                .getLocationSettingsStates();
                        switch (status.getStatusCode()) {
                            case LocationSettingsStatusCodes.SUCCESS:
                                // All location settings are satisfied. The client can
                                // initialize location
                                // requests here.
                                break;
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                // Location settings are not satisfied. But could be
                                // fixed by showing the user
                                // a dialog.
                                try {
                                    // Show the dialog by calling
                                    // startResolutionForResult(),
                                    // and check the result in onActivityResult().
                                    status.startResolutionForResult(MapsFregment.this, 1000);
                                } catch (IntentSender.SendIntentException e) {
                                    // Ignore the error.
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                // Location settings are not satisfied. However, we have
                                // no way to fix the
                                // settings so we won't show the dialog.
                                break;
                        }
                    }
                });
            }
        }

        // Add a marker in Sydney and move the camera

    }
    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getApplication(), Locale.getDefault());

        String add=null;
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if(addresses.isEmpty())
                return "Unable to get address";
            Address obj = addresses.get(0);
            add = obj.getAddressLine(0);
            add = add + "," + obj.getSubThoroughfare();
            add = add + "," + obj.getLocality();
            add = add + "," + obj.getSubAdminArea();
            add = add + "," + obj.getAdminArea();
            add = add + "," + obj.getCountryName();
            add.replace("null","");
            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            add=e.getMessage();
        }
        return add;
    }
    @Override
    public void onBackPressed(){
        if(MapsFregment.lat==null)
        {
            MapsFregment.lat=0.0;
            MapsFregment.lng=0.0;
            MapsFregment.address="";


        }
        Intent output = new Intent();
        output.putExtra("lat",  MapsFregment.lat);
        output.putExtra("lng",  MapsFregment.lng);
        output.putExtra("address",  MapsFregment.address);
        setResult(RESULT_OK, output);
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
