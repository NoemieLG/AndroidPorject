package com.example.noemie.projectapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChronoActivity extends AppCompatActivity implements ChronoFragment.OnFragmentInteractionListener{

    ChronoFragment chronoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrono);

        chronoFragment = ChronoFragment.newInstance("param1", "param2");
        getSupportFragmentManager().beginTransaction().add(R.id.chrono_frag, chronoFragment).commit();
    }

    public void gomap()
    {
        long time = SystemClock.elapsedRealtime() - chronoFragment.chrono.getBase();
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("chrono", time);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
