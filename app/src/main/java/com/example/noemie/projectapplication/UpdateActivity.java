package com.example.noemie.projectapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateActivity extends AppCompatActivity {

    TextView id;
    EditText tps;
    EditText commentaire;
    Button modifier;
    SportDataSource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        id = findViewById(R.id.id_xml);
        tps = findViewById(R.id.tps_xml);
        commentaire = findViewById(R.id.com_xml);
        modifier = findViewById(R.id.modif_xml);

        source = new SportDataSource(this);

        source.open();

    }

    public void courseModif(View view){
        CourseTable courseTable = null;
        long l = Long.parseLong(id.getText().toString());
        source.updateCourse( l , tps.getText().toString(), commentaire.getText().toString());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void myClickHandler(View view) {
        switch (view.getId()) {
            case R.id.modif_xml:
                courseModif(view);
                break;

        }
    }


    public void onPause(){
        super.onPause();
        source.close();
    }
}
