package com.yuly.elaundry.kurir.model.database;

/**
 * Created by anonymous on 28/11/16.
 */

public class Lokasi {

    int id;
    String note;
    int status;
    String created_at, konsumen_id,latitude, longitude, jarak;

    // constructors
    public Lokasi() {
    }

    public Lokasi(String note, int status) {
        this.note = note;
        this.status = status;
    }

    public Lokasi(String konsumen_id,String latitude, String longitude,String jarak,int status) {
        this.konsumen_id = konsumen_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.jarak = jarak;
        this.status = status;
    }

    public Lokasi(int id, String latitude, String longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Lokasi(int id, String konsumen_id,String latitude, String longitude,String jarak,int status) {
        this.id = id;
        this.konsumen_id = konsumen_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.jarak = jarak;
        this.status = status;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }

    // getters
    public long getId() {
        return this.id;
    }

    public String getNote() {
        return this.note;
    }

    public int getStatus() {
        return this.status;
    }

    public String getKonsumenId() {
        return this.konsumen_id;
    }

    public void setKonsumenId(String konsumen_id) {
        this.konsumen_id = konsumen_id;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getJarak() {
        return this.jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }
}
