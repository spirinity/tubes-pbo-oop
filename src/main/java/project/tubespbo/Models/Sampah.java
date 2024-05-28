package project.tubespbo.Models;

public class Sampah {
    private int id;
    private String nama;
    private int harga;

    public Sampah(int id, String nama, int harga) {
        this.id = id;
        this.nama = nama;
        this.harga = harga ;
    }

    public int getId() { return id; }
    public String getNama() { return nama; }
    public int getHarga() { return harga; }

    public void setNama(String nama) { this.nama = nama; }
    public void setHarga(int harga) { this.harga = harga; }
}

