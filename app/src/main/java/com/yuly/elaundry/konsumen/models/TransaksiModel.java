package com.yuly.elaundry.konsumen.models;

/**
 * Created by anonymous on 25/11/16.
 */

public class TransaksiModel {
    private String noid, konsumen_id, deskripsi, tanggal, nomer, harga, alamat;

    public TransaksiModel() {
    }

    public TransaksiModel(String konsumen_id, String deskripsi, String tanggal, String noid, String nomer, String harga, String alamat) {
        this.konsumen_id = konsumen_id;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.noid = noid;
        this.nomer = nomer;
        this.harga = harga;
        this.alamat = alamat;
    }

    public String getKonsumenId() {
        return konsumen_id;
    }

    public void setKonsumenId(String konsumen_id) {
        this.konsumen_id = konsumen_id;
    }

    public String getNoId() {
        return noid;
    }

    public void setNoId(String noid) {
        this.noid = noid;
    }


    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }


    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }


    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }



    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}