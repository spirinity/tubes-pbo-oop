package project.tubespbo.Models;

import java.util.Date;

public class Transaksi {
    private int id;
    private Entity user;
    private Sampah sampah;
    private int berat;
    private int harga;
    private String status;
    private Date tanggal;

    public Transaksi(int id, Entity user, Sampah sampah, int berat, int harga, String status, Date tanggal) {
        this.id = id;
        this.user = user;
        this.sampah = sampah;
        this.berat = berat;
        this.harga = harga;
        this.status = status;
        this.tanggal = tanggal;
    }

    public int getId() { return id; }
    public Entity getUser() {
        return user;
    }

    public void setUser(Entity user) {
        this.user = user;
    }

    public Sampah getSampah() {
        return sampah;
    }

    public void setSampah(Sampah sampah) {
        this.sampah = sampah;
    }
    public int getBerat() { return berat; }
    public int  getHarga() { return harga; }
    public String getStatus() { return status; }
    public Date getTanggal() { return tanggal; }

    public void setStatus(String status) { this.status = status; }
}

