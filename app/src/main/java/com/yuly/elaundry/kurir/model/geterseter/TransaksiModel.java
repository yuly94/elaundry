package com.yuly.elaundry.kurir.model.geterseter;

/**
 * Created by anonymous on 25/11/16.
 */

public class TransaksiModel {
    private String noid, konsumen_id,konsumen_nama, konsumen_nohp, deskripsi, tanggal, nomer, harga, alamat;

    public TransaksiModel() {
    }

    public TransaksiModel(String konsumen_id,String konsumen_nama,String konsumen_nohp, String deskripsi, String tanggal, String noid, String nomer, String harga, String alamat) {
        this.konsumen_id = konsumen_id;
        this.konsumen_nama = konsumen_nama;
        this.konsumen_nohp = konsumen_nohp;
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


    public String getNama() {
        return konsumen_nama;
    }

    public void setNama(String konsumen_nama) {
        this.konsumen_nama = konsumen_nama;
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