package com.example.noemie.projectapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BddExtActivity extends AppCompatActivity {

    ListView listView;
    int cpt = 1;
    TextView temps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bdd_ext);

        listView = (ListView) findViewById(R.id.listView);
        temps = findViewById(R.id.temps_xml);
        getJSON("https://seweryn-lyonnard.000webhostapp.com/nom.php");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nom = (String)parent.getItemAtPosition(position);
                System.out.println(nom);
                getJSON("https://seweryn-lyonnard.000webhostapp.com/stats.php?nom=" + nom);
            }
        });
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {

                    if (cpt == 1){
                        afficherNom(s);
                        cpt = 2;
                    }else
                        afficherStats(s);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void afficherNom(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);
        String[] Course = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            Course[i] = obj.getString("name");
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Course);
        listView.setAdapter(arrayAdapter);

    }

    private void afficherStats(String json) throws JSONException {

        JSONArray jsonArray = new JSONArray(json);
        String[] connection = new String[jsonArray.length()];
        String tps= "";

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            connection[i] = obj.getString("temps");
            System.out.println(connection[i]);
            tps = tps + connection[i] + " ";
        }
        temps.setText(tps);
    }
}
