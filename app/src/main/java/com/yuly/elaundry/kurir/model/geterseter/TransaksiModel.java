package com.yuly.elaundry.kurir.model.geterseter;

/**
 * Created by anonymous on 25/11/16.
 */

public class TransaksiModel {
    private String noid, pemesanan_id, konsumen_id,konsumen_nama, konsumen_nohp,konsumen_latitude,konsumen_longitude, deskripsi, tanggal, nomer, harga, alamat;

    public TransaksiModel() {
    }

    public TransaksiModel(String pemesanan_id,String konsumen_id,String konsumen_nama,String konsumen_nohp, String deskripsi, String tanggal, String noid, String nomer, String harga, String alamat) {

        this.pemesanan_id = pemesanan_id;
        this.konsumen_id = konsumen_id;
        this.konsumen_nama = konsumen_nama;
        this.konsumen_nohp = konsumen_nohp;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.noid = noid;
        this.nomer = nomer;
        this.harga = harga;
        this.konsumen_latitude = konsumen_latitude;
        this.konsumen_longitude = konsumen_longitude;
        this.alamat = alamat;
    }

    public String getPemesananId() {
        return pemesanan_id;
    }

    public void setPemesananId(String pemesanan_id) {
        this.pemesanan_id = pemesanan_id;
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


    public String getNama() {
        return konsumen_nama;
    }

    public void setNama(String konsumen_nama) {
        this.konsumen_nama = konsumen_nama;
    }

    public String getLatitude() {
        return konsumen_latitude;
    }

    public void setLatitude(String konsumen_latitude) {
        this.konsumen_latitude = konsumen_latitude;
    }

    public String getLongitude() {
        return konsumen_longitude;
    }

    public void setLongitude(String konsumen_longitude) {
        this.konsumen_longitude = konsumen_longitude;
    }



    public String getNoHp() {
        return konsumen_nohp;
    }

    public void setNoHp(String konsumen_nohp) {
        this.konsumen_nohp = konsumen_nohp;
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