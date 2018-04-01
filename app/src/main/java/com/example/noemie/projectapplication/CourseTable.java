package com.example.noemie.projectapplication;

/**
 * Created by Noemie on 30/03/2018.
 */

public class CourseTable {
    private long id;
    private String nom;
    private String  date;
    private String lat;
    private String lon;
    private String time;
    private String com;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String name) {
        this.nom = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTemps() {
        return time;
    }

    public void setTemps(String tps) {
        this.time = tps;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    /*Méthode utilisée par ArrayAdapter dans la listView*/
    public String toString(){
        return ("Nom: " + nom + "\nDate: " + date + "\nLatitude: " + lat + "\nLongitude: "
                + lon + "\nTemps: " + time + "\nCommentaire: " + com);
    }
}
