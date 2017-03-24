package com.d3if3804160.shareeat.Model;



/**
 * Created by Deni Cahya Wintaka on 10/14/2016.
 */

public class Tempat_Makan {

    String id,nama, alamat, url, telepon, deskripsi,  user;
    double jarak;
    public Tempat_Makan(String id, String nama, String alamat, String url) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.url = url;
    }
    public Tempat_Makan(String id, String nama, String alamat, String url, String telpon, String deskripsi) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.url = url;
        this.telepon = telpon;
        this.deskripsi = deskripsi;

    }
    public Tempat_Makan(String id, String nama, String alamat, String url, String telpon, String deskripsi, String user) {
        this.nama = nama;
        this.alamat = alamat;
        this.url = url;
        this.telepon = telpon;
        this.deskripsi = deskripsi;
        this.user=user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getJarak() {
        return jarak;
    }

    public void setJarak(double jarak) {
        this.jarak = jarak;
    }
}
