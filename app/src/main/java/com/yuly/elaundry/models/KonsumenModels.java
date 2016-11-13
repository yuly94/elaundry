package com.yuly.elaundry.models;


public class KonsumenModels {

    private String konsumen_nama;
    private String konsumen_email;
    private String konsumen_id;
    private String konsumen_unique_id;
    private String konsumen_password;
    private String konsumen_password_lama;
    private String konsumen_password_baru;
    private String konsumen_kode;




    public String getKonsumenNama() {
        return konsumen_nama;
    }

    public String getKonsumenEmail() {
        return konsumen_email;
    }

    public String getKonsumenUnique_id() {
        return konsumen_unique_id;
    }


    public void setKonsumenNama(String konsumen_nama) {
        this.konsumen_nama = konsumen_nama;
    }

    public void setKonsumenEmail(String konsumen_email) {
        this.konsumen_email = konsumen_email;
    }

    public void setKonsumenPassword(String password) {
        this.konsumen_password = konsumen_password;
    }

    public void setKonsumenPasswordLama(String konsumen_password_lama) {
        this.konsumen_password_lama = konsumen_password_lama;
    }

    public void setKonsumen_passwordBaru(String new_password) {
        this.konsumen_password_baru = konsumen_password_baru;
    }

    public void setKonsumenKode(String konsumen_kode) {
        this.konsumen_kode = konsumen_kode;
    }

}
