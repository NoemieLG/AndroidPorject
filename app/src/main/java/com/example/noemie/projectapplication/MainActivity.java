package com.example.noemie.projectapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class
MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CourseInBddFragment.OnFragmentInteractionListener {

    CourseInBddFragment dataFragment;
    SportDataSource dataBdd;
    CourseTable courseBdd;
    public static ListView listView;
    public static ArrayAdapter<CourseTable> adapter;
    Button delete;

    //MapsActivity course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Pour instancier l'objet de la classe LocationDataSource
        dataBdd = new SportDataSource(this);

        listView = findViewById(R.id.list);
        dataBdd.open();

        List<CourseTable> values = dataBdd.getAllComments();

        // utilisez SimpleCursorAdapter pour afficher les éléments dans une ListView
        adapter = new ArrayAdapter<CourseTable>(this, android.R.layout.simple_list_item_1, values);
        listView.setAdapter(adapter);

        delete = findViewById(R.id.button_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOldestCourse(listView);
            }
        });

        /*Création du fragment
        dataFragment = CourseInBddFragment.newInstance("param1", "param2");
        getSupportFragmentManager().beginTransaction().add(R.id.data_frag, dataFragment).commit();

        /*Récupération des données de l'intent
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            /*Affichage des valeurs dans les TextView de AsyncActivity
            dataBdd = (SportDataSource) extras.getSerializable("dataBdd");
            courseBdd = (CourseTable) extras.getSerializable("courseBdd");
            dataBdd.open();
            dataFragment.setCourseFromBdd(courseBdd);
            dataBdd.close();
        }*/
    }

    public void deleteOldestCourse(ListView listView){
        if (listView.getAdapter().getCount() > 0) {
            CourseTable courseTable = (CourseTable) listView.getAdapter().getItem(0);
            dataBdd.open();
            dataBdd.deleteCourse(courseTable);
            adapter.remove(courseTable);
            dataBdd.close();
        }
        else{
            String msg = String.format("Aucunes courses à supprimer");
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        /*if (id == R.id.action_bdd) {
            //Pour récupérer les donner des la base de données et les afficher dans le fragment
            if (course.courseBdd != null){
                dataBdd.open();
                dataFragment.setLocationFromBdd(locationBdd);
                dataBdd.close();
            }
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.add_match) {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_stat) {

        } else if (id == R.id.nav_info) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();
        dataBdd.close();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
