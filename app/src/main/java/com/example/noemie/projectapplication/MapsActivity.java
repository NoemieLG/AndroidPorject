package com.example.noemie.projectapplication;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.*;

/*Activitée qui permet d'ajouter la course d'un sprinter dans l'application*/
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
    CourseInBddFragment.OnFragmentInteractionListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    /*Pour afficher la map avec le marker*/
    private GoogleMap mMap;
    private TextView latitudeField;
    private TextView longitudeField;
    SupportMapFragment mapFragment;
    private Marker clickMarker;

    /*Pour le formulaire*/
    private EditText nom;
    //private DatePicker date;
    private EditText date;
    private int jour, mois, annee;
    private EditText temps;
    private EditText com;
    private String latreal;
    private String lonreal;
    private TextView adresse;

    //Pour sauvegarder les localisations dans une base de données
    private SportDataSource dataBdd;
    //private CourseTable courseBdd;
    private CourseInBddFragment dataFragment;
    //private ListView listView;
    private MapsActivity main;

    /*Pour le geocoding reverse*/
    protected Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        nom = findViewById(R.id.sprinter);
        //date = (DatePicker) findViewById(R.id.datePicker);
        date = findViewById(R.id.date);
        temps = findViewById(R.id.temps);
        com = findViewById(R.id.com);
        latitudeField = findViewById(R.id.latitude_xml);
        longitudeField = findViewById(R.id.longitude_xml);
        adresse = findViewById(R.id.address_xml);
        //listView = findViewById(R.id.list);

        /*Récupération des données de l'intent*/
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            /*Affichage des valeurs dans les TextView de AsyncActivity*/
            long value = extras.getLong("chrono");
            temps.setText(String.valueOf(value));
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /*Création du fragment
        dataFragment = CourseInBddFragment.newInstance("param1", "param2");
        getSupportFragmentManager().beginTransaction().add(R.id.data_frag, dataFragment).commit();*/

        /*Pour la datePicker
        jour = date.getDayOfMonth();
        mois = date.getMonth();
        annee = date.getYear();*/

        //Pour instancier l'objet de la classe LocationDataSource
        dataBdd = new SportDataSource(this);
        dataBdd.open();

        /*List<CourseTable> values = dataBdd.getAllComments();

        // utilisez SimpleCursorAdapter pour afficher les éléments dans une ListView
        ArrayAdapter<CourseTable> adapter = new ArrayAdapter<CourseTable>(this, android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);*/

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

            latreal = String.valueOf(latLng.latitude);
            lonreal = String.valueOf(latLng.longitude);

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                /*System.out.println(address);
                System.out.println(city);
                System.out.println(state);
                System.out.println(country);
                System.out.println(postalCode);
                System.out.println(knownName);*/

                adresse.setText("Adresse de la course: " + address);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void addCourse(View view){
        //@SuppressWarnings("unchecked");
        //ArrayAdapter<CourseTable> adapter = (ArrayAdapter<CourseTable>) listView.getAdapter();
        MainActivity.adapter = (ArrayAdapter<CourseTable>) MainActivity.listView.getAdapter();
        CourseTable courseBdd = null;
        courseBdd = dataBdd.createCourse(nom.getText().toString(), date.getText().toString(),
                latreal, lonreal, temps.getText().toString(), com.getText().toString());
        MainActivity.adapter.add(courseBdd);
        MainActivity.adapter.notifyDataSetChanged();
    }

    /*Fonction appelé quand on clique sur le bouton pour aouter une course*/
    public void myClickHandler(View view) {
        switch (view.getId()) {
            case R.id.button3:
                addCourse(view);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                //dataBdd.open();
                /*courseBdd = dataBdd.createCourse(nom.getText().toString(),
                        String.valueOf(jour) + "/" + String.valueOf(mois) + "/"  + String.valueOf(annee),
                        latreal, lonreal, temps.getText().toString(), com.getText().toString());*/
                /*ArrayAdapter<CourseTable> adapter = (ArrayAdapter<CourseTable>) listView.getAdapter();
                courseBdd = dataBdd.createCourse(nom.getText().toString(), date.getText().toString(),
                        latreal, lonreal, temps.getText().toString(), com.getText().toString());
                adapter.add(courseBdd);
                adapter.notifyDataSetChanged();
                dataBdd.close();
                //if (courseBdd != null){
                    /*Intent intent = new Intent(this, MainActivity.class);
                /*Récupération de l'opération et de la solution
                    intent.putExtra("courseBdd", (Serializable) courseBdd);
                    intent.putExtra("dataBdd", (Serializable) dataBdd);
                    startActivity(intent);

                    dataBdd.open();
                    dataFragment.setCourseFromBdd(courseBdd);
                    dataBdd.close();
                }*/
                break;
            case R.id.button_delete:
                //dataBdd.deleteCourse();
                break;
        }
    }

    public void onPause() {
        super.onPause();
        dataBdd.close();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
