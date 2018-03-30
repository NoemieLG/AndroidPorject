package com.example.noemie.projectapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/*Activitée qui permet d'ajouter la course d'un sprinter dans l'application*/
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    /*Pour afficher la map avec le marker*/
    private GoogleMap mMap;
    private TextView latitudeField;
    private TextView longitudeField;
    SupportMapFragment mapFragment;
    private Marker clickMarker;

    /*Pour le formulaire*/
    private EditText nom;
    private DatePicker date;
    private int jour, mois, annee;
    private EditText temps;
    private EditText com;

    //Pour sauvegarder les localisations dans une base de données
    private SportDataSource dataBdd;
    private CourseTable locationBdd;
    //private DataFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        nom = findViewById(R.id.sprinter);
        date = (DatePicker) findViewById(R.id.datePicker);
        temps = findViewById(R.id.temps);
        com = findViewById(R.id.com);
        latitudeField = findViewById(R.id.latitude_xml);
        longitudeField = findViewById(R.id.longitude_xml);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        jour = date.getDayOfMonth();
        mois = date.getMonth();
        annee = date.getYear();

        //Pour instancier l'objet de la classe LocationDataSource
        dataBdd = new SportDataSource(this);
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
        mMap.setOnMapClickListener(this);

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (latLng != null) {
            if (clickMarker != null) {
                clickMarker.remove();
            }
            clickMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Position choisie").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            latitudeField.setText("Latitude : " + String.valueOf(latLng.latitude));
            longitudeField.setText("Longitude : " + String.valueOf(latLng.longitude));
        }
    }

    /*Fonction appelé quand on clique sur le bouton pour aouter une course*/
    public void myClickHandler(View view) {
        switch (view.getId()) {
            case R.id.button3:
                dataBdd.open();
                locationBdd = dataBdd.createCourse(nom.getText().toString(),
                        String.valueOf(jour) + String.valueOf(mois) + String.valueOf(annee), latitudeField.getText().toString(),
                        longitudeField.getText().toString(), temps.getText().toString(), com.getText().toString());
                dataBdd.close();
                finish();
                break;
        }
    }
}
