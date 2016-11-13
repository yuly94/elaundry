package com.yuly.elaundry.models;

import java.util.ArrayList;
/**
 * Created by anonymous on 22/02/16.
 */
public class PesananModels {
    //Data Variables
    private String imageUrl;
    private String name;
    private int rank;
    private String realName;

    private String nama;
    private String alamat;
    private String kota;


    private String createdBy;
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

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setKota(String kota) {
        this.nama = kota;
    }

    public String getKota() {
        return kota;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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