package com.elaundry.kurir.geterseter;

import java.util.ArrayList;

/**
 * Created by anonymous on 22/02/16.
 */
public class AlamatModels {
    //Data Variables
    private String imageUrl;
    private String name;
    private int rank;
    private String realName;

    private String alamat_nama;
    private String alamat_jalan;
    private String alamat_kota;



    private String dibuat_oleh;
    private String firstAppearance;
    private ArrayList<String> powers;

    //Getters and Setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getRealName() {
        return realName;
    }

    public void setAlamatNama(String alamat_nama) {
        this.alamat_nama = alamat_nama;
    }

    public String getAlamatNama() {
        return alamat_nama;
    }

    public void setAlamatJalan(String alamat_jalan) {
        this.alamat_jalan = alamat_jalan;
    }

    public String getAlamatJalan() {
        return alamat_jalan;
    }

    public void setAlamatKota(String alamat_kota) {
        this.alamat_kota = alamat_kota;
    }

    public String getAlamatKota() {
        return alamat_kota;
    }

    public void setAlamat(String alamat_jalan) {
        this.alamat_jalan = alamat_jalan;
    }

    public String getAlamat() {
        return alamat_jalan;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDibuatOleh() {
        return dibuat_oleh;
    }

    public void setDibuatOleh(String createdBy) {
        this.dibuat_oleh = dibuat_oleh;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(String firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    public ArrayList<String> getPowers() {
        return powers;
    }

    public void setPowers(ArrayList<String> powers) {
        this.powers = powers;
    }
}